package jiemian;



import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �ļ����䡪���ͻ��ˣ���������һ���˿ڣ����ڽ����ļ�
 */
public class FileServer implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket ss=new ServerSocket(10010);
            Socket s=ss.accept();
            InputStream in = s.getInputStream();
            System.out.println(s.getInetAddress()+"--------------");

            // ��ȡ�������ݳ���
            int len;
            InputStream ini = new BufferedInputStream(in);
            byte[] bs = new byte[1024];
            byte[] lengthB= new byte[4];
            System.out.println();
            ini.read(lengthB);


            int length = MainView.byteArrayToInt(lengthB);

            byte[] byteName =new byte[length];
            ini.read(byteName);
            String sname = MainView.byteArrayToStr(byteName);
            System.out.println("length"+length+"       sname::::::"+sname);

            File save = new File("E:\\" + sname);
            if (!save.exists()) {
                // �ȵõ��ļ����ϼ�Ŀ¼���������ϼ�Ŀ¼���ڴ����ļ�
                save.getParentFile().mkdir();
                save.createNewFile();
            }
            if (!save.exists()) {
                save.mkdirs();
            }
            OutputStream f_output = new FileOutputStream(save);
            // ��ʼ��ȡ



            while ((len = ini.read(bs)) != -1) {
                System.out.println(new String(bs));
                System.out.println(len);
                f_output.write(bs, 0, len);
                f_output.flush();
            }
            f_output.close();
            in.close();
            s.close();
            ss.close();
            System.out.println("��������");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
