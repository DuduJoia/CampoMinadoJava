package example.visao;

import example.modelo.Tabuleiro;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(){
        Tabuleiro tab = new Tabuleiro(16,30,50);

        add( new PainelTabuleiro(tab));
        setTitle("CAMPO MINADO");
        setSize(690,430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new TelaPrincipal();

    }


}
