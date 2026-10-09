package ui;

import bean.User;

import java.util.*;

public class Login {
    //登录功能主页面

    ArrayList<User>list=new ArrayList<>();
    public void start() {
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");

            Scanner sc = new Scanner(System.in);
            String h = sc.next();

            switch (h) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> exit();
                default -> error();
            }
        }
    }

    //登录操作
    public void login(ArrayList<User>list){
        System.out.println("用户选择了登录操作");

        if(list.size()==0){
            System.out.println("当前没有注册用户，请先注册~~");
            return;
        }

        Scanner sc=new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名：");
            String username=sc.next();

            //检验用户名是否存在
            int p=0;
            for(User u:list){
                if(u.getUsername().equals(username)){
                    p=1;
                    break;
                }
            }
            if(p==0){
                System.out.println("用户名 "+username+" 未注册\n请注册后再进行登录~~");
                return;
            }

            //获取对象
            int index = findlist(list, username);
            User u=list.get(index);

            //是否封号
            if(u.isStatus()==false){
                System.out.println("用户名 "+username+" 已被禁用，请联系管理员~~");
                return;
            }

            for(int i=0;i<3;i++) {
                System.out.println("请输入密码：");
                String password=sc.next();
                    String rightcode=getCode();
                    System.out.println("验证码是："+rightcode);

                    System.out.println("请输入验证码：");
                    String code=sc.next();

                if(password.equals(u.getPassword())&&code.equalsIgnoreCase(rightcode)){
                    System.out.println("登录成功，游戏启动~~");
                    Game g=new Game();
                    g.game(username);
                    break;
                }
                else{
                    System.out.println("验证码或密码输入错误，请重新输入~~");
                    System.out.println("当前剩余 "+(2-i)+" 次机会");
                    if(i==2){
                        System.out.println("密码输入错误次数过多，账号已锁定，请联系管理员~~");
                        u.setStatus(false);
                        return;
                    }
                }
            }
        }
    }

    //注册操作
    public void register(ArrayList<User>list){
        System.out.println("用户选择了注册操作");

        User u=new User();

        System.out.println("请输入用户名：");
        Scanner sc=new Scanner(System.in);
        while (true) {
            String username=sc.next();

            //校验用户名
            if(username.length()<3||username.length()>16){
                System.out.println("用户名长度必须在3-16个字符之间");
                continue;
            }

            int p=1;
            for(int i=0;i<username.length();i++){
                if(!((username.charAt(i)>='0'&&username.charAt(i)<='9')||(username.charAt(i)>='a'&&username.charAt(i)<='z')||(username.charAt(i)>='A'&&username.charAt(i)<='Z'))){
                    p=2;
                    break;
                }
                if((username.charAt(i)>='a'&&username.charAt(i)<='z')||(username.charAt(i)>='A'&&username.charAt(i)<='Z'))p=0;
            }
            if(p==2){
                System.out.println("用户名必须由数字、大小写字母组成");
                continue;
            }
            if(p==1){
                System.out.println("用户名必须包含大小写字母");
                continue;
            }

            p=0;
            for(User uu:list){
                if(uu.getUsername().equals(username)){
                    p=1;
                    break;
                }
            }
            if(p==1){
                System.out.println("用户名已存在");
                continue;
            }

            u.setUsername(username);
            break;
        }

        while(true){
            System.out.println("请输入密码：");
            String passwork1=sc.next();
            System.out.println("请再次输入密码：");
            String passwork2=sc.next();

            if(!passwork1.equals(passwork2)){
                System.out.println("两次输入的密码不一致~~");
                continue;
            }

            if(passwork1.length()<3||passwork1.length()>8){
                System.out.println("密码长度必须在3-8个字符之间~~");
                continue;
            }

            int m=0,n=0,k=0;
            for(int i=0;i<passwork1.length();i++){
                if((passwork1.charAt(i)>='a'&&passwork1.charAt(i)<='z')||(passwork1.charAt(i)>='A'&&passwork1.charAt(i)<='Z'))m=1;
                else if((passwork1.charAt(i)>='0'&&passwork1.charAt(i)<='9'))n=1;
                else k=1;
            }

            if(k==1) {
                System.out.println("密码不能包含特殊字符~~");
                continue;
            }

            if(!(m==1&&n==1)){
                System.out.println("密码必须是字母与数字的组合~~");
                continue;
            }

            u.setPassword(passwork1);
            break;
        }

        list.add(u);
        System.out.println("注册成功!!!\n您的用户名是："+u.getUsername()+"，密码是："+u.getPassword());
    }

    //退出操作
    public void exit(){
        System.out.println("用户选择了退出操作");
        System.exit(0);
    }

    //错误提示
    public void error(){
        System.out.println("输入错误，请重新选择");
    }

    public int findlist(ArrayList<User>list,String username){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }

    public String getCode(){
        StringBuilder code=new StringBuilder();
        ArrayList<Character>list=new ArrayList<>();
        for(int i=0;i<26;i++){
            list.add((char)(i+'a'));
            list.add((char)(i+'A'));
        }
        for(int i=0;i<4;i++){
            code.append(list.get(new Random().nextInt(52)));
        }
        code.insert(new Random().nextInt(4),new Random().nextInt(10));

        return code.toString();
    }
}
