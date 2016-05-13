package cn.true123.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;


import cn.true123.file.DownLoaderFile;
import cn.true123.httpClient.DownLoadHttpClient;
import cn.true123.httpClient.HttpGet;
import cn.true123.httpClient.IHttpResponse;

public class DownLoaderSingleThread extends BaseDownLoadThread {


	private int available;
	private int current;

	protected DownLoaderSingleThread() {

	}

	protected DownLoaderSingleThread(String url, DL dl) {
		this.url = url;
		df = new DownLoaderFile(url, 0);
	}

	@Override
	public void run() {
		DownLoadHttpClient client = DownLoadHttpClient.getInstance();
		try {
			HttpGet get = new HttpGet(url);

			IHttpResponse res = client.exec(get);
			if (200 != res.getStatusCode()) {
				return;
			}
			InputStream in = res.getInputStream();
			available = in.available();
			byte[] b = new byte[1024];
			int length;
			while (run && (length = in.read(b)) > 0) {
				current += length;
				df.write(b, length);
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

	@Override
	protected long getRemainder() {
		long remainder = available - current;
		return remainder > 0 ? remainder : 0;
	}

}
