package cn.true123.httpClient;

import java.io.IOException;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnectFactory {
	private static int CON_TIME_OUT = 5 * 100;
	private static int READ_TIME_OUT = 10 * 1000;

	public void setConnectTimeout(int connectTimeout) {
		CON_TIME_OUT = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		READ_TIME_OUT = readTimeout;
	}

	private static ConnectFactory instance;

	private ConnectFactory() {

	}

	public static ConnectFactory getInstance() {
		if (null == instance) {
			synchronized (ConnectFactory.class) {
				instance = new ConnectFactory();
			}
		}
		return instance;
	}

	public HttpURLConnection getCon(BaseHttpMethod method,Map<String,String> property) throws IOException {

		HttpURLConnection con = method.getBuilder().build();
		
		con.setConnectTimeout(CON_TIME_OUT);
		con.setReadTimeout(READ_TIME_OUT);
		if(null != property && property.size()>0){
			Iterator keyIt = property.keySet().iterator();
			while(keyIt.hasNext()){
				String key = (String) keyIt.next();
				String value = property.get(key);
				con.setRequestProperty(key,value);
			}
		}
		//some sites will refuse, if no "User-Agent", so set default value
		if(property==null || !property.containsKey("User-Agent")){
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		}
		
		con.connect();
		method.getBuilder().doOutPut(con);
		return con;
	}
	public HttpURLConnection getCon(BaseHttpMethod method) throws IOException {
		return getCon(method, null);
	}
}
