package bean;

import java.util.Random;

public class User {
    //id，用户名，密码，状态
    private String id;
    private String username;
    private String password;
    private boolean status;

    public User() {
        //设置初始值
        id=randomid();
        status=true;
    }

    public User(String username, String password) {
        id=randomid();
        status=true;
        this.username = username;
        this.password = password;
    }

    //随机生成id
    public String randomid(){
        StringBuilder s=new StringBuilder("surges");
        for (int i = 0; i < 5; i++) {
            s.append(new Random().nextInt(10));
        }
        return s.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
