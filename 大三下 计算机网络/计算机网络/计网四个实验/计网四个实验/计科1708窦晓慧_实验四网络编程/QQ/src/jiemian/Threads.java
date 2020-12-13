package jiemian;



import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * �û��߳�
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
        System.out.println("�û�" + s.getInetAddress() + "���̴߳����ɹ���");
        in = new ObjectInputStream(s.getInputStream());
        out = new ObjectOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        boolean ClientCon = true;
        int c = 0;
        //���ϵؽ�������ĳ��socket����Ϣ
        while (ClientCon) {
            try {
                c++;

                //��ȡ�ͻ��˷�����Message

                m = (Message) in.readObject();
                System.out.println("Threads:��" + (c) + "Ȧ����Ϊ" + m.getType());
                System.out.println("Threads:m:" + m.getType() + m.getMessage());
//                    if(c==3){
//                        System.exit(0);
//                    }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /**���ж�Ϊ��¼����ʱ*/
            if (m.getType() == Types.login) {
                //set��m��Ķ�Ӧ�ǳ�
                m.setNickname1(idToName(m.getQQ1()));
                //set���û���Ӧ�ĺ��������ͺ���qq�ţ�PS��ע�⣬�������qq����m��������String friend_name��Ԫ�ر�ʾ����Ҫ����Ϊname���ǳƣ�
                this.setsetFriend_numAndFriend_name(m.getQQ1());
                //set���û���Ӧ���ѵ��ǳ�
                this.setFriend_nickName();
                System.out.println("Threads:������˺ź������ǣ�" + m.getQQ1() + "��" + m.getPassword());

                SQL sql = new SQL("user");
                try {
                    String password_temp;
                    sql.stm = sql.con.createStatement();
                    String sqlWords = "select password,name from " + sql.tableName + " where id='" + m.getQQ1() + "'";
                    sql.rs = sql.stm.executeQuery(sqlWords);//����������浽rs�й�ʹ��
                    sql.rs.next();
                    if (sql.rs.last()) {
                        sql.rs.first();
                        password_temp = sql.rs.getString("password");
                        m.setNickname1(sql.rs.getString("name"));
                        System.out.println("Threads:���ݿ���ȡ����������" + password_temp + "  �û������������" + m.getPassword());
                        //�˺�����ƥ��󷽿������½
                        if (password_temp.equals(m.getPassword())) {
                            setPersonalDatas(m);//set��������
                            m.setIslog(true);//�����½
                            //��QQ�źͶ�Ӧsocket��ӵ�������
                            Service.map_qqSoc.put(m.getQQ1(), s);
                            System.out.println("Threads:�ڵ�¼�ɹ���map_qqSoc������" + Service.map_qqSoc);

                            Service.add(m.getQQ1(), this);
                            System.out.println("Threads:�ڵ�¼�ɹ���map_qqTres������" + Service.map_qqTres);
                        }


                        try {
                            out.writeObject(m);// ��message����
                            out.flush();// �������
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Message m = new Message();
                        m.setType(Types.NoQQValue);
                        try {

                            out.writeObject(m);// ��message����
                            out.flush();// �������
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    sql.SQLover();

                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }

            /**���ж�˽����������ʱ*/
            System.out.println("Threads:���ʱ�����Ϣ������" + m.getType());
            if (m.getType() == Types.privateTalk) {
                m.setNickname1(idToName(m.getQQ1()));
                //���ܵ�����Ϣ���浽�ַ�����
                String str = m.getMessage();
                System.out.println("Threads:" + m.getQQ1() + "������" + m.getQQ2() + "��������Ϣ��" + m.getMessage());
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
                    //out.writeObject(m);// ��message����
                    // out.flush();// �������
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * �ж�Ϊע������ʱ
             */
            if (m.getType() == Types.regeist) {


                SQL sql = new SQL("user");
                try {
                    String password_temp;
                    sql.stm = sql.con.createStatement();
                    String sqlWords = "insert into user (id,password,name) values (" + m.getQQ1() + ",'" + m.getPassword() + "','" + m.getNickname1() + "')";
                    sql.stm.executeUpdate(sqlWords);


                    System.out.println("Threads:���û���ӳɹ�");
                    sql.SQLover();

                } catch (SQLException e) {
                    e.printStackTrace();

                }

            }
            //��������Ӻ��ѵİ�ťʱ
            if (m.getType() == Types.addFriends) {
                Message m0 = new Message();
                if (m.getQQ2() != null) {
                    System.out.println("Threads:��ʼ��Ӻ���");
                    SQL sql = new SQL("friends");
                    try {
                        sql.con.setAutoCommit(false);
                        sql.stm = sql.con.createStatement();

                        String sqlWords1 = "insert into friends (id1,id2) values ('" + m.getQQ1() + "','" + m.getQQ2() + "')";
                        String sqlWords2 = "insert into friends (id2,id1) values ('" + m.getQQ1() + "','" + m.getQQ2() + "')";
                        sql.stm.addBatch(sqlWords1);
                        sql.stm.addBatch(sqlWords2);
                        sql.stm.executeBatch();

                        sql.con.commit();//�����ύ


                        sql.con.setAutoCommit(true);//�ظ��Զ��ύģʽ
                        System.out.println("Threads:�º�����ӳɹ�");
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
            //�������ļ������ʱ,�����߷����˿ں�
            if (m.getType() == Types.sendfile) {
                try {
                    // System.out.println("Threads:" + m.getQQ1() + "������" + m.getQQ2() + "�����ļ���" + m.getFile());
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
             * ���ж�Ϊ�û�Ҫ�����Լ�������ʱ
             */
            if (m.getType() == Types.savePersonalDatas) {
                try {
                    System.out.println("Threads:�û�Ҫ�������ϣ�");
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
                    System.out.println("Threads:�������ϳɹ�");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (m.getType() == Types.refrush) {
                //set��m��Ķ�Ӧ�ǳ�
                m.setNickname1(idToName(m.getQQ1()));
                //set���û���Ӧ�ĺ��������ͺ���qq�ţ�PS��ע�⣬�������qq����m��������String friend_name��Ԫ�ر�ʾ����Ҫ����Ϊname���ǳƣ�
                this.setsetFriend_numAndFriend_name(m.getQQ1());
                //set���û���Ӧ���ѵ��ǳ�
                this.setFriend_nickName();
                SQL sql = new SQL("user");
                try {
                    String password_temp;
                    sql.stm = sql.con.createStatement();
                    String sqlWords = "select password,name from " + sql.tableName + " where id='" + m.getQQ1() + "'";
                    sql.rs = sql.stm.executeQuery(sqlWords);//����������浽rs�й�ʹ��
                    sql.rs.next();
                    if (sql.rs.last()) {
                        sql.rs.first();
                        password_temp = sql.rs.getString("password");
                        m.setNickname1(sql.rs.getString("name"));
                        //�˺�����ƥ��󷽿������½
                        setPersonalDatas(m);//set��������
                        try {
                            m.setType(Types.refrush);
                            out.writeObject(m);// ��message����
                            out.flush();// �������
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
                    System.out.println("Threads:���ݿ�ɾ���ɹ���");
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
                    System.out.println("Threads:�û�Ҫ�޸����룡");
                    SQL s = new SQL("user");
                    String sqlWords = "update user set password=? where id=?";
                    s.pstm = s.con.prepareStatement(sqlWords);
                    s.pstm.setString(1, m.getNewPassword());
                    s.pstm.setLong(2, Long.valueOf(m.getQQ1()));
                    s.pstm.execute();
                    s.SQLover();
                    System.out.println("Threads:�����޸ĳɹ�");

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
                    Thread.sleep(1000);/**������˯��һ��ʱ�仺���̵߳ı���~��������*/
                    System.out.println("Threads:�������жϹر�");
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
     * ����id�����ǳ�
     */
    public static String idToName(String id) {
        SQL s = new SQL("user");
        String name = null;
        try {

            s.stm = s.con.createStatement();
            String sqlWords = "select * from user where id='" + id + "'";
            /**
             * ���仰��Ż�ȡ�Ľ���� executeQueryͨ������ѡ�����
             */
            s.rs = s.stm.executeQuery(sqlWords);// ����������浽rs�й�ʹ��
            if (s.rs.next()) {// �û�������Ϣ�����ݿ���Ϣ���Ƚ�
                name = s.rs.getString("name");
                s.SQLover();
                return name;
            } else System.out.println("id��Ӧ�ǳ�ʱ���ִ���");
        } catch (Exception e) {
            e.printStackTrace();
        }
        s.SQLover();
        return "error��qq�Ŷ�Ӧ�ǳƳ���";

    }

    public void setsetFriend_numAndFriend_name(String QQ1) {
        try {
            SQL s = new SQL("friends");
            s.stm = s.con.createStatement();
            String sqlWords = "select id2 from friends where id1='" + QQ1 + "'";
            System.out.println("Message:" + sqlWords);
            s.rs = s.stm.executeQuery(sqlWords);//����������浽rs�й�ʹ��

            s.rs.last();

            m.setFriend_num(s.rs.getRow());
            System.out.println("Message:������" + m.getFriend_num());
            s.rs.beforeFirst();
            String[] str = new String[m.getFriend_num()];
            int i = 0;
            while (s.rs.next()) {
                str[i++] = s.rs.getString("id2");//��ȡ���£�һ�����ѵ�qq
            }
            m.setFriend_name(str);
            s.SQLover();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ���еĺ����ǳƣ�����ַ�������Friend_nickName[]��
     */
    public void setFriend_nickName() {
        String[] strNickname = new String[m.getFriend_num()];//����һ����ʱ�ַ����������������õ��ǳ�
        String[] strQQ = m.getFriend_name();
        for (int i = 0; i < m.getFriend_num(); i++) {
            strNickname[i] = Threads.idToName(strQQ[i]);
        }
        m.setFriend_nickname(strNickname);
    }

    /**
     * set����ĸ�������
     */
    public void setPersonalDatas(Message m) {
        SQL s = new SQL("user");
        try {
            s.stm = s.con.createStatement();
            String sqlWords = "select * from user where id='" + m.getQQ1() + "'";
            s.rs = s.stm.executeQuery(sqlWords);// ����������浽rs�й�ʹ��
            if (s.rs.next()) {// �û�������Ϣ�����ݿ���Ϣ���Ƚ�
                m.setNickname1(s.rs.getString("name"));
                m.setMotto(s.rs.getString("motto"));
                m.setNationality(s.rs.getString("Nationality"));
                m.setCountry(s.rs.getString("country"));
                m.setProvince(s.rs.getString("province"));
                m.setCity(s.rs.getString("city"));
            } else System.out.println("id��Ӧ�ǳ�ʱ���ִ���");
        } catch (Exception e) {
            e.printStackTrace();
        }
        s.SQLover();
    }
}
