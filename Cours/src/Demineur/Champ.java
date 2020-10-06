package Demineur;

import java.util.Random;

public class Champ {
    final Random r=new Random();
    final static int Mines=9;
    public enum Level{easy,medium,hard};
    int Nombre=0;
    int Dim=0;
    int[][] Position;
    public Champ(Level diffi){
        if(diffi==Level.easy) {
            Champ_construct(25, 10);
        }else if(diffi==Level.medium){
            Champ_construct(30,13);
        }else if(diffi==Level.hard){
            Champ_construct(40,16);
        }
    }
    public void Champ_construct(int number,int dimension){
        if(number>dimension*dimension){
            System.out.println("Trop bcp de mines");
            System.exit(0);
        }
        this.Nombre=number;
        this.Dim=dimension;
        Position=new int[Dim][Dim];
    }
    public void placeMines(){
        for(int i=0;i<Nombre;i++){
            int x=r.nextInt(Dim);
            int y=r.nextInt(Dim);
            while (Position[x][y]==Mines){
                x=r.nextInt(Dim);
                y=r.nextInt(Dim);
            }
            Position[x][y]=Mines;
            }
        }
    public int num_autour(int x,int y){
        int output=0;
        int[] x_list=new int[]{x-1,x-1,x-1,x,x,x+1,x+1,x+1};
        int[] y_list=new int[]{y-1,y,y+1,y-1,y+1,y-1,y,y+1};
        for(int i=0;i<8;i++){
            if((x_list[i]>=0)&&(y_list[i]>=0)&&(x_list[i]<Dim)&&(y_list[i]<Dim)){
                if(Position[x_list[i]][y_list[i]]==Mines){
                    output++;
                }
            }
        }
        return output;
    }
    public String toString(){
        String temp=new String("");
        for(int i=0;i<Position.length;i++) {
            for (int j = 0; j < Position[0].length; j++) {
                if (Position[i][j] == Mines) {
                    temp+='X';
                }else{
                    temp+='0';
                }
            }
            temp+="\n";
        }
        return temp;
    }
    public void affText(){
        System.out.println(toString());
    }
}
