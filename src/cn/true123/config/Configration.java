package cn.true123.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Configration {
	static Properties p1;
	static Properties p2;

	public Configration() {
		init();
	}

	private static void init() {
		
		p1 = new Properties();
		p2 = new Properties();
		try {
			InputStream in =Configration.class.getClassLoader().getResourceAsStream("./dl.properties");
			if(in!=null){
				p1.load(in);
			}
			p2.load(Configration.class.getClassLoader().getResourceAsStream("downloader.properties"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		String ret="";
		if(p1==null && p2==null){
			init();
		}
		ret = p1.getProperty(key);
		if(ret!=null){
			return ret;
		}
		ret = p2.getProperty(key);
		return ret;
	}
}
