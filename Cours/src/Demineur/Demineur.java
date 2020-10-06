package Demineur;

public class Demineur {
    static Champ c=new Champ(Champ.Level.easy);

    public static void main(String[] args) {
        c.placeMines();
        c.affText();
        System.out.println(c.num_autour(0, 0));
    }
}
