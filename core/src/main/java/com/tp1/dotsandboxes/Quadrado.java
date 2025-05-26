package com.tp1.dotsandboxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Quadrado {

    private Rectangle quadrado;
    private boolean desenhado;
    private Color cor;
    private char inicialJogador = ' ';


    public Quadrado(float x, float y, float distancia, Color cor) {
        this.quadrado = new Rectangle(x, y, distancia, distancia);
        this.desenhado = false;
        this.cor = cor;
    }

    public Rectangle getQuadrado() {
        return quadrado;
    }

    public boolean getDesenhado() {
        return desenhado;
    }

    public void setDesenhado(boolean desenhado) {
        this.desenhado = desenhado;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public float getX() {
        return quadrado.x;
    }

    public float getY() {
        return quadrado.y;
    }

    public void setPosicao(float x, float y) {
        quadrado.setPosition(x, y);
    }

    public float getLargura() {
        return quadrado.width;
    }

    public float getAltura() {
        return quadrado.height;
    }

    public char getInicialJogador() {
        return inicialJogador;
    }

    public void setInicialJogador(char inicial) {
        this.inicialJogador = inicial;
    }

}
