package Demineur;

public class Traitement {
    public UI ui;
    private Client client;
    public Traitement(){
        ui=new UI(this);
    }
    public Traitement(Client c){
        client=c;
        ui=new UI(this,this.client);
    }
    public void quit(){
        System.exit(0);
    }
    public void easy(){
        ui.Initilisation();
        ui.GetMineur(Champ.Level.easy);
    }
    public void medium(){
        ui.Initilisation();
        ui.GetMineur(Champ.Level.medium);
    }
    public void hard(){
        ui.Initilisation();
        ui.GetMineur(Champ.Level.hard);
    }
    public void connect(String name){
        client.connect(name);
    }
    public String input_name(){
        return ui.Name_input();
    }
    public void demine(){
        ui.cu.Change_score();
    }
    public void client_start(){
        easy();
        ui.cu.SetName(client.my_name,client.enemy_name);
    }
    public synchronized String demine(int x,int y) {
        for (int i = 0; i < ui.champes.size(); i++) {
            if ((ui.champes.get(i).position_x == x) && (ui.champes.get(i).position_y == y)) {
                if(ui.champes.get(i).isMine){
                    return "1000";
                }else{
                    return String.valueOf(ui.champes.get(i).autour);
                }
            }
        }
        return "999";
    }
    public void lose(){
        for(int i=0;i<ui.champes.size();i++) {
            ui.champes.get(i).isactive=false;
        }
        ui.cu.SetResult("You Lose!");
        ui.cu.isover=true;
    }
    public void add_user(String name){
        ui.Server_Add_User(name);
    }
    public void Display_Case(int x,int y,String s){
        for(int i=0;i<ui.champes.size();i++){
            if((ui.champes.get(i).position_x==x)&&(ui.champes.get(i).position_y==y)){
                ui.champes.get(i).Display(s);
            }
        }
    }
    public void Stop(){
        for(int i=0;i<ui.champes.size();i++){
            ui.champes.get(i).isactive=false;
        }
        ui.cu.isover=true;
    }
    public void Get_Point(){
        ui.cu.Change_score(1);
    }
    public void Enemy_Get(){
        ui.cu.Change_score(2);
    }
    public void Win(){
        for(int i=0;i<ui.champes.size();i++){
            ui.champes.get(i).isactive=false;
        }
        ui.cu.SetResult("You Win!");
        ui.cu.isover=true;
    }
    public void Draw(){
        ui.cu.SetResult("Egalité!");
        ui.cu.isover=true;
    }
    public  void Lose(){
        ui.cu.SetResult("You Lose!");
        ui.cu.isover=true;
    }
    public void Server_Over(String name){
        ui.Server_Add_Winner(name);
    }
    public static void main(String[] args) {
        new Traitement();
    }
}
