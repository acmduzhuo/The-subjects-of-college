package jiemian;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class ChatView extends JFrame implements ActionListener {
    Message mess;
    private static final long serialVersionUID = 1L;
    long id_QQ0;
    JTextArea jta_up;
    JTextArea jta_down;
    JScrollPane jsp_up;
    JScrollPane jsp_down;
    JButton jb_file_IO;
    JButton jb_sure;
    ObjectOutputStream objectOutputStream;
    String name_duifang;
    String name_ziji;
    Socket sf;
    OutputStream outf;
    InputStream inf;
    JButton jbColorFont;
    JButton jbColorBack;//背景色
    Color colorFont=Color.black;
    Color colorback=Color.white;
    JPanel jp_sure;


    String font1 = "宋体";
    int font2 = Font.PLAIN;
    int font3 = 11;


    ChatView(long id_QQ, Message m, ObjectOutputStream out, String nickName) {
        objectOutputStream = out;
        this.mess = m;
        this.id_QQ0 = id_QQ;
        int i_temp = -1;
        for (int i = 0; i < m.getFriend_name().length; i++) {
            if (String.valueOf(id_QQ).equals(m.getFriend_name()[i])) {
                i_temp = i;
            }
        }
        System.out.println("ChatView:i" + i_temp);
        name_duifang = nickName;
        this.setTitle("与 " + name_duifang + " 聊天会话框");
        this.setBackground(null);
        this.setSize(620, 550);                                //窗体大小
        this.setLocationRelativeTo(null);                    //在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainView.str_chatView.remove(String.valueOf(id_QQ0));
            }
        });
        this.setResizable(false);                            //锁定窗体
        this.setLayout(new FlowLayout());


        jta_up = new JTextArea();
        jta_up.setEditable(false);
        jta_up.setFont(new Font(font1, font2, font3));


        JPanel jp_function = new JPanel();
        jp_function.setPreferredSize(new Dimension(600, 35));
        jp_function.setBackground(Color.LIGHT_GRAY);

        jb_file_IO = new JButton("文件传输");
        jp_function.add(jb_file_IO);

        jta_down = new JTextArea();


        jp_sure = new JPanel();
        jp_sure.setPreferredSize(new Dimension(600, 30));
        jp_sure.setBackground(Color.WHITE);
        jp_sure.setLayout(new BorderLayout());

        jb_sure = new JButton("Biu~");
        jb_sure.setPreferredSize(new Dimension(60, 18));
        jp_sure.add(jb_sure, BorderLayout.EAST);
        this.getRootPane().setDefaultButton(jb_sure);// 为确定键添加回车响应

        jsp_up = new JScrollPane();
        jsp_up.setPreferredSize(new Dimension(600, 330));
        jsp_up.setViewportView(jta_up);

        jsp_down = new JScrollPane();
        jsp_down.setPreferredSize(new Dimension(600, 95));
        jsp_down.setViewportView(jta_down);

        final JComboBox<String> jFontStyle = new JComboBox<String>();
        jFontStyle.addItem("宋体");
        jFontStyle.addItem("黑体");
        jFontStyle.addItem("楷体");
        jFontStyle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                font1 = (String) jFontStyle.getSelectedItem();
                jta_up.setFont(new Font(font1, font2, font3));
            }
        });

        final JComboBox<String> jIsBold = new JComboBox<String>();
        jIsBold.addItem("常规");
        jIsBold.addItem("加粗");
        jIsBold.addItem("斜体");
        jIsBold.addItem("加粗且斜体");
        jIsBold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (font2 == 0) {
                    font2 = Font.PLAIN;
                } else if (font2 == 1) {
                    font2 = Font.BOLD;
                } else if (font2 == 2) {
                    font2 = Font.ITALIC;
                }

                font2 = jIsBold.getSelectedIndex();
                ;
                jta_up.setFont(new Font(font1, font2, font3));
            }
        });

        final JComboBox<String> jSize = new JComboBox<String>();

        for (int i = 0; i < 21; i++) {
            jSize.addItem(9 + i + "");
        }

        jSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                font3 = Integer.valueOf((String) jSize.getSelectedItem());
                jta_up.setFont(new Font(font1, font2, font3));
            }
        });

        jbColorFont = new JButton("字体颜色");
        jbColorFont.setBackground(Color.black);
        jbColorFont.setForeground(Color.white);
        jbColorFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Color c = JColorChooser.showDialog(ChatView.this,"请选择你要更改的字体颜色" , Color.black);
                if(c==null){
                    colorFont = Color.black;
                }else{
                    colorFont = c;
                    jbColorFont.setBackground(c);
                    jta_up.setForeground(c);
                }

            }
        });

        jbColorBack = new JButton("背景颜色");
        jbColorBack.setForeground(colorFont);
        jbColorBack.setBackground(Color.white);
        jbColorBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Color c = JColorChooser.showDialog(ChatView.this,"请选择你要更改的背景颜色" , Color.black);
                if(c==null){
                    colorFont = Color.white;
                }else{
                    colorFont = c;
                    jbColorFont.setBackground(c);
                    jta_up.setBackground(c);
                }

            }
        });


        jp_function.add(jFontStyle);
        jp_function.add(jIsBold);
        jp_function.add(jSize);
        jp_function.add(jbColorFont);
        jp_function.add(jbColorBack);
        this.add(jsp_up);
        this.add(jp_function);
        this.add(jsp_down);
        this.add(jp_sure);

        System.out.println("ChatView:" + "运行到了75行");
        //为发送消息按钮Biu~设置监听
        jb_sure.addActionListener(this);
        jb_file_IO.addActionListener(this);
        this.setVisible(true);                                //显示窗体

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_sure) {
            try {
                //获取系统时间
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");
                String str = jta_down.getText();
                jta_up.append(mess.getNickname1() + "  " + df.format(day) + "\n" + jta_down.getText() + "\n");
                Message m2 = new Message();
                m2.setFriend_nickname(mess.getFriend_nickname());
                m2.setQQ2(String.valueOf(id_QQ0));
                System.out.println("ChatView:QQ2===" + m2.getQQ2());
                m2.setQQ1(mess.getQQ1());
                m2.setType(Types.privateTalk);
                System.out.println("ChatView:----" + m2.getType());
                m2.setMessage(str);
                System.out.println("ChatView:" + "运行到了84行");
                //将信息包发送到服务端
                objectOutputStream.writeObject(m2);
                objectOutputStream.flush();
                //清空输入框
                jta_down.setText("");
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            System.out.println("ChatView:" + "运行到了91行");


        }
        if (e.getSource() == jb_file_IO) {
            try {
                Message mf = new Message();
                mf.setType(Types.sendfile);
                mf.setQQ1(mess.getQQ1());
                mf.setQQ2(String.valueOf(id_QQ0));
                objectOutputStream.writeObject(mf);
                objectOutputStream.flush();
            } catch (Exception e4) {
                e4.printStackTrace();
            }

        }


    }
}
