package cn.true123.download;

public interface DownLoaderListener {
	public void onFinish(String msg);
	public void onUpgrade(float progerss);
	public void onError(String msg);
	
}
