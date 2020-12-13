package jiemian;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Chat {
   static Message m123;
     String s_receive="";
    public static String receChat( ObjectInputStream objectInputStream){

        try {


            System.out.println("Chat:你倒是接收啊！");
            m123 = (Message)objectInputStream.readObject();

            System.out.println("Chat:嘿嘿接收了。从服务器那边接收到的好友发来的信息是"+m123.getMessage()+ m123.getQQ2());




        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return m123.getMessage();
    }

     public void receiveMessage(JTextArea jta, ObjectInputStream objectInputStream){
         System.out.println("ChatView:这是在监听biu~里11111111");
         s_receive += Chat.receChat(objectInputStream)+"\n";
         System.out.println("ChatView:这是在监听biu~里2222222");
         jta.setText(s_receive);
     }
}
