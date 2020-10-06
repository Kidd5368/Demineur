package Demineur;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Client {
    public String my_name;
    public String enemy_name;
    public static Traitement traitement;
    public static DataOutputStream out;
    public static DataInputStream in;
    public static ArrayList<String>user_name=new ArrayList<>();
    public Client(){
        traitement=new Traitement(this);
    }
    public void connect(String name){
        this.my_name =name;
        user_name.add(my_name);
        Socket sock;
        try {
            sock = new Socket("127.0.0.1",10000);
         out =new DataOutputStream(sock.getOutputStream());
         in = new DataInputStream(sock.getInputStream());
        if (name!=null) {
            out.writeUTF(name);
        }
        else {
            out.writeUTF("Noname");
        }
        wait_start();
        } catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void wait_start(){
        new Thread(() -> {
            boolean exit=false;
            while (!exit) {
                String nameJoueur = null;
                try {
                    nameJoueur = in.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (user_name.contains(nameJoueur)) {
                } else {
                    user_name.add(nameJoueur);
                    enemy_name=nameJoueur;
                }
                if(user_name.size()==2){
                    exit=true;
                    new Thread(()->{
                        boolean exit_2=false;
                        while(!exit_2){
                            try {
                                String infor=in.readUTF();
                                if(infor.equals("Start")){
                                    Start_Game();
                                    exit_2=true;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        }).start();
    }
    public void click(int x,int y) throws IOException {
        try {
            out.writeUTF(String.valueOf(x)+","+String.valueOf(y));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Demine_Manager(){
        new Thread(()->{
            while (true){
                String[] result;
                try {
                    result=in.readUTF().split(",");
                    if(result.length==3){
                        traitement.Display_Case(Integer.valueOf(result[0]),Integer.valueOf(result[1]),result[2]);
                    }else{
                        if(result[0].equals("Stop")){
                            Stop();
                        }else if(result[0].equals("Get")){
                            Get_Point();
                        }else if(result[0].equals("Enemy_Get")){
                            Enemy_Get();
                        }else if(result[0].equals("Win")){
                            Win();
                        }else if(result[0].equals("Draw")){
                            Draw();
                        }else if(result[0].equals("Lose")){
                            Lose();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void Start_Game(){
        traitement.client_start();
        Demine_Manager();
    }
    public static void Stop(){
        traitement.Stop();
    }
    public static void Get_Point(){
        traitement.Get_Point();
    }
    public static void Enemy_Get(){
        traitement.Enemy_Get();
    }
    public static void Win(){
        traitement.Win();
    }
    public static void Draw(){
        traitement.Draw();
    }
    public static void Lose(){
    traitement.Lose();
    }

    public static void main(String[] args) {
        new Client();
    }
}
