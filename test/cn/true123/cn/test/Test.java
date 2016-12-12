package cn.true123.cn.test;

import cn.true123.download.DownLoader;
import cn.true123.download.DownLoaderListener;

public class Test {
	public static void main(String[] args) {

		DownLoader dl = DownLoader.getInstance();
		dl.setUrl("http://jsdx.down.chinaz.com/%C1%F4%D1%D4%C8%D5%BC%C7/o-blog2.6.rar");
		dl.setOnDownLoadListener(new DownLoaderListener() {

			@Override
			public void onError(String arg0) {
				System.out.println(arg0);
			}

			@Override
			public void onFinish(String arg0) {
				System.out.println(arg0);
			}

			@Override
			public void onUpgrade(float arg0) {
				System.out.println(arg0);

			}
		});

		dl.setThreadCount(3);
		dl.start();
	}

}
