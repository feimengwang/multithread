package cn.true123.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import cn.true123.file.DownLoaderFile;
import cn.true123.httpClient.DownLoadHttpClient;
import cn.true123.httpClient.HttpGet;
import cn.true123.httpClient.IHttpResponse;

public class DownLoaderThread extends BaseDownLoadThread {



	protected DownLoaderThread() {

	}

	protected DownLoaderThread(String url, DL dl) {
		this.url = url;
		this.start = dl.getS();
		this.end = dl.getE();
		this.dl = dl;
		df = new DownLoaderFile(url, start);
	}

	@Override
	public void run() {
		DownLoadHttpClient client = DownLoadHttpClient.getInstance();
		while (start < end && run) {
			try {
				HttpGet get = new HttpGet(url);
				Map property = new HashMap();
				property.put("RANGE",  "bytes=" + start + "-");
				IHttpResponse res = client.exec(get, property);
				if(206 != res.getStatusCode() || 200 == res.getStatusCode()){
					continue;
				}
				InputStream in = res.getInputStream();
				byte[] b = new byte[1024];
				int length;
				while (run && (length = in.read(b)) > 0 && start < end) {
					df.write(b, length);
					start += length;
					dl.setS(start);
					listener.onUpgrade();
				}
				res.close();
				flg = true;

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
