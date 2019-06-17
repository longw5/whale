package org.whale.test;

public class ShardServer {
	private String domain;
	private String ip;

	public ShardServer(String domain, String ip) {
		this.domain = domain;
		this.ip = ip;
	}

	@Override
	public String toString() {
		return domain + ":" + ip;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
