package cn.true123.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.true123.config.Configration;
import cn.true123.download.DL;
import cn.true123.download.Utils;

public class FileDBImpl implements FileDB {
	private String path = "c:\\dl\\";
	private boolean flg = false;

	public FileDBImpl() {
		path = Configration.getValue("path");
	}

	@Override
	public synchronized void insert(List<DL> dlList) {
		if (!flg) {
			flg = true;
			String s = "";
			String url = "";
			if(dlList !=null  && dlList.size()>0){
				DL dl=	dlList.get(0);
				url =dl.getUrl();
				s+=url+"\r\n";
			}
			for (DL dl : dlList) {
			
				s += dl.getS() + ";" + dl.getE() + "\r\n";
			}
			executeClear(url);
			File file = new File(path + Utils.getFileNameNosuffix(url) + ".dat");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					System.out.println("Create new file error!");
				}
			}

			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(s.toString().getBytes());
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			flg = false;
		}
	}

	@Override
	public void clear(String url) {
		executeClear(url);
	}

	@Override
	public List<DL> getInit(String url) {
		String fileName = Utils.getFileNameNosuffix(url);
		List<DL> dlList = new ArrayList<DL>();

		if (fileName != null && fileName.length() > 0) {
			try {
				File file = new File(path + fileName + ".dat");
				if (!file.exists()) {
					dlList.clear();
					return dlList;
				}

				BufferedReader br = new BufferedReader(new FileReader(new File(
						path + fileName + ".dat")));
				String line;
				boolean firstLine=true;
				while ((line = br.readLine()) != null) {
					if(firstLine){
						continue;
					}
					String[] dlSplit = line.split(";");
					DL dl = new DL();
					if(dlSplit!=null && dlSplit.length>=2){
						dl.setUrl(url);
						long s = Long.parseLong(dlSplit[0]);
						long e = Long.parseLong(dlSplit[1]);
						if(s<e){
							dlList.add(dl);
						}
					}
					firstLine = false;
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dlList;
	}

	private void executeClear(String url) {
		String fileNameNosuffix = Utils.getFileNameNosuffix(url);
		if (fileNameNosuffix != null && fileNameNosuffix.length() > 0) {
			File file = new File(path + fileNameNosuffix + ".dat");
			if (file.exists())
				file.delete();
		}
	}



	

	@Override
	public boolean check(String url) {
		String fileName = Utils.getFileName(url);
		if(fileName!=null && !"".equals(fileName)){
			File f = new File(path +fileName);
			return f.exists();
		}
		return false;
	}

}
