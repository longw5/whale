package org.whale.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

public class TestZkApi {

	private static final int TIMEOUT = 3000;
	
	public static void main(String[] args) throws Exception {
		
		ZooKeeper zkp = new ZooKeeper("node1:2181", TIMEOUT, null);
		
		if(zkp.exists("/aaa", false) != null) {
			zkp.delete("/aaa", -1);
			String create = zkp.create("/aaa", "node1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			System.out.println("create : "+create);
		}else {
			String create = zkp.create("/aaa", "node1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			System.out.println("create : "+create);
		}
		
		byte[] data = zkp.getData("/aaa", false, null);
		String ss = new String(data);
		
		ss += (";"+ss);
		Stat setData = zkp.setData("/aaa", ss.getBytes(), -1);
		long czxid = setData.getCzxid();
		long pzxid = setData.getPzxid();
		long mzxid = setData.getMzxid();
		
		long ctime = setData.getCtime();
		long mtime = setData.getMtime();
		
		int dataLength = setData.getDataLength();
		
		int version = setData.getVersion();
		int aversion = setData.getAversion();
		int cversion = setData.getCversion();
		
		long ephemeralOwner = setData.getEphemeralOwner();
		
		int numChildren = setData.getNumChildren();
		
		System.out.println("czxid :  = "+czxid);
		System.out.println("pzxid :  = "+pzxid);
		System.out.println("mzxid :  = "+mzxid);
		System.out.println("ctime :  = "+ctime);
		System.out.println("mtime :  = "+mtime);
		System.out.println("dataLength :  = "+dataLength);
		System.out.println("version :  = "+version);
		System.out.println("cversion :  = "+cversion);
		System.out.println("ephemeralOwner :  = "+ephemeralOwner);
		System.out.println("numChildren :  = "+numChildren);
		
		byte[] data2 = zkp.getData("/aaa", false, null);
		String ss2 = new String(data2);
		System.out.println(ss2);
	}
}
