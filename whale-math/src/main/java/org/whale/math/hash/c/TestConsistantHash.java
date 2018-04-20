package org.whale.math.hash.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.MD5Hash;

/**
 * 一致性hash计算， 环上位置
 * @author Administrator
 */
public class TestConsistantHash {

	//计算一致性hash值， 移位运算
	private static final long CONSISTAN_HASH = 1L << 32;
	private static final List<Long> list = new ArrayList<>();
	private static final Map<Long,String> map = new HashMap<>();
	
	static {
		String server1 = "192.168.186.171";
		String server2 = "192.168.186.172";
		String server3 = "192.168.186.174";
		String server4 = "192.168.186.188";
		String server5 = "192.168.186.199";
		String server6 = "192.168.186.166";
		String server7 = "192.168.186.181";
		String server8 = "192.168.186.111";
		String server9 = "192.168.186.122";
		
		//初始化hash列表
		//hash全部字符串 权重1 
		list.add(hashCode(server1));
		list.add(hashCode(server2));
		list.add(hashCode(server3));
		list.add(hashCode(server4));
		list.add(hashCode(server5));
		
		//hash奇数位     权重2
		list.add(hashCodeUnEven(server1));
		list.add(hashCodeUnEven(server2));
//		list.add(hashCodeUnEven(server3));
//		list.add(hashCodeUnEven(server4));
		list.add(hashCodeUnEven(server5));
		
		//hash偶数位 权重3
//		list.add(hashCodeEven(server1));
//		list.add(hashCodeEven(server2));
//		list.add(hashCodeEven(server3));
//		list.add(hashCodeEven(server4));
		list.add(hashCodeEven(server5));
		
		//初始化hash映射表
		//权重为1
		map.put(hashCode(server1), server1);
		map.put(hashCode(server2), server2);
		map.put(hashCode(server3), server3);
		map.put(hashCode(server4), server4);
		map.put(hashCode(server5), server5);
		
		//权重为2
		map.put(hashCodeUnEven(server1), server1);
		map.put(hashCodeUnEven(server2), server2);
//		map.put(hashCodeUnEven(server3), server3);
//		map.put(hashCodeUnEven(server4), server4);
		map.put(hashCodeUnEven(server5), server5);
		
		//权重为3
//		map.put(hashCodeEven(server1), server1);
//		map.put(hashCodeEven(server2), server2);
//		map.put(hashCodeEven(server3), server3);
//		map.put(hashCodeEven(server4), server4);
		map.put(hashCodeEven(server5), server5);
		
	}
	
	public static void main(String[] args) {
		
		//计算hash值
		long hashCode = hashCode("192.168.186.144");
		System.out.println("hashCode:"+hashCode);
		list.add(hashCode);
		Collections.sort(list);
		
		System.out.println(list);
		
		//计算前后节点
		//hash环上的位置
		int indexOf = list.indexOf(hashCode);
		System.out.println("indexOf:"+indexOf);
		
		//计算hash距离, 距离最近的两个节点
		Long long1 = null;
		Long long2 = null;
		try {
			long1 = list.get(indexOf-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(map.get(list.get(indexOf+1)));
			System.exit(0);
		}
		
		try {
			long2 = list.get(indexOf+1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(map.get(list.get(indexOf-1)));
			System.exit(0);
		}
		
		//获取服务器
		String prefixNode = map.get(list.get(indexOf-1));
		String suffixNode = map.get(list.get(indexOf+1));
		
		String info = long2-hashCode > hashCode-long1?suffixNode:prefixNode;
		System.out.println(info);
	}

	//计算hash值
	private static long hashCode(String s) {
		//计算hash值
		MD5Hash digest = MD5Hash.digest(s.getBytes());
		
		byte[] digest2 = digest.getDigest();
		
		//通过hash值计算
		long ss = 1;
		for (int i = 0; i < digest2.length; i++) {
			byte b = digest2[i];
			if(b == 0) {
				continue;
			}
			ss *= b;
		}
		return Math.abs(ss%CONSISTAN_HASH);
	}	
	
	//计算奇数位hash值 uneven
	private static long hashCodeUnEven(String s) {
		//计算hash值
		MD5Hash digest = MD5Hash.digest(s.getBytes());
		
		byte[] digest2 = digest.getDigest();
		
		//通过hash值计算
		long ss = 1;
		for (int i = 0; i < digest2.length; i++) {
			byte b = digest2[i];
			//排除为0的情况
			if(b == 0) 
				continue;
			
			//只处理奇数位
			if(i%2 == 0)
				continue;
			ss *= b;
		}
		return Math.abs(ss%CONSISTAN_HASH);
	}
	
	//计算偶数位hash值 even
	private static long hashCodeEven(String s) {
		//计算hash值
		MD5Hash digest = MD5Hash.digest(s.getBytes());
		
		byte[] digest2 = digest.getDigest();
		
		//通过hash值计算
		long ss = 1;
		for (int i = 0; i < digest2.length; i++) {
			byte b = digest2[i];
			//排除0的情况
			if(b == 0) 
				continue;
			//只处理偶数位
			if(i%2 != 0)
				continue;
			ss *= b;
		}
		return Math.abs(ss%CONSISTAN_HASH);
	}
}