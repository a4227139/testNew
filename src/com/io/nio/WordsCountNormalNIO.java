package com.io.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsCountNormalNIO {

	public static Map<String, Integer> map = new HashMap<String, Integer>();
	static final int BUFFER_SIZE = 1024 * 10;
	static byte[] left = new byte[0];
	static final int LF = 10;// 换行符 ASCII
	static final int CR = 13;// 回车符 ASCII

	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception {
		File fin = new File("E:\\Alllog.log");
		long start = System.currentTimeMillis();
		FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
		ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
		readFileByLine(fcin, buf);
		long end = System.currentTimeMillis();
		System.out.println("spend time :" + (end - start));
		show();
	}

	public static void readFileByLine(FileChannel fcin, ByteBuffer buf) {
		byte[] lineByte = new byte[0];
		String encode = "GBK";//"UTF-8"
		try {
			while (fcin.read(buf) != -1) {
				int size = buf.position();//读取结束后的位置，相当于读取的长度
				byte[] byteArr = new byte[size];
				buf.rewind();// rewind用于重读,其将position设回0,若不设置,无法使用下面的get方法
				buf.get(byteArr);//读入b
				buf.clear();
				
				int startNum = 0;
				boolean hasLF = false;// 是否有换行符
				for (int i = 0; i < size; i++) {
					if (byteArr[i] == LF) {
						hasLF = true;
						int leftNum = left.length;
						int lineNum = i - startNum;
						lineByte = new byte[leftNum + lineNum];// 数组大小已经去掉换行符
						System.arraycopy(left, 0, lineByte, 0, leftNum);// 填充了lineByte[0]~lineByte[leftNum-1]
						System.arraycopy(byteArr, startNum, lineByte, leftNum, lineNum);// 填充lineByte[leftNum]~lineByte[leftNum+lineNum-1]
						left = new byte[0];
						String line = new String(lineByte,encode);// 一行完整的字符串(过滤了换行和回车)
						deal(line);
						// System.out.println(line);
						// 过滤回车符和换行符
						if (i + 1 < size && byteArr[i + 1] == CR) {
							startNum = i + 2;
						} else {
							startNum = i + 1;
						}
					}
				}
				if (hasLF) {
					left = new byte[byteArr.length - startNum];
					System.arraycopy(byteArr, startNum, left, 0, left.length);
				} else {// 兼容单次读取的内容不足一行的情况
					byte[] toleft = new byte[left.length + byteArr.length];
					System.arraycopy(left, 0, toleft, 0, left.length);
					System.arraycopy(byteArr, 0, toleft, left.length, byteArr.length);
					left = toleft;
				}
			}
			if (left != null && left.length > 0) {// 兼容文件最后一行没有换行的情况
				String line = new String(left, 0, left.length, encode);
				deal(line);
				// System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deal(String line) {
		Pattern pattern = Pattern.compile("[A-Z]?[a-z]+");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			if (map.containsKey(word)) {
				map.put(word, map.get(word) + 1);
			} else {
				map.put(word, 1);
			}
		}
	}

	public static void show() {
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
