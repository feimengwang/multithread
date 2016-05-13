package cn.true123.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.true123.httpClient.BaseHttpMethod;
import cn.true123.httpClient.DownLoadHttpClient;
import cn.true123.httpClient.HttpGet;
import cn.true123.httpClient.HttpHead;
import cn.true123.httpClient.IHttpResponse;

public class Utils {

	public static void main(String[] args) {
		//test("http://ca-toronto-dl02.jazz.net/mirror/downloads/rational-team-concert/5.0.2/5.0.2/RTC-Eclipse-Client-repo-5.0.2.zip");
		//test("http://ftp.kaist.ac.kr/eclipse/technology/epp/downloads/release/mars/2/eclipse-php-mars-2-win32-x86_64.zip");
		test("http://jsdx.down.chinaz.com/%C1%F4%D1%D4%C8%D5%BC%C7/o-blog2.6.rar");
		//test("http://blog.csdn.net/xiangsuixinsheng/article/details/6871868");
	}

	public static DLModel getModel(String surl) {
		DLModel mDLModel = new DLModel();
		mDLModel.setUrl(surl);
		BaseHttpMethod head = new HttpHead(surl);
		DownLoadHttpClient client = DownLoadHttpClient.getInstance();
		IHttpResponse response=null;
		try {
			response = client.exec(head);
			if(response!=null){
				String size = response.getHeader("Content-Length");
				long lsiz = size!=null && !"".equals(size)?Long.parseLong(size):0l;
				mDLModel.setSize(lsiz);
				String acceptRanges = response.getHeader("Accept-Ranges");
				mDLModel.setAcceptRanges(acceptRanges);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(response!=null){
				response.close();
			}
		}
		return mDLModel;
	}
	public static void test(String url){
		BaseHttpMethod head = new HttpGet(url);
		DownLoadHttpClient client = DownLoadHttpClient.getInstance();
		try {
			IHttpResponse response = client.exec(head);
			if(response!=null){
				Map<String, List<String>> m = response.getHeaders();
				 Iterator it = m.keySet().iterator();
				 while(it.hasNext()){
				 String key = (String) it.next();
				 System.out.println(key);
				 List<String> l = m.get(key);
				 for (String ll:l) {
				 System.out.println(ll);
				 }
				 }
			}
			
		InputStream in = response.getInputStream();
		FileOutputStream fos = new FileOutputStream(new File("c:/ddd.zip"));
		byte[] d =new byte[1024];
		int length;
		while((length=in.read(d))>0){
			fos.write(d, 0, length);
			fos.flush();
		}
		fos.close();
		response.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected static long getFileSize(String surl) {
		long length = 0;
		try {
			URL url = new URL(surl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(10000);
			con.setRequestMethod("HEAD");
			con.setRequestProperty("tjazz", "p94dN6q5j473rogZ452p421k9Z2a21");

			con.connect();
			System.out.println("dddd");
			Map<String, List<String>> m = con.getHeaderFields();
			// Iterator it = m.keySet().iterator();
			// while(it.hasNext()){
			// String key = (String) it.next();
			// System.out.println(key);
			// List<String> l = m.get(key);
			// for (String ll:l) {
			// System.out.println(ll);
			// }
			// }

			List<String> contentLength = m.get("Content-Length");
			for (String cl : contentLength) {
				con.disconnect();
				System.out.println(cl);
				return Integer.parseInt(cl);
			}
			System.out.println("fffffff");

			length = con.getContentLength();
			con.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return length;
	}

	public static String MD5(String msg) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = msg.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
	public static String getFileNameNosuffix(String url) {
		String fileNameNosuffix = "";
		if (url != null) {
			String fileName = "";
			fileName = getFileName(url);
			if (fileName != null && fileName.length() > 0) {
				int index = fileName.lastIndexOf(".");
				if (index != -1 && index <= fileName.length() - 1) {
					fileNameNosuffix = fileName.substring(0, index);
				} else {
					fileNameNosuffix = fileName;
				}
			}
		}
		return fileNameNosuffix;
	}
	public static String getFileName(String url) {
		if (url != null) {
			String fileName = "";
			int split = url.lastIndexOf('/');
			if (split != -1 && split < url.length()) {
				fileName = url.trim().substring(url.lastIndexOf('/') + 1);
				return fileName;
			}
		}
		return "";
	}
}
