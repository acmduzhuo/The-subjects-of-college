package jiemian;


import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLContextSpi;
import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Message implements Serializable {
    private String QQ1;//账号
    private String nickname1;//QQ1对应的昵称
    private String nickname2;//QQ2对应的昵称
    private int type;//聊天类型
    private String ip;//ip
    private int port;//端口号
    private boolean islog;//是否允许登陆
    private String QQ2;//对方的名字
    private String message;//发送给对方的消息
    private String filename;
    private File file;
    private int filelength;
    private int friend_num;//好友数量
    private String[] friend_name;//好友qq号
    private String[] friend_nickname;//好友qq号
    private String password;
    private  String personalMotto;//个人签名
    String Nationality;
    String country;
    String motto;

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReSureNewPassword() {
        return reSureNewPassword;
    }

    public void setReSureNewPassword(String reSureNewPassword) {
        this.reSureNewPassword = reSureNewPassword;
    }


    String province;
    String city;
    String newPassword;
    String reSureNewPassword;

    public String getPersonalMotto() {
        return personalMotto;
    }

    public void setPersonalMotto(String personalMotto) {
        this.personalMotto = personalMotto;
    }

    private boolean isAdd;

    public boolean getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean add) {
        isAdd = add;
    }

    public String getMessage() {
        return message;
    }



    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getFriend_nickname() {
        return friend_nickname;
    }

    public void setFriend_nickname(String[] friend_nickname) {
        this.friend_nickname = friend_nickname;
    }


    public String getNickname1() {
        return nickname1;
    }

    public void setNickname1(String nickname1) {
        this.nickname1 = nickname1;
    }

    public String getNickname2() {
        return nickname2;
    }

    public void setNickname2(String nickname2) {
        this.nickname2 = nickname2;
    }

    public String[] getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String[] friend_name) {
        this.friend_name = friend_name;
    }

    Message() {
        //构造完成后要通过下面的方法设置好对应的好友数量和好友们的名字
        //setsetFriend_numAndFriend_name(QQ1);
        //System.out.println("Message:好友数量"+friend_num);
    }

    public int getFriend_num() {
        return friend_num;
    }

    public void setFriend_num(int friend_num) {
        this.friend_num = friend_num;
    }


    public String getQQ1() {
        return QQ1;
    }

    public void setQQ1(String QQ1) {
        this.QQ1 = QQ1;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isIslog() {
        return islog;
    }

    public void setIslog(boolean islog) {
        this.islog = islog;
    }

    public String getQQ2() {
        return QQ2;
    }

    public void setQQ2(String QQ2) {
        this.QQ2 = QQ2;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFilelength() {
        return filelength;
    }

    public void setFilelength(int filelength) {
        this.filelength = filelength;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}


