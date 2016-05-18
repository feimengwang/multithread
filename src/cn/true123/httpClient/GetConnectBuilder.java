package cn.true123.httpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetConnectBuilder extends BaseBuilder {

	public GetConnectBuilder(BaseHttpMethod method) {
		super(method);
	}

	@Override
	protected HttpURLConnection build() throws IOException {
		String surl = method.getUrl();
		String paras = buildParamters();
		if (paras.length() > 0 && surl.endsWith("&")) {
			surl += paras;
		} else if (paras.length() > 0 && !surl.endsWith("&")) {
			surl += "&" + paras;
		}
		URL url = new URL(surl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod(method.getMethod());

		return con;

	}

}
