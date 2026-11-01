package example.visao;

import example.modelo.Campo;
import example.modelo.CampoEvento;
import example.modelo.CampoObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObserver, MouseListener {
    final private Color BG_PADRAO = new Color(184,184,184);
    final private Color BG_MARCADO = new Color(8,179,247);
    final private Color BG_EXPLOSAO = new Color(189,66,68);
    final private Color TEXTO_VERDE = new Color(0,100,0);
    final private Color BORDER_PADRAO = new Color (0,0,0);

    private Campo campo;

    public BotaoCampo(Campo campo){
        this.campo = campo;
        setBackground(BG_PADRAO);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        campo.registraObservers(this);
    }

    public void eventoOcorreu(Campo campo, CampoEvento evento){
        switch(evento){
            case ABRIR:
                aplicarEstiloAbrir();
                break;

            case MARCAR:
                aplicarEstiloMarcar();
                setForeground(Color.black);
                break;

            case EXPLODIR:
                aplicarEstiloExplodir();
                break;

            default:
                aplicarEstiloPadrao();
                break;
        }
    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setText("");
        setBorder(BorderFactory.createBevelBorder(0));
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLOSAO);
        setText("X");

    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCADO);
        setText("M");
    }

    private void aplicarEstiloAbrir() {
    setBackground(BG_PADRAO);
    setBorder(BorderFactory.createLineBorder(Color.GRAY));

    if (campo.isMinado()){
        aplicarEstiloExplodir();
        return;
    }

    switch( campo.minasNaVizinhanca()){
        case 1:
            setForeground(TEXTO_VERDE);
            break;
        case 2:
            setForeground(Color.BLUE);
            break;
        case 3:
            setForeground(Color.YELLOW);
        case 4:
        case 5:
        case 6:
            setForeground(Color.RED);
            break;
        default:
            setForeground(Color.PINK);
            break;
        }
        String valor = ! campo.vizinhancaSegura() ?
                campo.minasNaVizinhanca() + "" : "";
        setText(valor);

        SwingUtilities.invokeLater(
                () -> {repaint(); validate();}
        );
    }

    public void mousePressed(MouseEvent e){
        if (e.getButton() == 1){
            campo.abrir();
        }
        else if (e.getButton() == 3){
            campo.alternaMarcado();
        }
    }
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}
