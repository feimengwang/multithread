package cn.true123.httpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class HttpResponse implements IHttpResponse {

	private InputStream inputStream;
	private HttpURLConnection con;

	public HttpResponse(HttpURLConnection con) {
		this.con = con;
	}

	@Override
	public int getStatusCode() throws IOException {

		return con != null ? con.getResponseCode() : -1;

	}

	@Override
	public InputStream getInputStream() throws IOException {

		inputStream = null != con ? con.getInputStream() : null;

		return inputStream;
	}

	@Override
	public String getContent() {
		if (inputStream != null) {
			StringBuffer content = new StringBuffer();
			BufferedInputStream bufferInputStream = new BufferedInputStream(
					inputStream);
			byte[] data = new byte[1024];
			int length;
			try {
				while ((length = bufferInputStream.read(data)) > 0) {
					content.append(new String(data, 0, length));
				}
				return content.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		// TODO Auto-generated method stub
		return null != con ? con.getHeaderFields() : null;
	}

	@Override
	public String getHeader(String name) {
		return null != con ? con.getHeaderField(name) : null;
	}

	@Override
	public void close() {
		if (null != null)
			con.disconnect();

	}

}
