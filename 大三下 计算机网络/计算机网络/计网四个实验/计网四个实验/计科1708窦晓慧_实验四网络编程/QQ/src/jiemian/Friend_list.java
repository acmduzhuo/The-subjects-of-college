package jiemian;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;


public class Friend_list extends JButton implements MouseListener {
long id_QQ;
Message m0;
String qq_sender;
ObjectOutputStream objectOutputStream;
    String nickname0;

	/**
	 * 传入qq号，方便监听按钮 来生成会话框
	 * @param id_QQ
	 */
	public Friend_list(final long id_QQ, Message m, ObjectOutputStream out,String nickname,String qq_sender) {
		this.qq_sender = qq_sender;
	    objectOutputStream = out;
this.nickname0 = nickname;
		this.id_QQ = id_QQ;
		this.setText(nickname);
        this.setFont(new Font("黑体", Font.PLAIN, 16));
		this.m0=m;
		m0.setQQ2(""+id_QQ);
		System.out.println("Friend_list:QQ2"+m0.getQQ2());
        this.setToolTipText(id_QQ+"");
		this.setPreferredSize(new Dimension(280, 35));
		this.setContentAreaFilled(false);  //只须加上此句
		this.addMouseListener(this);


	}


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1){
            if (e.getSource()==this){
                ChatView cv =new ChatView(id_QQ,m0,objectOutputStream,nickname0);
                //向集合中添加好友的qq和对应会话框
                MainView.str_chatView.put(String.valueOf(id_QQ),cv);
                System.out.println("Friend_list:触发好友按钮之后集合为"+MainView.str_chatView.toString());
            }
        }else if(e.getButton()==MouseEvent.BUTTON3){
            Message mf = new Message();
            mf.setType(Types.findFriendsDatas);
            mf.setQQ1(String.valueOf(id_QQ));
            try{
                objectOutputStream.writeObject(mf);
                objectOutputStream.flush();
                Thread.sleep(200);//睡一秒钟等待服务端返回m对象
            }catch(Exception ef){
                ef.printStackTrace();
            }

            RightClick pop = new RightClick(id_QQ,MainView.m_friendDatas,objectOutputStream,qq_sender);

            Friend_list.this.add(pop);
            pop.show(this,e.getX(),e.getY());

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
