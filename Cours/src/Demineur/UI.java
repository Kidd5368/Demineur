package Demineur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class UI{
    public JPanel Server_Panel;
    public Client client;
    public JFrame jf=new JFrame();
    public int num_user=0;
    public final static int DIM=500;
    public Traitement traitement;
    public Champ_UI cu;
    public ArrayList<Case> champes=new ArrayList<>();
    public UI(Traitement tr) {
        this.traitement=tr;
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
        Initilisation();
    }
    public UI(Traitement tr,Client c) {
        this.client=c;
        this.traitement=tr;
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
        Initilisation();
    }
    public void Initilisation(){
        JMenuBar menuBar = new JMenuBar();
        JMenu other=new JMenu("Other");
        JMenu difficulty=new JMenu("Difficulty");
        menuBar.add(difficulty);
        menuBar.add(other);
        JMenuItem easy=new JMenuItem("Easy", KeyEvent.VK_Q);
        difficulty.add(easy);
        easy.addActionListener(new Listener(Listener.Type.Menu, Listener.Menu.Easy,traitement));
        JMenuItem medium=new JMenuItem("Medium", KeyEvent.VK_Q);
        difficulty.add(medium);
        medium.addActionListener(new Listener(Listener.Type.Menu, Listener.Menu.Medium,traitement));
        JMenuItem hard=new JMenuItem("Hard", KeyEvent.VK_Q);
        difficulty.add(hard);
        hard.addActionListener(new Listener(Listener.Type.Menu, Listener.Menu.Hard,traitement));
        JMenuItem quit=new JMenuItem("Quit", KeyEvent.VK_Q);
        other.add(quit);
        quit.addActionListener(new Listener(Listener.Type.Menu, Listener.Menu.Quit,traitement));
        JMenuItem connect=new JMenuItem("Connect", KeyEvent.VK_Q);
        other.add(connect);
        connect.addActionListener(new Listener(Listener.Type.Menu, Listener.Menu.Connect,traitement));
        jf.setJMenuBar(menuBar);
        if(cu!=null) {
            jf.remove(cu);
        }
        for(int i=0;i<champes.size();i++){
            jf.remove(champes.get(i));
        }
        champes.clear();
    }
    public void GetMineur(Champ.Level level){
        Container container=jf.getContentPane();
        container.setLayout(null);
        container.setSize(DIM,DIM);
        Init_Champ(container, level);
        Init_Champ_UI(container,level);
    }
    public void Init_Champ(Container c,Champ.Level level){
        Champ champ=new Champ(level);
        champ.placeMines();
        int champ_dim=champ.Position.length;
        for(int i=0;i<champ_dim;i++){
            for(int j=0;j<champ_dim;j++){
                Case temp;
                if(champ.Position[i][j]==Champ.Mines) {
                    temp = new Case(traitement,true, 0,i,j);
                }else{
                    temp=new Case(traitement,false,champ.num_autour(i,j),i,j);
                }
                champes.add(temp);
                temp.setLocation(DIM/2+(i-champ_dim/2)*Case.DIM,DIM/2+(j-champ_dim/2)*Case.DIM);
                temp.client=client;
                if (c != null) {
                    c.add(temp);
                }
            }
        }
    }
    public void Init_Champ_UI(Container c,Champ.Level level){
        if(client!=null) {
            cu = new Champ_UI(level,client);
        }else{
            cu=new Champ_UI(level);
        }
        c.add(cu);
        if(client!=null) {
            cu.Change_score(1);
            cu.Change_score(2);
        }else {
            cu.Change_score();
        }
    }
    public String Name_input(){
        JTextField name = new JTextField();
        Object[] message = {
                "Name:",name
        };
        int option=JOptionPane.showConfirmDialog(null,message,"Name",JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION){
            return name.getText();
        }else{
            return null;
        }
    }
    public void Server_Add_User(String name){
        if(Server_Panel==null){
            Server_Panel=new JPanel();
            Server_Panel.setLayout(null);
            Server_Panel.setSize(500,500);
            jf.add(Server_Panel);
        }
        JLabel add=new JLabel(name);
        add.setBounds(num_user*100,0,add.getPreferredSize().width,add.getPreferredSize().height);
        num_user++;
        Server_Panel.add(add);
        Server_Panel.repaint();
        jf.setVisible(true);
    }
    public void Server_Add_Winner(String name){
        if(name==null){
            JLabel add=new JLabel("Egalité");
            add.setBounds(num_user*100,0,add.getPreferredSize().width,add.getPreferredSize().height);
            Server_Panel.add(add);
        }else {
            JLabel add=new JLabel(name+" Win!");
            add.setBounds(num_user*100,0,add.getPreferredSize().width,add.getPreferredSize().height);
            Server_Panel.add(add);
        }
        Server_Panel.repaint();
        jf.setVisible(true);
    }


}
