package cn.true123.httpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public abstract class BaseBuilder {
	protected BaseHttpMethod method;
	public BaseBuilder(BaseHttpMethod method) {
		this.method=method;
	}
	protected void doOutPut(HttpURLConnection con){
		
	}
	protected abstract HttpURLConnection build() throws IOException;
	protected String buildParamters(){
		Map paras = method.getParamters();
		StringBuffer sb = new StringBuffer();
		if(paras!=null && paras.size()>0){
			Iterator<String> keyIt = paras.keySet().iterator();
			while(keyIt.hasNext()){
				String key = keyIt.next();
				String value = (String) paras.get(key);
				String encodedValue = null;
				try {
					encodedValue = URLEncoder.encode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if(encodedValue!=null){
					sb.append(key).append("=").append(encodedValue);
				}else{
					sb.append(key).append("=").append(value);
				}
				if(keyIt.hasNext())sb.append("&");
			}
		}
		return sb.toString();
	}
}
