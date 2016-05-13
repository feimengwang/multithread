package cn.true123.httpClient;

public class HttpHead extends BaseHttpMethod {

	@Override
	public String getMethod() {
		return HEAD;
	}

	public HttpHead() {
	}

	public HttpHead(String url) {
		super(url);
	}
}
