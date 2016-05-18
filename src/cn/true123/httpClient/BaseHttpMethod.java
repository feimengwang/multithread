package cn.true123.httpClient;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseHttpMethod {
	protected static String HEAD = "HEAD";
	protected static String GET = "GET";
	protected static String POST = "POST";
	String url;
	protected  Map paras = new HashMap();
	
	protected abstract String getMethod();
	protected abstract BaseBuilder getBuilder();
	protected Map getParamters(){
		return paras;
	}
	public void putParamter(String key,Object value){
		paras.put(key, value);
	}
	protected void setUrl(String url) {
		this.url = url;
	}
	protected String getUrl(){
		return url;
	}

	protected BaseHttpMethod() {

	}

	protected BaseHttpMethod(String url) {
		this.url = url;
	}
}
