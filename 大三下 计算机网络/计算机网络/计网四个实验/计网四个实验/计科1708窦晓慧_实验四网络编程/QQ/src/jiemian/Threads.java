package jiemian;



import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 用户线程
 *
 * @author Junking
 */
public class Threads implements Runnable {

    Socket s;
    Thread ct;
    ObjectInputStream in;
    ObjectOutputStream out;
    Message m = null;

    public Threads(Socket s) throws Exception {
        this.s = s;
        System.out.println("用户" + s.getInetAddress() + "的线程创建成功！");
        in = new ObjectInputStream(s.getInputStream());
        out = new ObjectOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        boolean ClientCon = true;
        int c = 0;
        //不断地接收来自某个socket的信息
        while (ClientCon) {
            try {
                c++;

                //获取客户端发来的Message

                m = (Message) in.readObject();
                System.out.println("Threads:第" + (c) + "圈类型为" + m.getType());
                System.out.println("Threads:m:" + m.getType() + m.getMessage());
//                    if(c==3){
//                        System.exit(0);
//                    }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /**当判断为登录操作时*/
            if (m.getType() == Types.login) {
                //set好m里的对应昵称
                m.setNickname1(idToName(m.getQQ1()));
                //set好用户对应的好友数量和好友qq号；PS：注意，这个好友qq号再m包中是用String friend_name的元素表示，不要误惹为name是昵称，
                this.setsetFriend_numAndFriend_name(m.getQQ1());
                //set好用户对应好友的昵称
                this.setFriend_nickName();
                System.out.println("Threads:输入的账号和密码是：" + m.getQQ1() + "和" + m.getPassword());

                SQL sql = new SQL("user");
                try {
                    String password_temp;
                    sql.stm = sql.con.createStatement();
                    String sqlWords = "select password,name from " + sql.tableName + " where id='" + m.getQQ1() + "'";
                    sql.rs = sql.stm.executeQuery(sqlWords);//将结果集储存到rs中供使用
                    sql.rs.next();
                    if (sql.rs.last()) {
                        sql.rs.first();
                        password_temp = sql.rs.getString("password");
                        m.setNickname1(sql.rs.getString("name"));
                        System.out.println("Threads:数据库中取到的密码是" + password_temp + "  用户输入的密码是" + m.getPassword());
                        //账号密码匹配后方可允许登陆
                        if (password_temp.equals(m.getPassword())) {
                            setPersonalDatas(m);//set个人资料
                            m.setIslog(true);//允许登陆
                            //将QQ号和对应socket添加到集合中
                            Service.map_qqSoc.put(m.getQQ1(), s);
                            System.out.println("Threads:在登录成功后map_qqSoc集合是" + Service.map_qqSoc);

                            Service.add(m.getQQ1(), this);
                            System.out.println("Threads:在登录成功后map_qqTres集合是" + Service.map_qqTres);
                        }


                        try {
                            out.writeObject(m);// 把message返回
                            out.flush();// 清除缓存
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Message m = new Message();
                        m.setType(Types.NoQQValue);
                        try {

                            out.writeObject(m);// 把message返回
                            out.flush();// 清除缓存
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    sql.SQLover();

                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }

            /**当判断私人聊天的情况时*/
            System.out.println("Threads:这个时候的消息参数是" + m.getType());
            if (m.getType() == Types.privateTalk) {
                m.setNickname1(idToName(m.getQQ1()));
                //将受到的消息保存到字符串中
                String str = m.getMessage();
                System.out.println("Threads:" + m.getQQ1() + "请求向" + m.getQQ2() + "发送了消息：" + m.getMessage());
                Socket sQQ2 = Service.map_qqSoc.get(m.getQQ2());
                System.out.println("Threads:sQQ2::" + sQQ2);
                try {
                    ObjectOutputStream o = Service.map_qqTres.get(m.getQQ2()).out;
                    //ObjectOutputStream o = new ObjectOutputStream(sQQ2.getOutputStream());
                    o.writeObject(m);
                    o.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    //out.writeObject(m);// 把message返回
                    // out.flush();// 清除缓存
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * 判断为注册的情况时
             */
            if (m.getType() == Types.regeist) {


                SQL sql = new SQL("user");
                try {
                    String password_temp;
                    sql.stm = sql.con.createStatement();
                    String sqlWords = "insert into user (id,password,name) values (" + m.getQQ1() + ",'" + m.getPassword() + "','" + m.getNickname1() + "')";
                    sql.stm.executeUpdate(sqlWords);


                    System.out.println("Threads:新用户添加成功");
                    sql.SQLover();

                } catch (SQLException e) {
                    e.printStackTrace();

                }

            }
            //当触发添加好友的按钮时
            if (m.getType() == Types.addFriends) {
                Message m0 = new Message();
                if (m.getQQ2() != null) {
                    System.out.println("Threads:开始添加好友");
                    SQL sql = new SQL("friends");
                    try {
                        sql.con.setAutoCommit(false);
                        sql.stm = sql.con.createStatement();

                        String sqlWords1 = "insert into friends (id1,id2) values ('" + m.getQQ1() + "','" + m.getQQ2() + "')";
                        String sqlWords2 = "insert into friends (id2,id1) values ('" + m.getQQ1() + "','" + m.getQQ2() + "')";
                        sql.stm.addBatch(sqlWords1);
                        sql.stm.addBatch(sqlWords2);
                        sql.stm.executeBatch();

                        sql.con.commit();//事务提交


                        sql.con.setAutoCommit(true);//回复自动提交模式
                        System.out.println("Threads:新好友添加成功");
                        if (sql.con != null) {
                            sql.con.rollback();
                            sql.con.setAutoCommit(true);
                        }
                        sql.SQLover();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    m0.setIsAdd(true);
                    m0.setType(Types.recAddFriends);
                    m0.setNickname2(idToName(m.getQQ2()));
                    m0.setQQ2(m.getQQ2());
                    System.out.println("Threads:" + idToName(m.getQQ2()));
                } else {
                    m0.setIsAdd(false);
                }
                try {
                    out.writeObject(m0);
                    out.flush();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }


            }
            if (m.getType() == Types.findFriendsDatas) {
                Message m = new Message();
                m = this.m;
                setPersonalDatas(m);
                try {
                    out.writeObject(m);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //当发送文件的情况时,向发送者反馈端口号
            if (m.getType() == Types.sendfile) {
                try {
                    // System.out.println("Threads:" + m.getQQ1() + "请求向" + m.getQQ2() + "发送文件：" + m.getFile());
                    Socket sk = Service.map_qqTres.get(m.getQQ2()).s;
                    m.setPort(sk.getPort());
                    m.setIp(sk.getInetAddress().getHostAddress());

                    ObjectOutputStream o = Service.map_qqTres.get(m.getQQ2()).out;

                    m.setType(Types.StartSen);
                    o.writeObject(m);
                    m.setType(Types.StartSendFile);

                    out.writeObject(m);
                    out.flush();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            /**
             * 当判断为用户要保存自己的资料时
             */
            if (m.getType() == Types.savePersonalDatas) {
                try {
                    System.out.println("Threads:用户要保存资料！");
                    SQL s = new SQL("user");
                    String sqlWords = "update user set name=?,motto=?,Nationality=?,country=?,province=?,city=? where id=?";
                    s.pstm = s.con.prepareStatement(sqlWords);
                    s.pstm.setString(1, m.getNickname1());
                    s.pstm.setString(2, m.getMotto());
                    s.pstm.setString(3, m.getNationality());
                    s.pstm.setString(4, m.getCountry());
                    s.pstm.setString(5, m.getProvince());
                    s.pstm.setString(6, m.getCity());
                    s.pstm.setLong(7, Long.valueOf(m.getQQ1()));
                    s.pstm.execute();
                    s.SQLover();
                    System.out.println("Threads:保存资料成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (m.getType() == Types.refrush) {
                //set好m里的对应昵称
                m.setNickname1(idToName(m.getQQ1()));
                //set好用户对应的好友数量和好友qq号；PS：注意，这个好友qq号再m包中是用String friend_name的元素表示，不要误惹为name是昵称，
                this.setsetFriend_numAndFriend_name(m.getQQ1());
                //set好用户对应好友的昵称
                this.setFriend_nickName();
                SQL sql = new SQL("user");
                try {
                    String password_temp;
                    sql.stm = sql.con.createStatement();
                    String sqlWords = "select password,name from " + sql.tableName + " where id='" + m.getQQ1() + "'";
                    sql.rs = sql.stm.executeQuery(sqlWords);//将结果集储存到rs中供使用
                    sql.rs.next();
                    if (sql.rs.last()) {
                        sql.rs.first();
                        password_temp = sql.rs.getString("password");
                        m.setNickname1(sql.rs.getString("name"));
                        //账号密码匹配后方可允许登陆
                        setPersonalDatas(m);//set个人资料
                        try {
                            m.setType(Types.refrush);
                            out.writeObject(m);// 把message返回
                            out.flush();// 清除缓存
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    sql.SQLover();

                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
            if (m.getType() == Types.deleteFriend) {
                try {
                    SQL sql = new SQL("friends");
                    sql.stm = sql.con.createStatement();
                    String sqlwords1 = "delete from friends where (id1='" + m.getQQ1() + "' and id2='" + m.getQQ2() + "') or (id1='" + m.getQQ2() + "' and id2='" + m.getQQ1() + "')";
                    // PreparedStatement ps = sql.con.prepareStatement(sqlwords1);
                    sql.stm.executeUpdate(sqlwords1);
                    System.out.println("Threads:数据库删除成功！");
                    sql.SQLover();



                    Message message =new Message();
                    message.setType(Types.deleteFriendSucceed);
                    out.writeObject(message);
                    out.flush();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (m.getType() == Types.updatePassword) {
                try {
                    System.out.println("Threads:用户要修改密码！");
                    SQL s = new SQL("user");
                    String sqlWords = "update user set password=? where id=?";
                    s.pstm = s.con.prepareStatement(sqlWords);
                    s.pstm.setString(1, m.getNewPassword());
                    s.pstm.setLong(2, Long.valueOf(m.getQQ1()));
                    s.pstm.execute();
                    s.SQLover();
                    System.out.println("Threads:密码修改成功");

                    Message m0 = new Message();
                    m0.setType(Types.updatePasswordSucceed);
                    out.writeObject(m0);
                    out.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (m.getType() == Types.logout) {
                try {
                    Service.deleteS(m.getQQ1());
                    Thread.sleep(1000);/**！！！睡眠一段时间缓解线程的奔跑~！！！！*/
                    System.out.println("Threads:服务器判断关闭");
                    in.close();
                    out.close();
                    s.close();
                    break;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 传入id返回昵称
     */
    public static String idToName(String id) {
        SQL s = new SQL("user");
        String name = null;
        try {

            s.stm = s.con.createStatement();
            String sqlWords = "select * from user where id='" + id + "'";
            /**
             * 本句话存放获取的结果集 executeQuery通常用于选择语句
             */
            s.rs = s.stm.executeQuery(sqlWords);// 将结果集储存到rs中供使用
            if (s.rs.next()) {// 用户输入信息和数据库信息作比较
                name = s.rs.getString("name");
                s.SQLover();
                return name;
            } else System.out.println("id对应昵称时出现错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        s.SQLover();
        return "error：qq号对应昵称出错";

    }

    public void setsetFriend_numAndFriend_name(String QQ1) {
        try {
            SQL s = new SQL("friends");
            s.stm = s.con.createStatement();
            String sqlWords = "select id2 from friends where id1='" + QQ1 + "'";
            System.out.println("Message:" + sqlWords);
            s.rs = s.stm.executeQuery(sqlWords);//将结果集储存到rs中供使用

            s.rs.last();

            m.setFriend_num(s.rs.getRow());
            System.out.println("Message:行数是" + m.getFriend_num());
            s.rs.beforeFirst();
            String[] str = new String[m.getFriend_num()];
            int i = 0;
            while (s.rs.next()) {
                str[i++] = s.rs.getString("id2");//获取（下）一个好友的qq
            }
            m.setFriend_name(str);
            s.SQLover();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的好友昵称，存进字符串数组Friend_nickName[]中
     */
    public void setFriend_nickName() {
        String[] strNickname = new String[m.getFriend_num()];//生成一个临时字符串数组用来储存获得的昵称
        String[] strQQ = m.getFriend_name();
        for (int i = 0; i < m.getFriend_num(); i++) {
            strNickname[i] = Threads.idToName(strQQ[i]);
        }
        m.setFriend_nickname(strNickname);
    }

    /**
     * set传入的个人资料
     */
    public void setPersonalDatas(Message m) {
        SQL s = new SQL("user");
        try {
            s.stm = s.con.createStatement();
            String sqlWords = "select * from user where id='" + m.getQQ1() + "'";
            s.rs = s.stm.executeQuery(sqlWords);// 将结果集储存到rs中供使用
            if (s.rs.next()) {// 用户输入信息和数据库信息作比较
                m.setNickname1(s.rs.getString("name"));
                m.setMotto(s.rs.getString("motto"));
                m.setNationality(s.rs.getString("Nationality"));
                m.setCountry(s.rs.getString("country"));
                m.setProvince(s.rs.getString("province"));
                m.setCity(s.rs.getString("city"));
            } else System.out.println("id对应昵称时出现错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        s.SQLover();
    }
}
