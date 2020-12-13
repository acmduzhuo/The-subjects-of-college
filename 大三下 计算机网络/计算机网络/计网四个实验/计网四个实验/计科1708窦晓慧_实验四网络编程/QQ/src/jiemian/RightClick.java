package jiemian;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream;

/**
 * ���Ϊ��ť�Ҽ��˵�
 */
public class RightClick extends PopupMenu implements ActionListener {
    static long qqDelete;

    String qq_sender;
    Message m;
    MenuItem menuItem1 = new MenuItem("��������");
    MenuItem menuItem2 = new MenuItem("ɾ������");
    ObjectOutputStream out;
    /**
     * ����Ҫɾ���ĺ��ѵ�qq
     * @param qq
     */
    RightClick(long qq, Message m0, ObjectOutputStream outputStream,String qq_sender){
        this.qq_sender = qq_sender;
out = outputStream;
        this.m=m0;
        this.qqDelete = qq;
        this.add(menuItem1);
        this.add(menuItem2);
        menuItem1.addActionListener(this);
       menuItem2.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Ϊ���������ϡ���ӹ���
         */
        if(e.getSource()==menuItem1){
            System.out.println("RightClick:"+"�����ɹ���");
            PersonalDatas p =new PersonalDatas(m,out,true);
        }
        /**
         * Ϊ��ɾ�����ѡ���ӹ���
         */
        if(e.getSource()==menuItem2){
            System.out.println("RightClick:ִ��ɾ������");
            try{
              Message m  = new Message();
              m.setQQ1(String.valueOf(qqDelete));
              m.setQQ2(qq_sender);
              m.setType(Types.deleteFriend);

              out.writeObject(m);
              out.flush();
            }catch(Exception e5){
            e5.printStackTrace();
            }

        }
    }
}
