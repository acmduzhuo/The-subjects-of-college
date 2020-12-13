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
             * �رտͻ���ʱҲ��ͻ��˷��Ϳͻ��˹ر�ָ��зǳ����ص�ȱ�ݣ��ȴ�����
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
        this.setTitle("TIM��Լ��");
        this.setVisible(true);
        this.setLocationRelativeTo(null);// ����Ļ�м���ʾ(������ʾ)
        this.setLayout(new BorderLayout());// ���ò��ֹ�����
        this.setResizable(false);// ������¼���С
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
        jl_qq.setFont(new Font("����", Font.PLAIN, 35));
        jl_qq.setToolTipText("��������");
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
                MainView.this.jl_qq.setFont(new Font("����", Font.BOLD, 30));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                MainView.this.jl_qq.setFont(new Font("����", Font.PLAIN, 30));

            }
        });

        jp_up.add(jl_qq);

        JButton jbAddFriends = new JButton("��Ӻ���");
        jbAddFriends.setFont(new Font("����", Font.PLAIN, 16));

        jbAddFriends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String qq = JOptionPane.showInputDialog(MainView.this, "��������Ҫ��ӵ�qq�ţ�");
                System.out.println("MainView:Ҫ��ӵ�QQ��" + qq);
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
        jbAddFriends.setContentAreaFilled(false);  //ֻ����ϴ˾�
        jbAddFriends.setForeground(Color.WHITE);

        JButton jbDeleteFriends = new JButton("�ٷ���վ");
        jbDeleteFriends.setFont(new Font("����", Font.PLAIN, 16));
        jbDeleteFriends.setPreferredSize(new Dimension(100, 35));
        jbDeleteFriends.setContentAreaFilled(false);  //ֻ����ϴ˾�
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
        Myfriends.setFont(new Font("����", Font.PLAIN, 30));
        Myfriends.setForeground(Color.black);
        Myfriends.setToolTipText("ˢ�º���");
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
                MainView.this.Myfriends.setFont(new Font("����", Font.BOLD, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                MainView.this.Myfriends.setFont(new Font("����", Font.PLAIN, 30));

            }
        });
        jp_up.add(Myfriends);

        jp_down.add(jbAddFriends);
        jp_down.add(jbDeleteFriends);


//		 �����ѭ������ָ���������ݿ����ж��ٸ����ѣ�������java��������ټ���
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
                /**ע�⣬�ڽ��յ���m1�У�getQQ1()����Ҫ���յĶ����ڸ��������У�������*/
                Message m1 = (Message) in.readObject();
                if (m1.getType() == Types.privateTalk) {
                    //��ȡϵͳʱ��
                    Date day = new Date();
                    SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");

                    //���ջ�ȴ����շ���˷�������Ϣ-----����ʽ����
                    //���յ���Ҫ���䵽�ĺ��ѵ���Ϣ��m.getQQ1()�������ڷ���˵ķ����ߵ���Ϣ
                    //��������д���QQ���ѣ�����ú��ѵĻỰ���Ѿ���
                    System.out.println("MainView:m.getQQ1()" + m1.getQQ1() + "********m.getQQ2()" + m.getQQ2());
                    if (str_chatView.containsKey(m1.getQQ1())) {
                        //�Ӽ������ҵ���Ӧ��qq����Ӧ��ChatViewֵ
                        ChatView c = str_chatView.get(m1.getQQ1());

                        c.jta_up.append(m1.getNickname1() + "  " + df.format(day) + "\n" + m1.getMessage() + "\n");
                    } else {//���������û���ҵ��Ự�򣬼��Ǵ�״̬��û�о�����һ��
                        System.out.println("MainView:" + m.getFriend_name());
                        m1.setFriend_nickname(m.getFriend_nickname());
                        m1.setFriend_name(m.getFriend_name());
                        m1.setNickname1(m.getNickname1());

                        ChatView c = new ChatView(Long.valueOf(m1.getQQ1()), m1, out, m.getNickname1());
                        str_chatView.put(m1.getQQ1(), c);
                        c.jta_up.append(m1.getNickname1() + "  " + df.format(day) + "\n" + m1.getMessage() + "\n");
                    }
                    System.out.println("MainView:��������" + (++count) + "��");
                }
                if (m1.getType() == Types.refrush) {
                    mFrush=m1;
                    frushMain();
                }
                if (m1.getType() == Types.deleteFriendSucceed) {

                }
                if (m1.getType() == Types.StartSen) {
                    int i = JOptionPane.showConfirmDialog(null,"�Է������ļ����Ƿ���ܣ�");
                    if (i==0){
                        new Thread(new FileServer()).start();
                    }
                }
                //���յ����صļӺ��ѵ���Ϣʱ
                if (m1.getType() == Types.recAddFriends) {

                    if (m1.getIsAdd()) {
                        JOptionPane.showMessageDialog(null, "��ӳɹ���");
                        long qqFriend = Long.valueOf(m1.getQQ2());
                        Friend_list jbF = new Friend_list(qqFriend, m, out, m1.getNickname2(),m.getQQ1());
                        System.out.println("MainView:~~~~~~~~~~~~~~~~" + m1.getNickname2());
                        jp_cen.add(jbF);
                        m1.setIsAdd(false);

                        this.validate();
                    } else {
//                        JOptionPane.showMessageDialog(null,"���ʧ�ܣ�");

                    }
                }
                if(m1.getType() == Types.updatePasswordSucceed){
                    JOptionPane.showMessageDialog(null,"�����޸ĳɹ���");
                }
                if (m1.getType() == Types.findFriendsDatas){
                    m_friendDatas = m1;
                    System.out.println("MainView:�Ѿ����ܵ����ѵ�m����");
                }
                if(m1.getType() == Types.savePersonalDatasSucceed){
                    JOptionPane.showMessageDialog(null,"���������޸ĳɹ���");
                }

                if (m1.getType() == Types.StartSendFile) {
                    int port = m1.getPort();

                    String ip = m1.getIp();
                    //System.out.println("MainView:�˿ں�"+port+"ip��"+ip);
                    Socket s = new Socket(ip, 10010);
                    System.out.println("ChatView:�ļ����俪ʼ��");
                    FileDialog fileDialog = new FileDialog(this, "��ѡ��Ҫ���͵��ļ�", FileDialog.LOAD);
                    fileDialog.setVisible(true);
                    String fileName = fileDialog.getFile();
                    String fileDirectory = fileDialog.getDirectory();
                    File f = new File(fileDirectory, fileName);//���򿪵��ļ����浽File������
                    OutputStream o = s.getOutputStream();
                    byte[] nameB = fileName.getBytes();

                    //������ռ���ֽ�����intռ4���ֽ�
                    int ib = nameB.length;
                    byte[] id_byte = IntToByte(ib);
                    o.write(id_byte);
                    o.flush();

                    //���������ֽڷ�ʽ����ȥ
                    o.write(nameB);
                    o.flush();


                    //�������Ա����ϵ��ļ���inputͨ��
                    BufferedInputStream bin = new BufferedInputStream(new FileInputStream(f));

                    byte[] buff = new byte[1024];
                    int len = 0;
                    while ((len = bin.read(buff)) != -1) {
                        o.write(buff, 0, len);

                        o.flush();
                    }
                    s.shutdownOutput();


                    System.out.println("ChatView:�ļ�������ϣ�");
                    o.close();
                    bin.close();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * �ַ���ת�ֽ�����
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
     * �ֽ�����ת�ַ���
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
     * byteתint
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
     * intתbyte����
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

    //ˢ��
    public void  frushMain() {
        this.dispose();
        new MainView(out,in,mFrush,client);

    }
}
