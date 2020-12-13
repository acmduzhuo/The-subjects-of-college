package jiemian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ��дһ��SQL��Ϊ�˷����������ݿ�
 * 
 * @author Junking
 *
 */
public class SQL {
	public String tableName;
	static public String datebaseName = "qq";
	PreparedStatement pstm = null;
	public Connection con = null;
	public Statement stm = null;
	public ResultSet rs = null;

	static String url = "com.mysql.jdbc.Driver";// ����������
	static String connectSql = "jdbc:mysql://localhost:3306/qq?useUnicode=true&characterEncoding=UTF-8";// ����MySQL���ݿ�
	static String sqlUser = "root";// ���ݿ��˺�
	static String sqlPasswd = "111111";// ���ݿ�����

	// ���ü��ϵķ����������ݼ���

	/**
	 * һ��SQL����޲ι��췽����ʹ��ʱӦ���Ը����е�tableName��ֵ���Դ����ӵ����ݱ�
	 */
	public SQL() {
		SQLconnect();
	}

	/**
	 * һ��SQL��Ĵ���һ���ַ��������Ĺ��췽�������ӵ�����ַ�����Ӧ�����ݱ�
	 * 
	 * @param tableName
	 */
	public SQL(String tableName) {
		this.tableName = tableName;
		SQLconnect();
	}

	/**
	 * �������ݿⷽ��
	 * 
	 * @���� Ҫ���ӵ����ݿ�ı���
	 *
	 */
	public void SQLconnect() {

		// ����������
		try {
			Class.forName(url);

			// ����MySQL

			con = DriverManager.getConnection(connectSql, sqlUser, sqlPasswd);

			//if (!con.isClosed())
				//System.out.println("���ݱ�" + datebaseName + "���ӳɹ�");
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}

		//ִ��MySQL���

		//System.out.println("���ݱ�"+tableName+"���Ӳ�����ִ�С�");

	}

	public void SQLover() {// �ر����ݿ�
		try {

			if (rs != null)
				rs.close();// rs��Ҫ�ж�֮���ٹرգ�ֱ�ӹرճ����жϡ�
			if (stm != null)
			stm.close();
			if (stm != null)
			con.close();
			//System.out.println("���ݿ�" + datebaseName + "�رճɹ�");

		} catch (Exception e) {
			System.out.println("���ݿ�" + datebaseName + "�ر�ʧ��");

			e.getMessage();
		}
	}

	/**
	 * Mysqlѡ�������ã�����ֵ����rs�� ע�⣺������tableName������ֵ
	 */
	public void SQLselect() throws SQLException {
		try {
			stm = con.createStatement();
			String sqlWords = "select * from " + tableName;
			/**
			 * ���仰��Ż�ȡ�Ľ���� executeQueryͨ������ѡ�����
			 */
			rs = stm.executeQuery(sqlWords);//����������浽rs�й�ʹ��

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SQL:");
		}

	}
	/**
	 * Mysql���������ã�����ֵ����pstm�� ע�⣺������tableName������ֵ
	 * 
	 * ����valueΪwhere��Ҫ�õ����ж�ֵ
	 */
	public void SQLupdate(String value){
		try {
			
		//Ԥ������£��޸ģ����ݣ������յ�sal��Ϊ5000.0
		pstm = con.prepareStatement("update emp set sal = ? where ename = ?");
		pstm.setFloat(1,(float) 5000.0);      
		pstm.setString(2,"����");             
		pstm.executeUpdate();
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
