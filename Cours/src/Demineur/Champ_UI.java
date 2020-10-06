package Demineur;

import javax.swing.*;
import java.awt.*;

public class Champ_UI extends JPanel {
    public Client client;
    public boolean isover=false;
    private final static int DIM=500;
    private int champ_dim;
    private int champ_center=DIM/2;
    public String client_1_name;
    public String client_2_name;
    public int client_1_score =0;
    public int client_2_score =0;
    public int limit;
    JLabel my_score_ui;
    JLabel enemy_score_ui;
    int time_num=0;
    JLabel time=new JLabel("Time: "+time_num);
    JLabel result=new JLabel();
    public Champ_UI(Champ.Level level){
        if(level==Champ.Level.easy){
            limit =25;
        }
        if(level==Champ.Level.medium){
            limit =100;
        }
        if(level==Champ.Level.hard){
            limit =225;
        }
        client_1_score--;
        setSize(DIM,DIM);
        setBackground(Color.YELLOW);
        client_1_name="Score";
        my_score_ui=new JLabel(client_1_name +":"+ client_1_score);
        add(my_score_ui);
        add(result);
        add(time);
        Init_Timer();
    }
    public Champ_UI(Champ.Level level,Client c){
        this.client=c;
        if(level==Champ.Level.easy){
            limit =25;
        }
        if(level==Champ.Level.medium){
            limit =100;
        }
        if(level==Champ.Level.hard){
            limit =225;
        }
        client_1_score--;
        client_2_score--;
        setSize(DIM,DIM);
        setBackground(Color.YELLOW);
        my_score_ui=new JLabel(client_1_name +":"+ client_1_score);
        add(my_score_ui);
        enemy_score_ui=new JLabel(client_2_name +":"+ client_2_score);
        add(enemy_score_ui);
        add(result);
        add(time);
        Init_Timer();
    }

    public void Change_score(int num_client){
        if(num_client==1) {
            client_1_score++;
            if (client_1_score == limit) {
                my_score_ui.setText("You win!");
            } else {
                my_score_ui.setText(client_1_name +":"+ client_1_score);
            }
        }else{
            if(enemy_score_ui!=null) {
                client_2_score++;
                if (client_2_score == limit) {
                    enemy_score_ui.setText("You win!");
                } else {
                    enemy_score_ui.setText(client_2_name + ":" + client_2_score);
                }
            }
        }
        my_score_ui.repaint();
        enemy_score_ui.repaint();
    }
    public void Change_score(){
            client_1_score++;
            if (client_1_score == limit) {
                my_score_ui.setText("You win!");
            } else {
                my_score_ui.setText(client_1_name +":"+ client_1_score);
            }
        my_score_ui.repaint();
    }
    public void SetName(String my_name,String enemy_name){
        client_1_name=my_name;
        client_2_name=enemy_name;
        my_score_ui.setText(client_1_name +":"+ client_1_score);
        enemy_score_ui.setText(client_2_name + ":" + client_2_score);
        my_score_ui.repaint();
        enemy_score_ui.repaint();
    }
    public void SetResult(String s){
        result.setText(s);
        result.repaint();
    }
    public void Init_Timer(){
       new Thread(()->{
           long start_time=System.currentTimeMillis();
           while (!isover) {
               while (System.currentTimeMillis() - start_time > 1000) {
                   start_time = System.currentTimeMillis();
                   time_num++;
                   time.setText("Time :"+time_num);
               }
           }
        }).start();

    }
}
