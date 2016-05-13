package cn.true123.download;

public class DL {
	private String url;
	private long s;
	private long e;
	private String id;
	
	public DL(){
		id = Utils.MD5(url+s+""+e);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DL other = (DL) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getS() {
		return s;
	}

	public void setS(long s) {
		this.s = s;
	}

	public long getE() {
		return e;
	}

	public void setE(long e) {
		this.e = e;
	}

	@Override
	public String toString() {

		return  url+";"+s+";"+e;
	}

}
