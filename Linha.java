package com.tp1.dotsandboxes;

import com.badlogic.gdx.graphics.Color;

public class Linha {
    private float x1, y1, x2, y2, largura;
    private Color cor;
    private boolean desenhada, tocada;

    public Linha(float x1, float y1, float x2, float y2, float largura, Color cor) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.largura = largura;
        this.cor = cor;
        this.desenhada = false;
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public float getLargura() {
        return largura;
    }
    public void setLargura(float largura) {
        this.largura = largura;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public boolean isDesenhada() {
        return desenhada;
    }

    public void setDesenhada(boolean desenhada) {
        this.desenhada = desenhada;
    }

    public boolean isTocada() {
        return tocada;
    }
    public void setTocada(boolean tocada) {
        this.tocada = tocada;
    }
}