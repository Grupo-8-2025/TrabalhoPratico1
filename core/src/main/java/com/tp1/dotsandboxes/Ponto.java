package com.tp1.dotsandboxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;

public class Ponto {

    private Circle circulo;
    private Color cor;

    public Ponto(Circle circulo, Color cor) {
        this.circulo = circulo;
        this.cor = cor;
    }

    public Circle getCirculo() {
        return circulo;
    }
    
    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }
    
}
