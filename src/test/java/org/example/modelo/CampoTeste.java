package org.example.modelo;

import org.example.Exception.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CampoTeste {

    private Campo campo;

    @BeforeEach
    void iniciarCampo(){
        campo = new Campo(3,3);
    }

    @Test
    void testeVizinhoRealDistancia1Esquerda(){
        Campo vizinho = new Campo(3,2);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeVizinhoRealDistancia1Direita(){
        Campo vizinho = new Campo(3,4);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeVizinhoRealDistancia1EmCima(){
        Campo vizinho = new Campo(2,3);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia1EmBaixo(){
        Campo vizinho = new Campo(4,3);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia2Diagonal(){
        Campo vizinho = new Campo(2,2);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }


    @Test
    void testeVizinhoFalso(){
        Campo vizinho = new Campo(1,1);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);
    }

    @Test
    void testeValorPadraoAtributoMarcado(){
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacado(){
        campo.alternaMarcado();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacadoDuasChamadas(){
        campo.alternaMarcado();
        campo.alternaMarcado();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAbrirMinadoMarcado(){
        campo.alternaMarcado();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoNaoMarcado(){
        campo.minar();
        assertThrows(ExplosaoException.class, () -> {
            campo.abrir();
        });
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirComVizinho(){

        Campo campo22 = new Campo(2,2);
        Campo campo11 = new Campo(1,1);
        campo.adicionarVizinho(campo22);
        campo22.adicionarVizinho(campo11);

        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    void testeAbrirComVizinho2(){

        Campo campo11 = new Campo(1,1);
        Campo campo12 = new Campo(1,2);
        campo12.minar();

        Campo campo22 = new Campo(2,2);
        campo22.adicionarVizinho(campo12);
        campo22.adicionarVizinho(campo11);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isFechado());
    }
}
