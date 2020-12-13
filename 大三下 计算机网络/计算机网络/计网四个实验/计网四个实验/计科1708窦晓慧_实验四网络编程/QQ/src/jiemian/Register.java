package jiemian;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class Register extends JDialog{
	PreparedStatement pstm;
	JTextField idReg;
	JTextField passwordReg;
	JTextField nameReg;
	ObjectOutputStream out;
	public Register(ObjectOutputStream out) {
		this.out = out;
		this.setLayout(new FlowLayout(FlowLayout.CENTER));				//����ʽ����    
		
		this.setTitle("���û�ע��");							//�����ǩ  
		this.setSize(400, 280);								//�����С  
		this.setLocationRelativeTo(null);					//����Ļ�м���ʾ(������ʾ)  
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setVisible(true);								//��ʾ���� 
		this.setResizable(false);							//��������
		
		
		JButton back = new JButton("����");	//���ز˵���ť
		back.setFont(new Font("Dialog", Font.PLAIN, 20));
		back.setPreferredSize(new Dimension(80,30));
		
		JButton addR = new JButton("ע��");	//ע�ᰴť
		addR.setFont(new Font("Dialog", Font.PLAIN, 20));
		addR.setPreferredSize(new Dimension(80,30));
		
		JLabel id = new JLabel("�˺ţ�");
		id.setFont(new Font("Dialog", Font.PLAIN, 30));
		id.setPreferredSize(new Dimension(120,50));
		
		JLabel name = new JLabel("�ǳƣ�");
		name.setFont(new Font("Dialog", Font.PLAIN, 30));
		name.setPreferredSize(new Dimension(120,50));
		
		JLabel password = new JLabel("���룺");
		password.setFont(new Font("Dialog", Font.PLAIN, 30));
		password.setPreferredSize(new Dimension(120,50));
		
		idReg = new JTextField();
		idReg.setFont(new Font("Dialog", Font.PLAIN, 30));
		idReg.setPreferredSize(new Dimension(180,40));
		
		passwordReg = new JTextField();
		passwordReg.setFont(new Font("Dialog", Font.PLAIN,30));
		passwordReg.setPreferredSize(new Dimension(180,40));
		
		nameReg = new JTextField();
		nameReg.setFont(new Font("Dialog", Font.PLAIN, 30));
		nameReg.setPreferredSize(new Dimension(180,40));
		
		
		
		this.add(id);
		this.add(idReg);
		this.add(password);
		this.add(passwordReg);
		this.add(name);
		this.add(nameReg);
		this.add(addR);
		this.add(back);
		
		back.addActionListener(new BackLogin());
		addR.addActionListener(new register());

	
	
	}
	
	/*
	 * ��Ӧע�ᰴť��
	 */
	class  register implements ActionListener{ 
		  public void actionPerformed(ActionEvent e){

		       Message m= new Message();
		       m.setType(Types.regeist);
		       m.setQQ1(idReg.getText());
		       m.setPassword(passwordReg.getText());
		       m.setNickname1(nameReg.getText());
		       try{
				   out.writeObject(m);
		           out.flush();
		       }catch(Exception e1){
		       e1.printStackTrace();
		       }
		       JOptionPane.showMessageDialog(null,"ע��ɹ���");
		       Register.this.dispose();


		  }	
	}
	
	/**
	 * ��Ӧ���ز˵���ť��,���صȵ���¼����
	 */
	class  BackLogin implements ActionListener{ 
		  public void actionPerformed(ActionEvent e){ 
			 dispose();
		  }	
	}
}
