package bean;

import java.util.*;

public class Oneself extends Person{

    //自己的数据
    public ArrayList<String> skills;

    public Oneself(){
        super();
        skills = new ArrayList<>();
    }

    public Oneself(String name, int HP, int attack, int defense){
        super(name, HP, attack, defense);
        skills = new ArrayList<>();
    }
}
