package cn.true123.httpClient;

public class HttpPost extends BaseHttpMethod {

	@Override
	public String getMethod() {
		return POST;
	}

	public HttpPost() {
	}

	public HttpPost(String url) {
		super(url);
	}

	@Override
	protected BaseBuilder getBuilder() {
		return new PostConnectBuilder(this);
	}
	
}
