package cn.true123.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.true123.file.DownLoaderFile;

public class CopyOfDownLoaderThread extends Thread {

	private String url;
	private long start;
	private long end;
	private DL dl;
	private DownLoaderThreadListener listener;
	private DownLoaderFile df;
	boolean run = true;
	boolean flg = false;

	protected CopyOfDownLoaderThread() {

	}

	protected CopyOfDownLoaderThread(String url, DL dl) {
		this.url = url;
		this.start = dl.getS();
		this.end = dl.getE();
		this.dl = dl;
		df = new DownLoaderFile(url, start);
	}

	@Override
	public void run() {
		while (start < end && run) {
			try {
				URL u = new URL(url);
				HttpURLConnection con = (HttpURLConnection) u.openConnection();
//http://ca-toronto-dl02.jazz.net/mirror/downloads/rational-team-concert/5.0.2/5.0.2/RTC-Eclipse-Client-repo-5.0.2.zip?tjazz=p94dN6q5j473rogZ452p421k9Z2a21
				con.addRequestProperty("RANGE", "bytes=" + start + "-");
				con.setRequestProperty("tjazz", "p94dN6q5j473rogZ452p421k9Z2a21");
				con.connect();
				System.out.println("code=="+con.getResponseCode());
				if(206 != con.getResponseCode() || 200 == con.getResponseCode()){
					//con.disconnect();
					continue;
				}
				InputStream in = con.getInputStream();
				byte[] b = new byte[1024];
				int length;
				while (run && (length = in.read(b)) > 0 && start < end) {
					df.write(b, length);
					start += length;
					dl.setS(start);
					listener.onUpgrade();
				}
				con.disconnect();
				flg = true;

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void cancel() {
		run = false;
	}

	protected boolean testFinish() {
		return flg;
	}

	protected void setDownLoaderlistener(DownLoaderThreadListener listener) {
		this.listener = listener;
	}

	protected long getRemainder() {
		long remainder = end - start;
		return remainder > 0 ? remainder : 0;
	}
	
	public static void main(String[] args) {
		//URL u = new URL
	}
	
	
}
