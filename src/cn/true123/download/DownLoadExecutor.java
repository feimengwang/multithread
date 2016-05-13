package cn.true123.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownLoadExecutor {
	private static DownLoadExecutor executor;
	private ExecutorService service;
	public static DownLoadExecutor instance() {
		if (null == executor) {
			synchronized (DownLoadExecutor.class) {
				executor = new DownLoadExecutor();
			}
		}
		return executor;
	}

	private DownLoadExecutor() {

	}

	public DownLoadExecutor newFixedThreadPool(int count) {
		service = Executors.newFixedThreadPool(count);
		return this;
	}

	public DownLoadExecutor newSingleThreadScheduledExecutor() {
		service = Executors.newSingleThreadScheduledExecutor();
		return this;
	}

	public DownLoadExecutor newSingleThreadExecutor() {
		service = Executors.newSingleThreadExecutor();
		return this;
	}

	public void execute(Thread t){
		service.execute(t);
	}
	public void shutdown(){
		service.shutdown();
	}
}
