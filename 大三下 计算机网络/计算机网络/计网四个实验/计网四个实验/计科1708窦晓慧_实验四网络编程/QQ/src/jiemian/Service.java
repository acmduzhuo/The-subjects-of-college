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
 * ���������������ٿ��ͻ���
 * @author Junking
 *
 */
public class Service {

	//����һ����̬�ļ��ϣ��˺źͶ�Ӧ�׽��֣�Ϊ��ʵ���������칦��
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
			System.out.println("�������Ѿ��������˿ں�Ϊ10009");
			Socket s =	ss.accept();
			new Thread(new Threads(s)).start();
		}
	
	}
	
}

