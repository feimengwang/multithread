package cn.true123.download;

import cn.true123.file.DownLoaderFile;

public abstract class BaseDownLoadThread extends Thread {
	protected String url;
	protected long start;
	protected long end;
	protected DL dl;
	protected DownLoaderThreadListener listener;
	protected DownLoaderFile df;
	protected boolean run = true;
	protected boolean flg = false;
	 protected void cancel() {
		run = false;
	}

	protected boolean testFinish() {
		return flg;
	}

	protected void setDownLoaderlistener(DownLoaderThreadListener listener) {
		this.listener = listener;
	}
	protected long getRemainder() {
		long remainder = end - start;
		return remainder > 0 ? remainder : 0;
	}
	
}
