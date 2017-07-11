package com.io.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChatRoomServer {

	private Selector selector = null;
	static final int port = 9999;
	private Charset charset = Charset.forName("UTF-8");
	private static HashSet<String> users = new HashSet<String>();
	private static String USER_EXIST = "system message: user exist, please change a name";
	// 相当于自定义协议格式，与客户端协商好
	private static String USER_CONTENT_SPILIT = "#@#";

	public void init() throws IOException {
		selector = Selector.open();
		ServerSocketChannel server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress(port));
		// 非阻塞的方式
		server.configureBlocking(false);
		// 注册到选择器上，设置为监听状态
		server.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server is listening now...");
		while (true) {
			selector.select();
			Iterator<SelectionKey> keyIterator = selector.selectedKeys()
					.iterator(); // 可以通过这个方法，知道可用通道的集合
			while (keyIterator.hasNext()) {
				SelectionKey sk = keyIterator.next();
				keyIterator.remove();
				dealWithSelectionKey(server, sk);
			}
		}
	}

	public void dealWithSelectionKey(ServerSocketChannel server, SelectionKey sk)
			throws IOException {
		if (sk.isAcceptable()) {
			SocketChannel sc = server.accept();// 收到一个连接请求，新建一个SocketChannel
			sc.configureBlocking(false);// 设置非阻塞模式，selector都要非阻塞
			// 注册到selector上并设置对读操作感兴趣
			sc.register(selector, SelectionKey.OP_READ);
			System.out.println("Server is listening from client :"
					+ sc.getRemoteAddress());
			sc.write(charset.encode("Please input your name."));
		}
		// 处理来自客户端的数据读取请求
		if (sk.isReadable()) {
			// 返回该SelectionKey对应的 Channel，其中有数据需要读取
			SocketChannel sc = (SocketChannel) sk.channel();
			ByteBuffer buff = ByteBuffer.allocate(1024);
			StringBuffer content = new StringBuffer();
			try {
				while (sc.read(buff) > 0) {
					buff.flip();
					content.append(charset.decode(buff));
				}
				System.out.println("Server is listening from client "
						+ sc.getRemoteAddress() + " data rev is: " + content);
			} catch (IOException io) {
				sk.cancel();
				if (sk.channel() != null) {
					sk.channel().close();
				}
			}
			if (content.length() > 0) {
				String[] arrayContent = content.toString().split(
						USER_CONTENT_SPILIT);
				if (arrayContent != null && arrayContent.length == 1) {// 注册用户
					String name = arrayContent[0];
					if (users.contains(name)) {
						sc.write(charset.encode(USER_EXIST));
					} else {
						users.add(name);
						int num = OnlineNum(selector);
						String message = "welcome " + name
								+ " to chat room! Online numbers:" + num;
						BroadCast(selector, null, message);
					}
				} else if (arrayContent != null && arrayContent.length > 1) {// 注册完了，发送消息
					String name = arrayContent[0];
					String message = content.substring(name.length()
							+ USER_CONTENT_SPILIT.length());
					message = name + " say " + message;
					if (users.contains(name)) {
						BroadCast(selector, sc, message);
					}
				}
			}

		}
	}

	// TODO 要是能检测下线，就不用这么统计了
	public static int OnlineNum(Selector selector) {
		int res = 0;
		for (SelectionKey key : selector.keys()) {
			Channel targetchannel = key.channel();
			if (targetchannel instanceof SocketChannel) {
				res++;
			}
		}
		return res;
	}

	public void BroadCast(Selector selector, SocketChannel srcChannel,
			String content) throws IOException {
		// 广播数据到所有的SocketChannel中
		for (SelectionKey key : selector.keys()) {
			Channel targetchannel = key.channel();
			// 不回发内容给srcChannel
			if (targetchannel instanceof SocketChannel
					&& targetchannel != srcChannel) {
				SocketChannel dest = (SocketChannel) targetchannel;
				dest.write(charset.encode(content));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new ChatRoomServer().init();
	}
}