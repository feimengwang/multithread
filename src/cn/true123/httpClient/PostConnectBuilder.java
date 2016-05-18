package cn.true123.httpClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostConnectBuilder extends BaseBuilder {

	public PostConnectBuilder(BaseHttpMethod method) {
		super(method);
	}

	@Override
	protected void doOutPut(HttpURLConnection con) {
		super.doOutPut(con);
		OutputStream out;
		try {
			out = con.getOutputStream();
			out.write(buildParamters().getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected HttpURLConnection build() throws IOException {

		URL url = new URL(method.getUrl());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method.getMethod());
		con.setDoOutput(true);
		con.setUseCaches(false);
		return con;
	}

}
