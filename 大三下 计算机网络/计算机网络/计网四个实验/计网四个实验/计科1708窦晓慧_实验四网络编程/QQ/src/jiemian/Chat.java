package jiemian;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Chat {
   static Message m123;
     String s_receive="";
    public static String receChat( ObjectInputStream objectInputStream){

        try {


            System.out.println("Chat:�㵹�ǽ��հ���");
            m123 = (Message)objectInputStream.readObject();

            System.out.println("Chat:�ٺٽ����ˡ��ӷ������Ǳ߽��յ��ĺ��ѷ�������Ϣ��"+m123.getMessage()+ m123.getQQ2());




        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return m123.getMessage();
    }

     public void receiveMessage(JTextArea jta, ObjectInputStream objectInputStream){
         System.out.println("ChatView:�����ڼ���biu~��11111111");
         s_receive += Chat.receChat(objectInputStream)+"\n";
         System.out.println("ChatView:�����ڼ���biu~��2222222");
         jta.setText(s_receive);
     }
}
