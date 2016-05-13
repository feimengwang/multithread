package cn.true123.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import cn.true123.config.Configration;

public class DownLoaderFile {
	RandomAccessFile raf = null;
	String path = "c:/";

	public DownLoaderFile() {

	}

	public DownLoaderFile(String url, long start) {
		String fileName = url.substring(url.lastIndexOf("/"));
		try {
			String resPath = Configration.getValue("path");
			if (resPath != null) {
				if (resPath.lastIndexOf("/") != resPath.length() - 1)
					path = resPath + "/";
				else
					path = resPath;
			}
			File f = new File(path + fileName);
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			if(!f.exists()){f.createNewFile();}
			raf = new RandomAccessFile(f, "rw");
			raf.seek(start);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized void write(byte[] b, int length) {

		try {
			raf.write(b, 0, length);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

	}
}
