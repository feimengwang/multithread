package cn.true123.db;

import java.util.List;

import cn.true123.download.DL;

public interface FileDB {
	public void insert(List<DL> dlList);
	public void clear(String url);
	public List<DL> getInit(String url);
	public boolean check(String url);
}
