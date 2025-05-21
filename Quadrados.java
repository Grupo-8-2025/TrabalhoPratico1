package com.tp1.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Quadrados {

    private float margemErro = 5f;

    private Array<Quadrado> quadrados;
    private float linhas, colunas, espaco;
    private Color cor;
    private ShapeRenderer shapeRenderer;

    public Quadrados(float linhas, float colunas, float espaco, Color cor) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.espaco = espaco;
        this.cor = cor;
        this.shapeRenderer = new ShapeRenderer();
        this.quadrados = new Array<>();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
    
        for (int i = 0; i < linhas - 1; i++) {
            for (int j = 0; j < colunas - 1; j++) {
                float x = screenWidth / 2 - screenWidth / 5 + i * espaco;
                float y = screenHeight / 2 - screenHeight / 4 + j * espaco;
                quadrados.add(new Quadrado(x, y, espaco, cor));
            }
        }
    }
    

    public void verificaQuadrados(Array<Linha> linhasH, Array<Linha> linhasV) {
        for (Quadrado quadrado : quadrados) {
            if (quadrado.isDesenhado()) continue;

            float x = quadrado.getX();
            float y = quadrado.getY();

            Linha cima = encontraLinha(linhasH, x, y, x + espaco, y);
            Linha baixo = encontraLinha(linhasH, x, y + espaco, x + espaco, y + espaco);
            Linha esquerda = encontraLinha(linhasV, x, y, x, y + espaco);
            Linha direita = encontraLinha(linhasV, x + espaco, y, x + espaco, y + espaco);

            if (cima != null && cima.isDesenhada() &&
                baixo != null && baixo.isDesenhada() &&
                esquerda != null && esquerda.isDesenhada() &&
                direita != null && direita.isDesenhada()) {
                quadrado.setDesenhado(true);
            }
        }
    }

    private Linha encontraLinha(Array<Linha> linhas, float x1, float y1, float x2, float y2) {
        for (Linha linha : linhas) {
            boolean matchDireto =
                    Math.abs(linha.getX1() - x1) < margemErro && Math.abs(linha.getY1() - y1) < margemErro &&
                    Math.abs(linha.getX2() - x2) < margemErro && Math.abs(linha.getY2() - y2) < margemErro;

            boolean matchInverso =
                    Math.abs(linha.getX1() - x2) < margemErro && Math.abs(linha.getY1() - y2) < margemErro &&
                    Math.abs(linha.getX2() - x1) < margemErro && Math.abs(linha.getY2() - y1) < margemErro;

            if (matchDireto || matchInverso) {
                return linha;
            }
        }
        return null;
    }

    public void desenhaQuadrados(Camera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(cor);

        for (Quadrado quadrado : quadrados) {
            if (quadrado.isDesenhado()) {
                shapeRenderer.rect(quadrado.getX(), quadrado.getY(), espaco, espaco);
            }
        }

        shapeRenderer.end();
    }

    public Array<Quadrado> getQuadrados() {
        return quadrados;
    }
}
