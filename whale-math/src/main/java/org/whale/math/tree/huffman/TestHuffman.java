package org.whale.math.tree.huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 静态霍夫曼编码
 * @author Administrator
 */
public class TestHuffman {

	public static void main(String[] args) {
		
		Map<Character, Integer> weigths = new HashMap();
		String s = "abecdabecabea";
		
		//拆分字符
		char[] charArray = s.toCharArray();
		
		//计算权重
		for (int i = 0; i < charArray.length; i++) {
			
			if(weigths.get(charArray[i]) != null) {
				weigths.put(charArray[i], weigths.get(charArray[i])+1);
			}else {
				weigths.put(charArray[i], 1);
			}
		}
		System.out.println("weigths:"+weigths);
		
		//存取权重
		Collection<Integer> values = weigths.values();
		Set<Integer> weigthset = new TreeSet<>();
		Iterator<Integer> iterator = values.iterator();
		while(iterator.hasNext()) {
			weigthset.add(iterator.next());
		}
		System.out.println("weigthset:"+weigthset);
		
		//排序
		Set<Entry<Character,Integer>> entrySet = weigths.entrySet();
		Map<Integer, List<Character>> sortMap = new HashMap<>();
		
		for (Entry<Character, Integer> entry : entrySet) {
			
			Character key = entry.getKey();
			Integer value = entry.getValue();
			
			List<Character> list = sortMap.get(value);
			
			if(list == null) {
				List<Character> charList = new ArrayList<>();
				charList.add(key);
				sortMap.put(value, charList);
			}else {
				list.add(key);
				sortMap.put(value, list);
			}
		}
		System.out.println("按照权重进行归集数据:"+sortMap);
		
		List<Character> list = new ArrayList<>();
		
		Iterator<Integer> iterator2 = weigthset.iterator();
		
		while (iterator2.hasNext()) {
			Integer integer = (Integer) iterator2.next();
			List<Character> list2 = sortMap.get(integer);
			list.addAll(list2);
		}
		
		//按权重排序
		System.out.println(list);
		
		Character c1 = null;
		Character c2 = null;
		
		Iterator<Character> iterator3 = list.iterator();
		
		while (iterator3.hasNext()) {
			
			if(c1 == null) {
				c1 = iterator3.next();
			}
			
			if(c2 == null) {
				c2 = iterator3.next();
				Integer weigth_add = weigths.get(c1)+weigths.get(c2);
				System.out.println(weigth_add);
				c1 = null;
				c2 = null;
			}
		}
	}
}
