package Demineur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

class Case extends JPanel implements MouseListener {
    public Client client;
    public boolean isactive=true;
    private String txt = "X";
    public final static int DIM=25;
    public int position_x=0;
    public int position_y=0;
    public boolean isMine;
    public int autour;
    private Traitement traitement;
    public Case (Traitement traitement,boolean isMine,int autour,int x,int y) {
        setSize(DIM,DIM);
        setBackground(Color.ORANGE);
        addMouseListener(this); // ajout listener souris
        this.isMine=isMine;
        this.autour=autour;
        this.traitement=traitement;
        this.position_x=x;
        this.position_y=y;
    }
    /** le dessin de la case */
    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        gc.drawString(txt, 5,12);
    }
    /** la gestion de la souris */
    @Override
    public void mousePressed (MouseEvent e) {
        if (!isactive) {
            return;
        }
        if(client==null) {
            if (isMine) {
                txt = "*";
                traitement.lose();
            } else {
                txt = String.valueOf(this.autour);
                traitement.demine();
            }
        }else{
            try {
                client.click(position_x,position_y);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        repaint();
    }
    public void Display(String s){
        if(s.equals("1000")){
            txt="*";
        }else {
            txt = s;
        }
        repaint();
        isactive=false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
