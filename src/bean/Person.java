package bean;

public class Person {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;

    public Person(){

    }

    public Person(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }

    //判断是否存活
    public boolean isalive(){
        return HP>0;
    }

    //恢复血量
    public void heal(int sum){
        HP= Math.min(maxHP,HP+sum);
    }

    //受到伤害
    public void takeDamage(int damage){
        HP=Math.max(0,HP-damage);
    }

    //展示人物属性
    public void show(){
        System.out.println("名称：[ "+name+" 当前血量："+HP+" 攻击："+attack+" 防御："+defense+" ]");
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
