package jiemian;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 服务器，开启后再开客户端
 * @author Junking
 *
 */
public class Service {

	//创建一个静态的集合（账号和对应套接字）为了实现在线聊天功能
	public static HashMap<String,Socket> map_qqSoc = new HashMap<String,Socket>();
	public static HashMap<String,Threads> map_qqTres = new HashMap<String,Threads>();
	public  static void  add(String s , Threads t){
		map_qqTres.put(s,t);
	}
	public  static void  deleteS(String s ){
		map_qqTres.remove(s);
	}

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(10009);
		while(true)
		{
			System.out.println("服务器已经开启，端口号为10009");
			Socket s =	ss.accept();
			new Thread(new Threads(s)).start();
		}
	
	}
	
}

