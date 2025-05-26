package com.tp1.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;

public class Pontos {

    private ShapeRenderer shape, margem;
    private Array<Ponto> bolinhas;
    private float numBolinhas;

    public Pontos(float numBolinhas, float raio, float distancia, Color corInicial) {
        this.numBolinhas = numBolinhas;
        bolinhas = new Array<>();
        shape = new ShapeRenderer();
        margem = new ShapeRenderer();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        for (int i = 0; i < numBolinhas; i++) {
            for (int j = 0; j < numBolinhas; j++) {
                Circle circulo = new Circle(screenWidth / 2 - screenWidth / 5 + i * distancia,
                        screenHeight / 2 - screenHeight / 4 + j * distancia, raio);
                bolinhas.add(new Ponto(circulo, new Color(corInicial)));
            }
        }
    }

    public void desenhaBolinhas(OrthographicCamera camera, Jogador jogador) {
        iniciaShapeRender(camera, shape);
        iniciaShapeRender(camera, margem);

        for (Ponto bolinha : bolinhas) {
            shape.setColor(bolinha.getCor());
            Circle c = bolinha.getCirculo();
            shape.circle(c.x, c.y, c.radius);
        }
        for (Ponto bolinha : bolinhas) {
            margem.setColor(Color.BLACK);
            Circle c = bolinha.getCirculo();
            margem.circle(c.x, c.y, c.radius + 1f);
        }

        margem.end();
        shape.end();
    }

    private void iniciaShapeRender(OrthographicCamera camera, ShapeRenderer forma){
        forma.setProjectionMatrix(camera.combined);
        forma.begin(ShapeRenderer.ShapeType.Filled);
    }

    void verificaColisao(Jogador jogador) {
        float x = jogador.getPosicaoMouse().x;
        float y = jogador.getPosicaoMouse().y;
        for (Ponto bolinha : bolinhas) {
            Circle c = bolinha.getCirculo();
            if (c.contains(x, y)) {
                bolinha.setCor(jogador.getCor());
            } else {
                bolinha.setCor(Color.CORAL);
            }
        }
    }

    public float getNumBolinhas() {
        return numBolinhas;
    }

    public Array<Ponto> getBolinhas() {
        return bolinhas;
    }

    public void dispose() {
        if (shape != null) {
            shape.dispose();
        }
        if (bolinhas != null) {
            bolinhas.clear();
        }
    }

}