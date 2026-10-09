package bean;

public class Enemy extends Person{

    //敌人的数据
    public String skill;
    public boolean buff;

    public Enemy() {
        super();
    }

    public Enemy(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    public void takeDamage(int damage){
        if(buff){
            buff=false;
            damage=Math.max(damage/2,1);
        }
        super.takeDamage(damage);
    }

}
