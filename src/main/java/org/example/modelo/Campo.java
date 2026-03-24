package org.example.modelo;

import org.example.Exception.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private boolean minado = false;
    private boolean aberto = false;
    private boolean marcado = false;


    private final int linha;
    private final int coluna;

    private List<Campo> listaVizinhos = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
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

    void alternaMarcado() {
        if (!aberto) {
            marcado = !marcado;
        }
    }

    boolean abrir() {
        if (!aberto && !marcado) {
            aberto = true;

            if (minado) {
                throw new ExplosaoException("KABOOM!!! FIM DE JOGO.");
            }
            if (vizinhancaSegura()) {
                listaVizinhos.forEach(v -> v.abrir());
            }

            return true;
        } else {
            return false;
        }
    }

    boolean vizinhancaSegura() {
        return listaVizinhos.stream().noneMatch(v -> v.minado);
    }

}
