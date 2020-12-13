package jiemian;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainView extends JFrame implements Runnable {

    JLabel Myfriends;
    public Thread r;
    public String myname;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public Socket client;
    GradientPanel jp_cen;
    public Message m;
    public Message mFrush;
    public static Message m_friendDatas;
    JLabel jl_qq;
    public static HashMap<String, ChatView> str_chatView = new HashMap<String, ChatView>();
    public MainView(ObjectOutputStream out0, ObjectInputStream in0, Message m0, Socket client) {
        this.m = m0;
        this.client = client;
        this.out = out0;
        this.in = in0;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            /**
             * 关闭客户端时也向客户端发送客户端关闭指令，有非常严重的缺陷，等待更正
             * @param e
             */
            public void windowClosing(WindowEvent e) {
                Message m2 = new Message();
                m2.setType(Types.logout);
                try {
                    out.writeObject(m2);
                    System.out.println("MainView:" + m2.getType());
                    out.flush();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                System.exit(0);
            }
        });
        this.setBounds(500, 500, 300, 600);
        this.setTitle("TIM简约版");
        this.setVisible(true);
        this.setLocationRelativeTo(null);// 在屏幕中间显示(居中显示)
        this.setLayout(new BorderLayout());// 设置布局管理器
        this.setResizable(false);// 锁定登录框大小
//        JPanel jp_up = new JPanel();
        //JPanel jp_cen = new JPanel();
        GradientPanel jp_up = new GradientPanel(new FlowLayout());
        jp_up.i = 2;
        jp_cen = new GradientPanel(new FlowLayout());
        jp_cen.i = 1;
        GradientPanel jp_down = new GradientPanel(new FlowLayout());
        jp_down.i = 3;
        jp_up.setPreferredSize(new Dimension(250, 100));
        jp_cen.setPreferredSize(new Dimension(225, 420));
        jp_down.setPreferredSize(new Dimension(250, 80));

        JScrollPane jsp_cen;
        jsp_cen = new JScrollPane();
        jsp_cen.setPreferredSize(new Dimension(250, 420));
        jsp_cen.setViewportView(jp_cen);

        //jp_down.setLayout(null);

        jl_qq = new JLabel("     " + m.getNickname1() + "     ");
        jl_qq.setFont(new Font("黑体", Font.PLAIN, 35));
        jl_qq.setToolTipText("个人资料");
        jl_qq.setForeground(Color.WHITE);
        jl_qq.setBounds(new Rectangle(250, 70));
        jl_qq.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainView.this.validate();
                new PersonalDatas(m,out,false);
                MainView.this.validate();
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                MainView.this.jl_qq.setFont(new Font("黑体", Font.BOLD, 30));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                MainView.this.jl_qq.setFont(new Font("黑体", Font.PLAIN, 30));

            }
        });

        jp_up.add(jl_qq);

        JButton jbAddFriends = new JButton("添加好友");
        jbAddFriends.setFont(new Font("黑体", Font.PLAIN, 16));

        jbAddFriends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String qq = JOptionPane.showInputDialog(MainView.this, "请输入你要添加的qq号：");
                System.out.println("MainView:要添加的QQ是" + qq);
                Message mAdd = new Message();
                mAdd.setType(Types.addFriends);
                mAdd.setQQ1(m.getQQ1());
                mAdd.setQQ2(qq);

                try {
                    out.writeObject(mAdd);
                    out.flush();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }
        });

        jbAddFriends.setPreferredSize(new Dimension(100, 35));
        jbAddFriends.setContentAreaFilled(false);  //只须加上此句
        jbAddFriends.setForeground(Color.WHITE);

        JButton jbDeleteFriends = new JButton("官方网站");
        jbDeleteFriends.setFont(new Font("黑体", Font.PLAIN, 16));
        jbDeleteFriends.setPreferredSize(new Dimension(100, 35));
        jbDeleteFriends.setContentAreaFilled(false);  //只须加上此句
        jbDeleteFriends.setForeground(Color.WHITE);
        jbDeleteFriends.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				java.net.URI uri;
				try {
					uri = new java.net.URI("http://www.qq.com");
					java.awt.Desktop.getDesktop().browse(uri);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

        Myfriends = new JLabel("  My Friends  ");
        Myfriends.setBounds(new Rectangle(250, 100));
        Myfriends.setFont(new Font("黑体", Font.PLAIN, 30));
        Myfriends.setForeground(Color.black);
        Myfriends.setToolTipText("刷新好友");
        Myfriends.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Message m0 = new Message();
                m0.setQQ1(m.getQQ1());
                m0.setType(Types.refrush);
                try{
                   out.writeObject(m0);
                   out.flush();
                }catch(Exception e0){
                e0.printStackTrace();
                }

                MainView.this.validate();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MainView.this.Myfriends.setFont(new Font("黑体", Font.BOLD, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                MainView.this.Myfriends.setFont(new Font("黑体", Font.PLAIN, 30));

            }
        });
        jp_up.add(Myfriends);

        jp_down.add(jbAddFriends);
        jp_down.add(jbDeleteFriends);


//		 这里的循环次数指的是在数据库中有多少个好友，可以在java程序这边再计量
        String[] s = m.getFriend_name();
        for (int i = 0; i < m.getFriend_num(); i++) {
            long id_QQ = new Long(s[i]);
            String friendsNickname = m.getFriend_nickname()[i];
            System.out.println("MainView:" + friendsNickname);
            jp_cen.add(new Friend_list(id_QQ, m, out, friendsNickname,m.getQQ1()));
        }
        this.add(jp_up, BorderLayout.NORTH);
        this.add(jsp_cen, BorderLayout.CENTER);
        this.add(jp_down, BorderLayout.SOUTH);
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            try {
                /**注意，在接收到的m1中，getQQ1()是需要接收的对象，在个人聊天中，即好友*/
                Message m1 = (Message) in.readObject();
                if (m1.getType() == Types.privateTalk) {
                    //获取系统时间
                    Date day = new Date();
                    SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");

                    //接收或等待接收服务端发来的消息-----阻塞式方法
                    //接收到需要传输到的好友的信息，m.getQQ1()是来自于服务端的发送者的信息
                    //如果集合中存在QQ好友，即与该好友的会话框已经打开
                    System.out.println("MainView:m.getQQ1()" + m1.getQQ1() + "********m.getQQ2()" + m.getQQ2());
                    if (str_chatView.containsKey(m1.getQQ1())) {
                        //从集合中找到对应的qq所对应的ChatView值
                        ChatView c = str_chatView.get(m1.getQQ1());

                        c.jta_up.append(m1.getNickname1() + "  " + df.format(day) + "\n" + m1.getMessage() + "\n");
                    } else {//如果集合中没有找到会话框，即非打开状态，没有就生成一个
                        System.out.println("MainView:" + m.getFriend_name());
                        m1.setFriend_nickname(m.getFriend_nickname());
                        m1.setFriend_name(m.getFriend_name());
                        m1.setNickname1(m.getNickname1());

                        ChatView c = new ChatView(Long.valueOf(m1.getQQ1()), m1, out, m.getNickname1());
                        str_chatView.put(m1.getQQ1(), c);
                        c.jta_up.append(m1.getNickname1() + "  " + df.format(day) + "\n" + m1.getMessage() + "\n");
                    }
                    System.out.println("MainView:俺运行了" + (++count) + "次");
                }
                if (m1.getType() == Types.refrush) {
                    mFrush=m1;
                    frushMain();
                }
                if (m1.getType() == Types.deleteFriendSucceed) {

                }
                if (m1.getType() == Types.StartSen) {
                    int i = JOptionPane.showConfirmDialog(null,"对方发送文件，是否接受？");
                    if (i==0){
                        new Thread(new FileServer()).start();
                    }
                }
                //当收到返回的加好友的信息时
                if (m1.getType() == Types.recAddFriends) {

                    if (m1.getIsAdd()) {
                        JOptionPane.showMessageDialog(null, "添加成功！");
                        long qqFriend = Long.valueOf(m1.getQQ2());
                        Friend_list jbF = new Friend_list(qqFriend, m, out, m1.getNickname2(),m.getQQ1());
                        System.out.println("MainView:~~~~~~~~~~~~~~~~" + m1.getNickname2());
                        jp_cen.add(jbF);
                        m1.setIsAdd(false);

                        this.validate();
                    } else {
//                        JOptionPane.showMessageDialog(null,"添加失败！");

                    }
                }
                if(m1.getType() == Types.updatePasswordSucceed){
                    JOptionPane.showMessageDialog(null,"密码修改成功！");
                }
                if (m1.getType() == Types.findFriendsDatas){
                    m_friendDatas = m1;
                    System.out.println("MainView:已经接受到好友的m对象");
                }
                if(m1.getType() == Types.savePersonalDatasSucceed){
                    JOptionPane.showMessageDialog(null,"个人资料修改成功！");
                }

                if (m1.getType() == Types.StartSendFile) {
                    int port = m1.getPort();

                    String ip = m1.getIp();
                    //System.out.println("MainView:端口号"+port+"ip号"+ip);
                    Socket s = new Socket(ip, 10010);
                    System.out.println("ChatView:文件传输开始了");
                    FileDialog fileDialog = new FileDialog(this, "请选择要发送的文件", FileDialog.LOAD);
                    fileDialog.setVisible(true);
                    String fileName = fileDialog.getFile();
                    String fileDirectory = fileDialog.getDirectory();
                    File f = new File(fileDirectory, fileName);//将打开的文件保存到File对象中
                    OutputStream o = s.getOutputStream();
                    byte[] nameB = fileName.getBytes();

                    //名字所占的字节数，int占4个字节
                    int ib = nameB.length;
                    byte[] id_byte = IntToByte(ib);
                    o.write(id_byte);
                    o.flush();

                    //把名字以字节方式传过去
                    o.write(nameB);
                    o.flush();


                    //创建来自本机上的文件的input通道
                    BufferedInputStream bin = new BufferedInputStream(new FileInputStream(f));

                    byte[] buff = new byte[1024];
                    int len = 0;
                    while ((len = bin.read(buff)) != -1) {
                        o.write(buff, 0, len);

                        o.flush();
                    }
                    s.shutdownOutput();


                    System.out.println("ChatView:文件发送完毕！");
                    o.close();
                    bin.close();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 字符串转字节数组
     *
     * @param str
     * @return
     */
    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }
    /**
     * 字节数组转字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }

    /**
     * byte转int
     *
     * @param b
     * @return
     */
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    /**
     * int转byte数组
     *
     * @param
     * @return
     */
    public static byte[] IntToByte(int num) {

        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xff);
        bytes[1] = (byte) ((num >> 16) & 0xff);
        bytes[2] = (byte) ((num >> 8) & 0xff);
        bytes[3] = (byte) (num & 0xff);
        return bytes;
    }

    //刷新
    public void  frushMain() {
        this.dispose();
        new MainView(out,in,mFrush,client);

    }
}
