package Demineur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener {
    public enum Type{Menu,Game}
    public  enum  Menu{Easy,Medium,Hard,Quit,Connect};
    public  enum  Game{};
    private Type type;
    private Menu menu;
    private Game game;
    private Traitement traitement;
    public Listener(Type t,Menu m,Traitement tr){
        this.traitement=tr;
        this.type=t;
        this.menu=m;
    }
    public Listener(Type t,Game g,Traitement tr){
        this.traitement=tr;
        this.type=t;
        this.game=g;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(type==Type.Menu){
            if(menu==Menu.Quit){
                traitement.quit();
            }
            if(menu==Menu.Easy){
                traitement.easy();
            }
            if(menu==Menu.Medium){
                traitement.medium();
            }
            if(menu==Menu.Hard){
                traitement.hard();
            }
            if(menu==Menu.Connect){
                traitement.connect(traitement.input_name());
            }
        }

    }
}
