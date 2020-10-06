package Demineur;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static Traitement traitement;
    public static ArrayList<Socket> user_socket=new ArrayList<>();
    public static ArrayList<Integer>user_score=new ArrayList<>();
    public static ArrayList<String> user_name=new ArrayList<>();
    public static ArrayList<DataInputStream> user_input=new ArrayList<>();
    public static ArrayList<DataOutputStream> user_output=new ArrayList<>();
    public static final int num_of_limit_client=3;
    public static int active_user=num_of_limit_client;
    Server(){
        traitement=new Traitement();
        StartSocket();
    }
    private static class StartSocket implements Runnable{
        @Override
        public void run() {
            new Thread(()->{
                try
                {
                    ServerSocket gestSock = new ServerSocket(10000);
                    while (user_name.size() < num_of_limit_client) {
                        Socket socket = gestSock.accept();
                        DataInputStream entree = new DataInputStream(socket.getInputStream());
                        DataOutputStream sortie = new DataOutputStream(socket.getOutputStream());
                        String nomJoueur = entree.readUTF();
                        if (user_name.contains(nomJoueur)) {
                            sortie.writeUTF("Fail.Same usr name");
                        } else {
                            user_socket.add(socket);
                            user_name.add(nomJoueur);
                            user_score.add(0);
                            user_input.add(entree);
                            user_output.add(sortie);
                            traitement.add_user(nomJoueur+" connected");
                            Return_name();
                            System.out.println(nomJoueur + " connected");
                        }
                    }
                    if(user_name.size()==num_of_limit_client){
                        StartGame();
                    }
                } catch(
                IOException e)
                {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    public static void Return_name() throws IOException {
        for(int i=0;i<user_output.size();i++) {
            for (int j = 0; j < user_name.size(); j++) {
                user_output.get(i).writeUTF(user_name.get(j));
            }
        }
    }
    public static void StartSocket(){
        new Thread(new StartSocket()).start();
    }
    public static void StartGame(){
        System.out.println("start");
        for(int i=0;i<user_output.size();i++) {
            try {
                user_output.get(i).writeUTF("Start");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        traitement.ui.Init_Champ(null, Champ.Level.easy);
        Demine_Manager();
    }
    public static void Demine_Manager(){
        for(int i=0;i<user_name.size();i++){
            int temp_i = i;
            new Thread(()->{
                boolean exit=false;
                while (!exit){
                    try {
                        if(active_user==0){
                            int winner=-1;
                            if(user_score.get(0)==user_score.get(1)){
                                traitement.Server_Over(null);
                            }else if(user_score.get(0)>user_score.get(1)){
                                winner=0;
                                traitement.Server_Over(user_name.get(0));
                            }else{
                                winner=1;
                                traitement.Server_Over(user_name.get(1));
                            }
                            for(int j=0;j<user_output.size();j++) {
                                if(j==winner){
                                    user_output.get(j).writeUTF("Win");
                                }else if(winner==-1){
                                    user_output.get(j).writeUTF("Draw");
                                }else{
                                    user_output.get(j).writeUTF("Lose");
                                }
                            }
                        }
                        String[]position;
                        position=user_input.get(temp_i).readUTF().split(",");
                        String result=traitement.demine(Integer.valueOf(position[0]), Integer.valueOf(position[1]));
                        if(result=="1000"){
                            for(int j=0;j<user_output.size();j++) {
                                user_output.get(j).writeUTF( position[0] +","+ position[1] +","+ result);
                                if (j == temp_i) {
                                    user_output.get(j).writeUTF("Stop");
                                    active_user--;
                                }
                            }
                        }else{
                            for(int j=0;j<user_output.size();j++) {
                                user_output.get(j).writeUTF(position[0] +","+ position[1] +","+ result);
                                if (j == temp_i) {
                                    user_score.set(j,user_score.get(j)+1);
                                    user_output.get(j).writeUTF("Get");
                                }else{
                                    user_output.get(j).writeUTF("Enemy_Get");
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        Server server=new Server();
    }
}
