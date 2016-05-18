package cn.true123.httpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HeadConnectBuilder extends BaseBuilder {

	public HeadConnectBuilder(BaseHttpMethod method) {
		super(method);
	}

	@Override
	protected HttpURLConnection build() throws IOException {
		String surl = method.getUrl();
		
		URL url = new URL(surl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod(method.getMethod());

		return con;

	}

}
