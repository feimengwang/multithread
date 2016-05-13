package cn.true123.download;

public class DownLoader implements DownLoadInterface {
	DownLoaderFactory factory;
	private static DownLoader downloader = new DownLoader();

	
	public static DownLoader getInstance() {
		return downloader;
	}

	private DownLoader() {
		factory = DownLoaderFactory.newInstance();
	}

	@Override
	public void start() {
		factory.start();
	}

	@Override
	public void pause() {
		factory.pause();
	}

	@Override
	public void cancel() {
		factory.cancel();
	}

	@Override
	public void setUrl(String url) {
		factory.setUrl(url);
	}

	@Override
	public void setOnDownLoadListener(DownLoaderListener listener) {
		factory.setOnDownLoadListener(listener);

	}

	@Override
	public void setThreadCount(int count) {
		factory.setCount(count);
		
	}
}
