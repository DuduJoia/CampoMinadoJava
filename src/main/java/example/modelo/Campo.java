package example.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Campo {

    private boolean minado = false;
    private boolean aberto = false;
    private boolean marcado = false;


    private final int linha;
    private final int coluna;

    private List<Campo> listaVizinhos = new ArrayList<>();
    private List<CampoObserver> observers = new ArrayList<>();


    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void registraObservers(CampoObserver observer){
        observers.add(observer);
    }

    public void notificarObservers(CampoEvento evento){
        observers.stream().forEach(o -> o.eventoOcorreu(this,evento));
    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            listaVizinhos.add(vizinho);
            return true;

        } else if (deltaGeral == 2 && diagonal) {
            listaVizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    public void alternaMarcado() {
        if (!aberto) {
            marcado = !marcado;

            if (marcado){
                notificarObservers(CampoEvento.MARCAR);
            }else notificarObservers(CampoEvento.DESMARCAR);
        }
    }

    public boolean abrir() {
        if (!aberto && !marcado) {
            if (minado) {
                notificarObservers(CampoEvento.EXPLODIR);
                return true;
            }
            setAberto(true);


            if (vizinhancaSegura()) {
                listaVizinhos.forEach(v -> v.abrir());
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean vizinhancaSegura() {
        return listaVizinhos.stream().noneMatch(v -> v.minado);
    }

    public boolean isMarcado() {
        return marcado;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;

        if (aberto){
            notificarObservers(CampoEvento.ABRIR);
        }
    }

    public boolean isAberto() {
        return aberto;
    }

    public boolean isFechado() {
        return !aberto;
    }

    void minar() {
        minado = true;
    }

    public boolean isMinado() {
        return minado;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado() {
        boolean revelado = !minado && aberto;
        boolean protegido = minado && marcado;
        return revelado || protegido;
    }

    public int minasNaVizinhanca() {
        return (int) listaVizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar() {
        aberto = false;
        marcado = false;
        minado = false;
        notificarObservers(CampoEvento.REINICIAR);
    }
}
