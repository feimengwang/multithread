package cn.true123.download;

public interface DownLoadInterface {
	public void start();

	public void pause();

	public void cancel();

	public void setUrl(String url);

	public void setThreadCount(int count);
	public void setOnDownLoadListener(DownLoaderListener listener);
}
