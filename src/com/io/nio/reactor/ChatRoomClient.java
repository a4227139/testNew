package com.io.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ChatRoomClient {

	private Selector selector = null;
	static final int port = 9999;
	private Charset charset = Charset.forName("UTF-8");
	private SocketChannel sc = null;
	private String name = "";
	private static String USER_EXIST = "system message: user exist, please change a name";
	private static String USER_CONTENT_SPILIT = "#@#";

	public void init() throws IOException {
		selector = Selector.open();
		// ����Զ��������IP�Ͷ˿�
		sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
		sc.configureBlocking(false);
		sc.register(selector, SelectionKey.OP_READ);
		// ����һ�����߳�����ȡ�ӷ������˵�����
		new Thread(new ClientThread()).start();
		// �����߳��� �Ӽ��̶�ȡ�������뵽��������
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if ("".equals(line))
				continue; // ������������Ϣ
			if ("".equals(name)) {
				name = line;
				line = name + USER_CONTENT_SPILIT;
			} else {
				line = name + USER_CONTENT_SPILIT + line;
			}
			sc.write(charset.encode(line));// sc����дҲ�ܶ��������д
		}

	}

	private class ClientThread implements Runnable {
		public void run() {
			try {
				while (true) {
					selector.select();
					Iterator<SelectionKey> keyIterator = selector
							.selectedKeys().iterator(); // ����ͨ�����������֪������ͨ���ļ���
					while (keyIterator.hasNext()) {
						SelectionKey sk = (SelectionKey) keyIterator.next();
						keyIterator.remove();
						dealWithSelectionKey(sk);
					}
				}
			} catch (IOException io) {
			}
		}

		private void dealWithSelectionKey(SelectionKey sk) throws IOException {
			if (sk.isReadable()) {
				// ʹ�� NIO ��ȡ
				// Channel�е����ݣ������ȫ�ֱ���sc��һ���ģ���Ϊֻע����һ��SocketChannel,sc����дҲ�ܶ�������Ƕ�
				SocketChannel sc = (SocketChannel) sk.channel();
				ByteBuffer buff = ByteBuffer.allocate(1024);
				String content = "";
				while (sc.read(buff) > 0) {
					buff.flip();
					content += charset.decode(buff);
				}
				// ��ϵͳ����֪ͨ�����Ѿ����ڣ�����Ҫ�����ǳ�
				if (USER_EXIST.equals(content)) {
					name = "";
				}
				System.out.println(content);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new ChatRoomClient().init();
	}
}