package jiemian;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.*;


public class Denglu extends JFrame {
    public static String Nid = "δ��ֵa ";
    public static String Nname = "δ��ֵa ";
    public static String Npassword = "δ��ֵa ";
    private ObjectInputStream in;//������
    private ObjectOutputStream out;//�����
    private Socket s;// �׽���
    private Message mess;
    JTextField name;
    JPanel zujianJpan;
    JPasswordField password;
    JLabel imagelable;

    public Denglu() {
        try{
            //Ŀ���������ip�Ͷ˿ںŵ��׽���
            s = new Socket("127.0.0.1", 10009);
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());

        }catch(Exception e){
            e.printStackTrace();
        }

        this.setUndecorated(true);//ȥ��������
        this.setDragable(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("QQ��¼");
        this.setBounds(600, 800, 490, 370);
        this.setLocationRelativeTo(null);// ����Ļ�м���ʾ(������ʾ)
        this.setLayout(null);// ���ò��ֹ�����
        this.setResizable(false);// ������¼���С

        zujianJpan = new JPanel();
        zujianJpan.setSize(490 ,270);
        zujianJpan.setLocation(0,200);
        zujianJpan.setLayout(null);
        zujianJpan.setBackground(new Color(235,242,249));

        ImageIcon exitImg = new ImageIcon("exit.png");// ��ӹر�ͼ��
        JLabel exit = new JLabel(exitImg);
        exit.setBounds(new Rectangle(450,15,20,20));
        exit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        ImageIcon duangImg = new ImageIcon("Duang.png");// �����С��ͼ��
        JLabel duang = new JLabel(duangImg);
        duang.setBounds(new Rectangle(410,15,20,20));
       duang.addMouseListener(new MouseListener() {

           /**
            * ���������
            * @param e
            */
           @Override
           public void mouseClicked(MouseEvent e) {
                Denglu.this.setExtendedState(Denglu.this.ICONIFIED);
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
       });

        JLabel jl = new JLabel("�˺ţ�");
        jl.setFont(new Font("����", Font.PLAIN, 16));
        jl.setBounds(210, 40, 200, 18);
        name = new JTextField();
        name.setBounds(280, 40, 200, 18);

        JLabel jl2 = new JLabel("���룺");
        jl2.setFont(new Font("����", Font.PLAIN, 16));
        jl2.setBounds(210, 90, 200, 18);
        password = new JPasswordField();
        password.setBounds(280, 90, 200, 18);

        ImageIcon icon = new ImageIcon("qq_img2.png");
        imagelable = new JLabel(icon);
        imagelable.setBounds(15, 0, 150, 160);

//        ImageIcon icon2 = new ImageIcon("qq.png");
//        JLabel imagelable2 = new JLabel(icon2);
//        imagelable2.setBounds(210, 1, 268, 45);

        ImageIcon icon3 = new ImageIcon("TIM_W490.png");// ���TIM������ɫ
        JLabel imagelable3 = new JLabel(icon3);
        imagelable3.setBounds(0, 0, 490, 200);
        imagelable3.setLayout(null);

        JButton reg = new JButton("ע��");
        reg.setBounds(420, 120, 60, 18);
        reg.addActionListener(new register());
        reg.setBackground(new Color(235,242,249));


        this.add(exit);
        this.add(duang);

        this.add(zujianJpan);
        zujianJpan.add(reg);
        zujianJpan.add(jl);
        zujianJpan.add(name);
        zujianJpan.add(jl2);
        zujianJpan.add(password);
        zujianJpan.add(imagelable);
        this.add(imagelable3);


        JButton jb = new JButton("ȷ��");
        jb.setBackground(new Color(235,242,249));
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {

                    if (name.getText().trim().length() == 0
                            || new String(password.getPassword()).trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "�û������벻����Ϊ��");
                        return;
                    }

                    //��֤��¼
                    link();
                    //����һ��Message�������մӷ���˷�������֤��Ϣ
                    Message m = null;
                    //���մӷ�������������֤��Ϣ
                    m = (Message) in.readObject();
                    if(m.getType()==Types.NoQQValue){
                        JOptionPane.showMessageDialog(Denglu.this,"��QQ�Ų����ڣ���˶Ժ���������");
                    }else{

                        System.out.println("Denglu:"+m.isIslog());
                        if (m.isIslog()) {
                            m.setType(Types.StopLogin);
                            MainView f = new MainView(out, in, m, s);
                            new Thread(f).start();//����ʱ�̽��շ���˷�����Ϣ���߳�
                            System.out.println("Denglu:��������"+m.getFriend_num());
                            dispose();//���ٵ�¼��
                        } else {
                            m.setType(Types.StopLogin);
                            JOptionPane.showMessageDialog(null, "�˺Ż��������");
                        }

                    }
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        jb.setBounds(280, 120, 60, 18);
        zujianJpan.add(jb);
        this.getRootPane().setDefaultButton(jb);// Ϊȷ������ӻس���Ӧ

        final JButton button = new JButton();
        button.setText("����");
        button.setBackground(new Color(235,242,249));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // TODO �Զ����ɷ������
                name.setText("");
                password.setText("");
            }
        });
        button.setBounds(350, 120, 60, 18);
        zujianJpan.add(button);
        setVisible(true);
    }
    class register implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Register r = new Register(out);
        }
    }

    /**
     * ��Ե�¼��������������Ĳ���
     * @throws Exception
     */
    public void link()  {
        try {
//        ����һ�����˻��������Message����������ȥ�����Ƿ�ͨ����¼
            mess = new Message();
            //m.setType(Types.login);
            mess.setQQ1(name.getText());
            mess.setPassword(password.getText());
            mess.setType(Types.login);
            out.writeObject(mess);
            out.flush();
        }catch (Exception e){
            e.getMessage();
        }
    }

    Point loc = null;
    Point tmp = null;
    boolean isDragged = false;
    private void setDragable(final JFrame jFrame) {
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                isDragged = false;
                jFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            }

            public void mousePressed(java.awt.event.MouseEvent e) {

                tmp = new Point(e.getX(), e.getY());

                isDragged = true;

                jFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));

            }

        });

        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseDragged(java.awt.event.MouseEvent e) {

                if(isDragged) {
                    loc = new Point(jFrame.getLocation().x + e.getX() - tmp.x,
                    jFrame.getLocation().y + e.getY() - tmp.y);
                    jFrame.setLocation(loc);
                }

            }

        });

    }
}



