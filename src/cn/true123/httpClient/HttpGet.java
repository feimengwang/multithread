package cn.true123.httpClient;

public class HttpGet extends BaseHttpMethod {

	@Override
	public String getMethod() {
		return GET;
	}

	public HttpGet() {
	}

	public HttpGet(String url) {
		super(url);
	}
}
