package cn.true123.httpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnectFactory {
	private int CON_TIME_OUT = 5 * 100;
	private int READ_TIME_OUT = 10 * 1000;

	public void setConnectTimeout(int connectTimeout) {
		this.CON_TIME_OUT = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.READ_TIME_OUT = readTimeout;
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

		HttpURLConnection con = null;

		URL url = new URL(method.getUrl());
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method.getMethod());
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
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		con.connect();

		return con;
	}

}
