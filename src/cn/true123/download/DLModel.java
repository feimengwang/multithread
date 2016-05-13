package cn.true123.download;

public class DLModel {
	private String url;
	
	private String acceptRanges;
	
	private long size;

	public String getAcceptRanges() {
		return acceptRanges;
	}

	public void setAcceptRanges(String acceptRanges) {
		this.acceptRanges = acceptRanges;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSupportAcceptRanges() {
		return null!=acceptRanges;
	}


	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
