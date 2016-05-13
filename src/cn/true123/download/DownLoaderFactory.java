package cn.true123.download;

import java.util.ArrayList;
import java.util.List;

import cn.true123.config.Configration;
import cn.true123.db.FileDB;
import cn.true123.db.FileDBImpl;

public class DownLoaderFactory implements DownLoadInterface,
		DownLoaderThreadListener {
	private String url;
	private DownLoaderListener listener;
	private int count = 5;
	private List<DL> dlList = new ArrayList<DL>();
	private List<BaseDownLoadThread> threadList = new ArrayList<BaseDownLoadThread>();
	private long fileSize;
	private float prePercent;
	private DLModel dlModel;
	DownLoadExecutor executor;
	public void setCount(int count) {
		this.count = count;
	}

	public static DownLoaderFactory newInstance() {
		return new DownLoaderFactory();
	}

	private DownLoaderFactory() {
		String ct = Configration.getValue("thread");
		if (ct != null) {
			count = Integer.parseInt(ct);
		}
	}

	class DLThread extends Thread {

		@Override
		public void run() {
			//fileSize = Utils.getFileSize(url);
			dlList.clear();
			if(!dlModel.isSupportAcceptRanges()){
				DL dl = new DL();
				dl.setUrl(dlModel.getUrl());
				dl.setS(0);
				dl.setE(dlModel.getSize());
				if (!dlList.contains(dl)) {
					dlList.add(dl);
				}
				DownLoaderSingleThread thread = new DownLoaderSingleThread(url, dl);
				if (!threadList.contains(thread)) {
					threadList.add(thread);
				}
				start(threadList);
				return;
			}
			FileDB db = new FileDBImpl();
			boolean isFileExist = db.check(url);
			List<DL> list = db.getInit(url);
			
			if(isFileExist){
			if (list != null && list.size() > 0) {
				for (DL dl : list) {
					DownLoaderThread thread = new DownLoaderThread(url, dl);
					// thread.setDownLoaderlistener(DownLoaderFactory.this);
					// thread.start();
					if (!threadList.contains(thread)) {
						threadList.add(thread);
					}
					if (!dlList.contains(dl)) {
						dlList.add(dl);
					}
				}
				start(threadList);
				// return;
			} }else {
				if (fileSize != 0) {
					long average = fileSize / count;
					for (int i = 0; i < count; i++) {
						long s = average * i;
						long e = average * (i + 1);
						if (e > fileSize) {
							e = fileSize;
						}
						System.out.println("s=" + s + ";e=" + e);
						DL dl = new DL();
						dl.setUrl(url);
						dl.setE(e);
						dl.setS(s);
						if (!dlList.contains(dl)) {
							dlList.add(dl);
						}
						DownLoaderThread thread = new DownLoaderThread(url, dl);
						// thread.setDownLoaderlistener(DownLoaderFactory.this);
						// thread.start();
						if (!threadList.contains(thread)) {
							threadList.add(thread);
						}

					}
					start(threadList);
				}
			}
		}

		private void start(List<BaseDownLoadThread> threadList) {
			 executor = DownLoadExecutor.instance().newFixedThreadPool(count);
			for (BaseDownLoadThread thread : threadList) {
				thread.setDownLoaderlistener(DownLoaderFactory.this);
				//thread.start();
				executor.execute(thread);
			}
			new DLMoniter().start();
		}
	}

	class DLMoniter extends Thread {

		@Override
		public void run() {
			List<BaseDownLoadThread> temp = new ArrayList<BaseDownLoadThread>();
			while (true) {
				for (BaseDownLoadThread thread : threadList) {
					if (thread.testFinish()) {
						temp.add(thread);
					}
				}
				for (BaseDownLoadThread downLoaderThread : temp) {
					threadList.remove(downLoaderThread);
				}
				temp.clear();
				if (threadList.size() == 0) {
					FileDB db = new FileDBImpl();
					db.clear(url);
					break;
				} else {
					FileDB db = new FileDBImpl();
					db.insert(dlList);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	protected void run() {
		new DLThread().start();
	}

	@Override
	public void start() {
		init();
		if(!checkPath()){
			if (listener != null) {
				listener.onError("please set the down load path!");
			}
			return;
		}
		if(fileSize==0){
			if (listener != null) {
				listener.onError("Can not get the file size.");
			}
			return;
		}
		run();
	}

	private boolean checkPath() {
		return Configration.getValue("path") == null ? false : true;
	}
	private void init(){
		dlModel = Utils.getModel(url);
		fileSize = dlModel.getSize();
	}

	@Override
	public void pause() {
		threadCancle();
		FileDB db = new FileDBImpl();
		db.insert(dlList);
	}

	@Override
	public void cancel() {
		threadCancle();
		FileDB db = new FileDBImpl();
		db.clear(url);
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void setOnDownLoadListener(DownLoaderListener listener) {
		this.listener = listener;
	}

	private void threadCancle() {
		for (BaseDownLoadThread thread : threadList) {
			thread.cancel();
		}
		executor.shutdown();
	}

	@Override
	public synchronized void onUpgrade() {
		if(listener==null){
			return;
		}
		long remainders = 0l;
		for (BaseDownLoadThread thread : threadList) {
			remainders += thread.getRemainder();
		}
		float curPercent = (fileSize - remainders) * 1000 / fileSize / 10;
		if (curPercent > prePercent) {
			listener.onUpgrade(curPercent);
			prePercent = curPercent;
		}
		if(remainders==0){
			executor.shutdown();
			listener.onFinish("finish!");
		}

	}

	@Override
	public void setThreadCount(int count) {
		this.count=count;
		
	}

	@Override
	public void onUpdate(DL dl) {
		
		
	}
	
}
