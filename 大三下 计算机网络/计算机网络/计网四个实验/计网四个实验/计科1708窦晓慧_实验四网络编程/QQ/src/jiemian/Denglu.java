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
    public static String Nid = "未赋值a ";
    public static String Nname = "未赋值a ";
    public static String Npassword = "未赋值a ";
    private ObjectInputStream in;//输入流
    private ObjectOutputStream out;//输出流
    private Socket s;// 套接字
    private Message mess;
    JTextField name;
    JPanel zujianJpan;
    JPasswordField password;
    JLabel imagelable;

    public Denglu() {
        try{
            //目标服务器的ip和端口号的套接字
            s = new Socket("127.0.0.1", 10009);
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());

        }catch(Exception e){
            e.printStackTrace();
        }

        this.setUndecorated(true);//去掉标题栏
        this.setDragable(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("QQ登录");
        this.setBounds(600, 800, 490, 370);
        this.setLocationRelativeTo(null);// 在屏幕中间显示(居中显示)
        this.setLayout(null);// 设置布局管理器
        this.setResizable(false);// 锁定登录框大小

        zujianJpan = new JPanel();
        zujianJpan.setSize(490 ,270);
        zujianJpan.setLocation(0,200);
        zujianJpan.setLayout(null);
        zujianJpan.setBackground(new Color(235,242,249));

        ImageIcon exitImg = new ImageIcon("exit.png");// 添加关闭图标
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

        ImageIcon duangImg = new ImageIcon("Duang.png");// 添加最小化图标
        JLabel duang = new JLabel(duangImg);
        duang.setBounds(new Rectangle(410,15,20,20));
       duang.addMouseListener(new MouseListener() {

           /**
            * 鼠标点击操作
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

        JLabel jl = new JLabel("账号：");
        jl.setFont(new Font("黑体", Font.PLAIN, 16));
        jl.setBounds(210, 40, 200, 18);
        name = new JTextField();
        name.setBounds(280, 40, 200, 18);

        JLabel jl2 = new JLabel("密码：");
        jl2.setFont(new Font("黑体", Font.PLAIN, 16));
        jl2.setBounds(210, 90, 200, 18);
        password = new JPasswordField();
        password.setBounds(280, 90, 200, 18);

        ImageIcon icon = new ImageIcon("qq_img2.png");
        imagelable = new JLabel(icon);
        imagelable.setBounds(15, 0, 150, 160);

//        ImageIcon icon2 = new ImageIcon("qq.png");
//        JLabel imagelable2 = new JLabel(icon2);
//        imagelable2.setBounds(210, 1, 268, 45);

        ImageIcon icon3 = new ImageIcon("TIM_W490.png");// 添加TIM主题蓝色
        JLabel imagelable3 = new JLabel(icon3);
        imagelable3.setBounds(0, 0, 490, 200);
        imagelable3.setLayout(null);

        JButton reg = new JButton("注册");
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


        JButton jb = new JButton("确定");
        jb.setBackground(new Color(235,242,249));
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {

                    if (name.getText().trim().length() == 0
                            || new String(password.getPassword()).trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "用户名密码不允许为空");
                        return;
                    }

                    //验证登录
                    link();
                    //定义一个Message用来接收从服务端发来的验证信息
                    Message m = null;
                    //接收从服务器发来的验证信息
                    m = (Message) in.readObject();
                    if(m.getType()==Types.NoQQValue){
                        JOptionPane.showMessageDialog(Denglu.this,"该QQ号不存在，请核对后重新输入");
                    }else{

                        System.out.println("Denglu:"+m.isIslog());
                        if (m.isIslog()) {
                            m.setType(Types.StopLogin);
                            MainView f = new MainView(out, in, m, s);
                            new Thread(f).start();//开启时刻接收服务端发来消息的线程
                            System.out.println("Denglu:好友数量"+m.getFriend_num());
                            dispose();//销毁登录框
                        } else {
                            m.setType(Types.StopLogin);
                            JOptionPane.showMessageDialog(null, "账号或密码错误");
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
        this.getRootPane().setDefaultButton(jb);// 为确定键添加回车响应

        final JButton button = new JButton();
        button.setText("重置");
        button.setBackground(new Color(235,242,249));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // TODO 自动生成方法存根
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
     * 针对登录的与服务器互动的操作
     * @throws Exception
     */
    public void link()  {
        try {
//        传递一个带账户和密码的Message，将服务器去检验是否通过登录
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



