package org.whale.test;

import java.util.Random;
import java.util.UUID;

public class TestHash {

	static int shardnum = 20;
	static final String PRIFIX = "abcdefghijklmnopqrstuvwxyz";
	
	public static void main(String[] args) {
		
		String s = "111";
		int hashCode = (PRIFIX+s).hashCode();
		System.out.println(hashCode);
		
		String s2 = "1";
		int hashCode2 = (s2).hashCode();
		System.out.println(hashCode2);
		
	}
	
	public static void main2(String[] args) {
		
		ShardServer shardServer1 = new ShardServer("node1", "192.168.117.101");
		ShardServer shardServer2 = new ShardServer("node2", "192.168.117.102");
		ShardServer shardServer3 = new ShardServer("node3", "192.168.117.103");
		ShardServer shardServer4 = new ShardServer("node4", "192.168.117.104");
		ShardServer shardServer5 = new ShardServer("node5", "192.168.117.105");
		ShardServer shardServer6 = new ShardServer("node6", "192.168.117.106");
		
		int hashCode1 = shardServer1.hashCode();
		int hashCode2 = shardServer2.hashCode();
		int hashCode3 = shardServer3.hashCode();
		int hashCode4 = shardServer4.hashCode();
		int hashCode5 = shardServer5.hashCode();
		int hashCode6 = shardServer6.hashCode();
		
		int shard1hash = shardServer1.hashCode()%shardnum;
		int shard2hash = shardServer2.hashCode()%shardnum;
		int shard3hash = shardServer3.hashCode()%shardnum;
		int shard4hash = shardServer4.hashCode()%shardnum;
		int shard5hash = shardServer5.hashCode()%shardnum;
		int shard6hash = shardServer6.hashCode()%shardnum;
		
		System.out.println(hashCode1 + "    " + shard1hash);
		System.out.println(hashCode2 + "    " + shard2hash);
		System.out.println(hashCode3 + "    " + shard3hash);
		System.out.println(hashCode4 + "    " + shard4hash);
		System.out.println(hashCode5 + "    " + shard5hash);
		System.out.println(hashCode6 + "    " + shard6hash);
	
	}
	
	public static void main1(String[] args) {
		
		String[] names = {"tom", "jom", "lucy", "lily", "kobe", "will"};
	
		for (int i = 0; i < 100; i++) {
			String id = UUID.randomUUID().toString();
			String name = names[new Random().nextInt(names.length)];
			int age = new Random().nextInt(100);
			String partion = id+name+age;
			int partionid = Math.abs(partion.hashCode())%Integer.MAX_VALUE;
			System.out.println(partionid);
		}
	}
}