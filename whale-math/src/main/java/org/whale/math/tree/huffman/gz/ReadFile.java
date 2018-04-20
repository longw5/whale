package org.whale.math.tree.huffman.gz;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 解压文件 从压缩文件中读入数据解压
 * 
 * @author Andrew
 * 
 */
public class ReadFile {
	/**
	 * 码表 Integter: 字节 [0,255) String： 哈夫曼编码
	 */
	private Map<Integer, String> code_Map = new HashMap<Integer, String>();

	public static void main(String[] args) {
		ReadFile readFile = new ReadFile();
		readFile.readFile();
	}

	/**
	 * 第一步： 从文件中读出码表
	 * 
	 * @param dataInput
	 *            基本数据输入流
	 * 
	 */
	private void readMap(DataInputStream dataInput) {

		try {
			// 读出码表的长度
			int size_Map = dataInput.readInt();
			/**************** 读出码表信息 ************************************/
			for (int i = 0; i < size_Map; i++) {
				int data = dataInput.readInt();// 读入字节信息
				String str = "";// 哈夫曼编码
				// 哈夫曼编码长度,存储时以字符写入
				byte codeSize = dataInput.readByte();
				for (byte j = 0; j < codeSize; j++) {
					str += dataInput.readChar();
				}
				System.out.println("&&&&&&&&&&>>>>"+data+"!!!!!!!>>"+str);
				code_Map.put(data, str);
			}
			/***************************************************************/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 第二步： 解压正式文件
	 */
	private void readFile() {
		try {
			// 文件输入流
			InputStream input = new FileInputStream("E:\\huffman\\huffman.log");
			// BufferedInputStream bIn = new BufferedInputStream(input);
			DataInputStream dInput = new DataInputStream(input);
			// 调用读出码表的方法
			this.readMap(dInput);
			byte zerofill = dInput.readByte();// 读出文件补零个数
			System.out.println("补零个数》》》>>>>"+zerofill);
			// 文件输出流
			OutputStream out = new FileOutputStream("E:\\huffman\\huffmanzz.log");
			
			String transString = "";//接收用于匹配码表中哈夫曼编码的字符串
			String waiteString = "";//接收截取哈夫曼编码后剩余的字符串
			
			/***********************************不耗内存的方法*****************************************/
			int readCode = input.read();//从压缩文件中读出一个数据
			int size = input.available();
			for(int j=0; j<=size; j++){
				System.out.println("readCodereadCodereadCode》》>>>>"+readCode);
				waiteString += this.changeIntToBinaryString(readCode);//将读到的整数转化为01字符串
				for(int i=0; i<waiteString.length(); i++){
					//将从文件中读出的01串一个一个字节的截取添加与码表中的哈夫曼编码比较
					transString += waiteString.charAt(i);
					if(this.searchHC(transString, out)){
//						waiteString = waiteString.substring( i+1 );
						transString = "";
					}
				}
				waiteString = "";
				readCode = input.read();
				if(j == size-1){
					break;
				}
			}
			/************************************处理最后一个字***************************************/
//			int lastByte = input.read();
			String lastWord = this.changeIntToBinaryString(readCode);
			waiteString = transString + lastWord.substring(0, 8-zerofill);
			transString = "";
			for(int i=0; i<waiteString.length(); i++){
				//将从文件中读出的01串一个一个字节的截取添加与码表中的哈夫曼编码比较
				transString += waiteString.charAt(i);
				if(this.searchHC(transString, out)){
//					waiteString = waiteString.substring( i+1 );
					transString = "";
				}
			}
//			this.searchHC(transString, out);
			/***********************************队列法，耗内存****************************************/
//			int readCode = input.read();//从压缩文件中读出一个数据
//			List<Character> list = new ArrayList<Character>();
//			while(-1 != readCode){
//				String str = this.changeIntToBinaryString(readCode);
//				for(int i=0; i<str.length(); i++){
//					list.add(str.charAt(i));
//				}
//				readCode = input.read();
//			}
//			for(int j=0; j<list.size()-zerofill; j++){
//				transString += list.get(j);
//				if(this.searchHC(transString, out)){
//					transString = "";
//				}
//			}
			/*****************************************************************************************/
			input.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将从文件中读到的01 串与码表中的哈夫曼编码比较
	 * 若在码表中含有与之对应的哈夫曼编码则将码表中对应的
	 * 数据写入解压文件，否则不写入
	 * @param str  从文件中读到的01 字符串
	 * @param out  文件输出流
	 * @return     若写入文件返回true，否则返回false
	 * @throws IOException  写入发生错误时抛出异常
	 */
	private boolean searchHC(String str, OutputStream out) throws IOException{
		Set<Integer> set = code_Map.keySet();
		Iterator<Integer> p = set.iterator();
		while (p.hasNext()) {
			Integer key = p.next();//获得码表中的字节数据
			String hfmCode = code_Map.get(key);//获得哈夫曼编码
			if(hfmCode.equals(str)){
				out.write(key);
				return true;
			}
		}
		return false;
	}
	/**
	 * 根据 "除2取余，逆序排列"法
	 * 将十进制数字转化为二进制字符串
	 * @param a   要转化的数字
	 * @return    01 字符串
	 */
	private String changeIntToBinaryString(int a) {
		int b = a;
		int count = 0; //记录 a 可转化为01串的个数，在不够8为时便于补零
		String str = "";// 逆序二进制字符串
		String exmpStr = "";// 顺序二进制字符串

		while (a != 0) {
			b = a/2;
			if (a % 2 != 0) {
				str += 1;
			} else {
				str += 0;
			}
			a = b;
			count++;
		}
		//不够8位的地方补零
		for (int i = 0; i < 8 - count; i++) {
			str += 0;
		}
		//将转化后的二进制字符串正序
		for (int j = 7; j >= 0; j--) {
			System.out.print(str.charAt(j));
			exmpStr += str.charAt(j);
		}
		System.out.println("转化后的字符串>>>>>>>>>"+exmpStr);
		return exmpStr;
	}
}
