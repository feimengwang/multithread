package cn.true123.httpClient;

public abstract class BaseHttpMethod {
	protected static String HEAD = "HEAD";
	protected static String GET = "GET";
	protected static String POST = "POST";
	String url;

	protected abstract String getMethod();

	protected void setUrl(String url) {
		this.url = url;
	}
	protected String getUrl(){
		return url;
	}

	protected BaseHttpMethod() {

	}

	protected BaseHttpMethod(String url) {
		this.url = url;
	}
}
