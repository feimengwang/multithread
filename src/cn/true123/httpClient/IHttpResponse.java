package cn.true123.httpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IHttpResponse {

	public int getStatusCode() throws IOException;
	public InputStream getInputStream() throws IOException;
	public String getContent();
	public Map<String, List<String>> getHeaders();
	public String getHeader(String name);
	public void close();
}
