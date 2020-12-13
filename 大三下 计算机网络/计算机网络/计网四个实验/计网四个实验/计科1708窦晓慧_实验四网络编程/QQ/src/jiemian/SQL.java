package jiemian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 编写一个SQL类为了方便连接数据库
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

	static String url = "com.mysql.jdbc.Driver";// 加载驱动包
	static String connectSql = "jdbc:mysql://localhost:3306/qq?useUnicode=true&characterEncoding=UTF-8";// 链接MySQL数据库
	static String sqlUser = "root";// 数据库账号
	static String sqlPasswd = "111111";// 数据库密码

	// 采用集合的方法返回数据集合

	/**
	 * 一个SQL类的无参构造方法，使用时应当对该类中的tableName赋值，以此连接到数据表
	 */
	public SQL() {
		SQLconnect();
	}

	/**
	 * 一个SQL类的传入一个字符串参数的构造方法，连接到与该字符串对应的数据表
	 * 
	 * @param tableName
	 */
	public SQL(String tableName) {
		this.tableName = tableName;
		SQLconnect();
	}

	/**
	 * 连接数据库方法
	 * 
	 * @参数 要连接的数据库的表明
	 *
	 */
	public void SQLconnect() {

		// 加载驱动包
		try {
			Class.forName(url);

			// 连接MySQL

			con = DriverManager.getConnection(connectSql, sqlUser, sqlPasswd);

			//if (!con.isClosed())
				//System.out.println("数据表" + datebaseName + "连接成功");
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}

		//执行MySQL语句

		//System.out.println("数据表"+tableName+"连接操作已执行。");

	}

	public void SQLover() {// 关闭数据库
		try {

			if (rs != null)
				rs.close();// rs需要判断之后再关闭，直接关闭程序中断。
			if (stm != null)
			stm.close();
			if (stm != null)
			con.close();
			//System.out.println("数据库" + datebaseName + "关闭成功");

		} catch (Exception e) {
			System.out.println("数据库" + datebaseName + "关闭失败");

			e.getMessage();
		}
	}

	/**
	 * Mysql选择语句调用，并将值传入rs中 注意：对象中tableName必须有值
	 */
	public void SQLselect() throws SQLException {
		try {
			stm = con.createStatement();
			String sqlWords = "select * from " + tableName;
			/**
			 * 本句话存放获取的结果集 executeQuery通常用于选择语句
			 */
			rs = stm.executeQuery(sqlWords);//将结果集储存到rs中供使用

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SQL:");
		}

	}
	/**
	 * Mysql更新语句调用，并将值传入pstm中 注意：对象中tableName必须有值
	 * 
	 * 参数value为where需要用到的判定值
	 */
	public void SQLupdate(String value){
		try {
			
		//预处理更新（修改）数据，将王刚的sal改为5000.0
		pstm = con.prepareStatement("update emp set sal = ? where ename = ?");
		pstm.setFloat(1,(float) 5000.0);      
		pstm.setString(2,"王刚");             
		pstm.executeUpdate();
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
