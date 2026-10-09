package ui;

import bean.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public void game(String username){
        System.out.println("╔════════════════════════════════════════╗\n"
                + "🎮 " + username + "欢迎来到文字格斗游戏 🎮\n"
                + "╚════════════════════════════════════════╝");
        //创建角色对象
        Oneself my=myplay(username);

        System.out.println("角色创建成功！");
        System.out.println("\uD83C\uDF1F 初始属性: "+my.name+" [HP: "+my.HP+"/"+my.HP+", ATK: "+my.attack+", DEF: "+my.defense+"]");
        System.out.println("\uD83C\uDF1F 拥有技能: 普通攻击, 强力一击, 生命汲取");

        ArrayList<Enemy> enemy=new ArrayList<>();
        enemy.add(new Enemy("初级战士", 80, 15, 10, "猛击"));
        enemy.add(new Enemy("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemy.add(new Enemy("重装坦克", 120, 10, 20, "防御姿态"));
        enemy.add(new Enemy("神秘法师", 70, 25, 8, "火球术"));
        enemy.add(new Enemy("老吴", 50, 0, 0, "哈气"));

        int count=1;
        int win=0;

        while(my.isalive()){
            //敌人越来越强
            if(win!=0){
                for(int i=0;i<enemy.size();i++){
                    Enemy e=enemy.get(i);
                    e.maxHP+=10;
                    e.HP=e.maxHP;
                    e.attack+=3;
                    e.defense+=2;
                    e.buff=false;
                }
            }
            //随机获取敌人
            Enemy e=enemy.get(new Random().nextInt(enemy.size()));
            e.show();
            //开始战斗
            int round=1;
            System.out.println("\n═══════════════════════════════════════");
            System.out.println("⚔️ 第 "+count+" 场战斗开始！对手: "+e.name);
            while(true){
                System.out.println("---------------------------------------");
                System.out.println("⚔\uFE0F 第 "+round+" 回合开始！ ");
                //打印敌我双方血条
                System.out.println(getheal(username, my.HP, my.maxHP));
                System.out.println(getheal(e.name, e.HP, e.maxHP));
                //玩家回合
                myturn(my,e);
                if(e.isalive()==false){
                    System.out.println("\uD83C\uDF89 你击败了 "+e.name+"!");
                    win++;
                    break;
                }
                //敌人回合
                eturn(my,e);
                if(my.isalive()==false){
                    System.out.println("你被 "+e.name+" 击败了~~");
                    break;
                }

                round++;
            }
            //战斗结束
            if(my.isalive()){
                int healHP=new Random().nextInt(20,41);
                my.heal(healHP);
                System.out.println("\uD83C\uDF89 战斗结束！你恢复了 "+healHP+" 点生命值");
                System.out.println("\uD83C\uDFC6 当前胜场: "+win);
            }
            if(win%3==0){
                my.maxHP+=30;
                my.heal(30);
                my.attack+=5;
                my.defense+=3;
                System.out.println("\uD83C\uDF89 获胜 "+win+" 回合，你获得了30点生命值，5点攻击力和3点防御力");
                System.out.print("你的属性：");
                my.show();
            }
            if(my.isalive()){
                System.out.println("继续下一场战斗？(y/n): ");
                Scanner sc=new Scanner(System.in);
                String choice=sc.next();
                if(choice.equals("n")){
                    System.out.println("游戏结束，感谢游玩！");
                    break;
                }
                else if(choice.equals("y")){
                    System.out.println("继续下一场战斗");
                    count++;
                }
                else{
                    System.out.println("输入错误，默认游戏继续");
                    count++;
                }
            }
        }

        System.out.println("\n═══════════════════════════════════════");
        System.out.println("你的最终属性：");
        my.show();
        System.out.println("总胜场："+win);
        System.out.println("游戏结束，感谢游玩！");
        System.exit(0);
    }

    //打印血条
    public String getheal(String username,int HP,int maxHP){

        int count=20;
        int fill=(int)(HP*1.0/maxHP*count);

        StringBuilder h=new StringBuilder(username+": [");
        for(int i=0;i<count;i++){
            if(i<fill)
                h.append("█");
            else
                h.append(" ");
        }
        h.append("] "+HP+"/"+maxHP+" HP");
        return h.toString();
    }

    //玩家回合
    public void myturn(Oneself my, Enemy e){
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.println("选择行动 (1-3): ");
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();

        switch(n){
            default->{
                System.out.println("输入错误！默认使用普通攻击");
            }
            case(1)->{
                e.takeDamage(my.attack);
                System.out.println("⚔\uFE0F 你 对 "+e.name+" 使用了普通攻击，造成 "+my.attack+" 点伤害！");
                break;
            }
            case(2)->{
                if(my.HP>10){
                    my.takeDamage(10);
                    e.takeDamage((int)(my.attack*1.8));
                    System.out.println("\uD83D\uDCA5  消耗10HP，你 对 "+e.name+" 使用了强力一击，造成 "+(int)(my.attack*1.8)+" 点伤害！");
                }
                else{
                    System.out.println("你的HP不足，无法使用强力一击");
                    e.takeDamage(my.attack);
                    System.out.println("⚔\uFE0F 你 对 "+e.name+" 使用了普通攻击，造成 "+my.attack+" 点伤害！");
                }
                break;
            }
            case(3)->{
                if(my.HP>=10){
                    my.takeDamage(10);
                    int t=new Random().nextInt(1,21);
                    my.heal(t);
                    System.out.println("\uD83D\uDC9A  消耗10HP，你 使用了生命汲取，恢复了 "+t+" 点生命！");
                }
                else{
                    System.out.println("你的HP不足，无法使用生命汲取");
                    e.takeDamage(my.attack);
                    System.out.println("⚔\uFE0F 你 对 "+e.name+" 使用了普通攻击，造成 "+my.attack+" 点伤害！");
                }
                break;
            }
        }
    }

    //敌方回合
    public void eturn(Oneself my, Enemy e){
        System.out.println("===== "+e.name+"的回合 =====");
        int t=new Random().nextInt(200);
        if (t<=99) {
            switch(e.skill){
                case("哈气")->{
                    System.out.println("哦~你搞你搞你搞你搞你搞~~");
                }
                case("猛击")->{
                    my.takeDamage((int)(e.attack*1.5));
                    System.out.println("⚔\uFE0F "+e.name+" 对 你 使用了猛击，造成 "+(int)(e.attack*1.5)+" 点伤害！");
                    break;
                }
                case("快速攻击")->{
                    my.takeDamage((int)(e.attack*0.5));
                    System.out.println("⚔\uFE0F "+e.name+" 对 你 使用了快速攻击第一段，造成 "+(int)(e.attack*0.5)+" 点伤害！");
                    my.takeDamage((int)(e.attack*0.6));
                    System.out.println("⚔\uFE0F "+e.name+" 对 你 使用了快速攻击第二段，造成 "+(int)(e.attack*0.6)+" 点伤害！");
                    break;
                }
                case("防御姿态")->{
                    e.buff=true;
                    System.out.println("⚔\uFE0F "+e.name+" 使用了防御姿态，下回合受到的伤害降低了");
                    break;
                }
                case("火球术")->{
                    my.takeDamage(e.attack*2);
                    System.out.println("⚔\uFE0F "+e.name+" 对 你 使用了火球术，造成 "+e.attack*2+" 点伤害！");
                    break;
                }
                default->{}
            }
        }
        else {
            my.takeDamage(e.attack);
            System.out.println("⚔\uFE0F "+e.name+" 对 你 使用了普通攻击，造成 "+e.attack+" 点伤害！");
        }
    }

    //创建角色
    public Oneself myplay(String username){
        System.out.println("创建你的角色:");
        System.out.println("您的角色名为: "+username);
        System.out.println("请分配属性点 (共20点):");
        Scanner sc=new Scanner(System.in);

        int point=20;
        System.out.println("1. 生命值 (每点+10 HP)");
        System.out.println("2. 攻击力 (每点+2 ATK)");
        System.out.println("3. 防御力 (每点+1 DEF)");

        int a=100,b=10,c=0;

        //处理生命值
        System.out.println("请为生命值加点：");
        int HPpoint=sc.nextInt();
        if(HPpoint<0){
            HPpoint=0;
            System.out.println("无效输入！自动分配 0 点");
        }
        else if(HPpoint>20){
            HPpoint=20;
            point=0;
            System.out.println("属性点不足！剩余点数全部分配到生命值");
        }
        else {
            System.out.println("您分配了 "+HPpoint+" 点属性点到生命值, 剩余 "+point+" 点属性点");
            point-=HPpoint;
        }
        a+=HPpoint*10;

        //处理攻击力
        if (point>0) {
            System.out.println("请为攻击力加点：");
            int ATKpoint=sc.nextInt();
            if(ATKpoint<0){
                ATKpoint=0;
                System.out.println("无效输入！自动分配 0 点");
            }
            else if(ATKpoint>point){
                ATKpoint=point;
                System.out.println("属性点不足！剩余点数全部分配到攻击力");
            }
            else {
                System.out.println("您分配了 "+ATKpoint+" 点属性点到攻击力, 剩余 "+point+" 点属性点");
                point-=ATKpoint;
            }
            b+=ATKpoint*2;
        }
        else{
            System.out.println("您没有剩余的属性点，攻击力将自动分配 0");
        }

        //处理防御力
        if (point>0) {
            System.out.println("请为防御力加点：");
            int DEFpoint=sc.nextInt();
            if(DEFpoint<0){
                DEFpoint=0;
                System.out.println("无效输入！自动分配 0 点");
            }
            else if(DEFpoint>point){
                DEFpoint=point;
                System.out.println("属性点不足！剩余点数全部分配到防御力");
            }
            else {
                System.out.println("您分配了 "+DEFpoint+" 点属性点到防御力, 剩余 "+point+" 点属性点");
                point-=DEFpoint;
            c+=DEFpoint;
            }
        }
        else{
            System.out.println("您没有剩余的属性点，防御力将自动分配 0");
        }

        Oneself my=new Oneself(username,a,b,c);

        my.skills.add("普通攻击");
        my.skills.add("强力一击");
        my.skills.add("生命汲取");

        return my;
    }
}
