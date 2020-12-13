package jiemian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * �������ϴ��壬�����ڵ�½�ߵ�����
 */
public class PersonalDatas extends JDialog implements ActionListener {
    Button bSave;
    Button bReSetPass;
    JLabel[] labels = new JLabel[9];
    JTextField[] jtfs = new JTextField[9];
    ObjectOutputStream out ;

    PersonalDatas(Message m, ObjectOutputStream out,boolean isFriends) {
        this.out = out;
        String nickName = m.getNickname1();
        String qq = m.getQQ1();
        String motto = m.getMotto();
        String Nationality = m.getNationality();
        String country = m.getCountry();
        String province = m.getProvince();
        String city = m.getCity();
        /*
        ������������޸ģ�label
         */
        String newPassword;
        String reSureNewPassword;
        this.setLayout(new FlowLayout());
        this.setTitle("��������");
        this.setLocation(200, 200);
        this.setSize(390, 580);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        bSave = new Button("�����������");
        bReSetPass = new Button("����������");


        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setPreferredSize(new Dimension(70, 30));//��ǩ
            labels[i].setBackground(Color.blue);

        }


        for (int i = 0; i < jtfs.length; i++) {

            jtfs[i] = new JTextField();
            jtfs[i].setPreferredSize(new Dimension(280, 30));//��ǩ
            jtfs[i].setBackground(Color.lightGray);
        }

        labels[0].setText("�ǳ�");
        labels[1].setText("QQ��");
        labels[2].setText("����ǩ��");
        labels[3].setText("����");
        labels[4].setText("����");
        labels[5].setText("ʡ��");
        labels[6].setText("����");
        labels[7].setText("������");
        labels[8].setText("ȷ��������");

        jtfs[0].setText(nickName);
        jtfs[1].setText(qq);
        jtfs[1].setEditable(false);
        jtfs[2].setText(motto);
        jtfs[3].setText(Nationality);
        jtfs[4].setText(country);
        jtfs[5].setText(province);
        jtfs[6].setText(city);
        jtfs[7].setText("������Ϊ����������");
        for (int i = 0; i < labels.length; i++) {
            this.add(labels[i]);
            this.add(jtfs[i]);
        }
        bSave.addActionListener(this);
        bReSetPass.addActionListener(this);
        if(!isFriends){
            this.add(bReSetPass);
            this.add(bSave);
        }else{
            this.remove(jtfs[7]);
            this.remove(jtfs[8]);
            this.remove(labels[7]);
            this.remove(labels[8]);
        }
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bSave) {
            System.out.println("PersonalDatas:��Ҫ�޸�����");
            Message m = new Message();
            m.setNickname1(jtfs[0].getText());
            m.setQQ1(jtfs[1].getText());
            m.setMotto(jtfs[2].getText());
            m.setNationality(jtfs[3].getText());
            m.setCountry(jtfs[4].getText());
            m.setProvince(jtfs[5].getText());
            m.setCity(jtfs[6].getText());
            m.setType(Types.savePersonalDatas);
            try {
                out.writeObject(m);
                out.flush();
            } catch (Exception e0) {
                e0.printStackTrace();
            }

        } else if (e.getSource() == bReSetPass) {
            System.out.println("PersonalDatas:��Ҫ�޸�����");
            Message m = new Message();

            m.setQQ1(jtfs[1].getText());
            m.setNewPassword(jtfs[7].getText());
            m.setReSureNewPassword(jtfs[8].getText());
            if(jtfs[7].getText().equals(jtfs[8].getText())){
                m.setType(Types.updatePassword);
                try {
                    out.writeObject(m);
                    out.flush();
                } catch (Exception e0) {
                    e0.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(null,"�����������벻һ��");
            }
        }
    }
}
