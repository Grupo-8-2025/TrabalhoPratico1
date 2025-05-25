package com.tp1.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Quadrados {

    private float margemErro = 5f;

    private Array<Quadrado> quadrados;
    private float espaco;
    private ShapeRenderer shapeRenderer;

    public Quadrados(float linhas, float colunas, float espaco, Color cor) {
        this.espaco = espaco;
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

    public int verificaQuadrados(Array<Linha> linhasH, Array<Linha> linhasV, DotsAndBoxes jogo) {
        int quadradosFechados = 0;
        for (Quadrado quadrado : quadrados) {
            if (quadrado.getDesenhado())
                continue;
    
            if (quadradoFechado(quadrado, linhasH, linhasV)) {
                realizaVerificacaoQuadrado(quadrado, linhasH, linhasV, jogo);
                quadradosFechados++;
            }
        }
        return quadradosFechados;
    }
    
    private boolean quadradoFechado(Quadrado quadrado, Array<Linha> linhasH, Array<Linha> linhasV) {
        float x = quadrado.getX();
        float y = quadrado.getY();
    
        Linha cima = encontraLinha(linhasH, x, y, x + espaco, y);
        Linha baixo = encontraLinha(linhasH, x, y + espaco, x + espaco, y + espaco);
        Linha esquerda = encontraLinha(linhasV, x, y, x, y + espaco);
        Linha direita = encontraLinha(linhasV, x + espaco, y, x + espaco, y + espaco);
    
        return cima != null && cima.getDesenhada() &&
               baixo != null && baixo.getDesenhada() &&
               esquerda != null && esquerda.getDesenhada() &&
               direita != null && direita.getDesenhada();
    }
    
    private void realizaVerificacaoQuadrado(Quadrado quadrado, Array<Linha> linhasH, Array<Linha> linhasV, DotsAndBoxes jogo) {
        float x = quadrado.getX();
        float y = quadrado.getY();
    
        Linha cima = encontraLinha(linhasH, x, y, x + espaco, y);
        Linha baixo = encontraLinha(linhasH, x, y + espaco, x + espaco, y + espaco);
        Linha esquerda = encontraLinha(linhasV, x, y, x, y + espaco);
        Linha direita = encontraLinha(linhasV, x + espaco, y, x + espaco, y + espaco);
    
        Linha ultima = getUltimaLinhaDesenhada(cima, baixo, esquerda, direita);
        quadrado.setCor(ultima != null ? ultima.getCor() : Color.GRAY);
        quadrado.setDesenhado(true);
    
        if (ultima != null && jogo != null) {
            Jogador j = jogo.getJogadorPorCor(ultima.getCor());
            if (j != null) {
                j.incrementaPontos();
                char inicial = j.getNome().isEmpty() ? ' ' : Character.toUpperCase(j.getNome().charAt(0));
                quadrado.setInicialJogador(inicial);
            }
        }
        if (jogo != null && jogo.getErroSound() != null) {
            jogo.tocarErro();
        }
    }

    private Linha getUltimaLinhaDesenhada(Linha cima, Linha baixo, Linha esquerda, Linha direita) {
        Linha[] linhas = { cima, baixo, esquerda, direita };
        Linha ultima = null;
        long ultimaTime = -1;
        for (Linha l : linhas) {
            if (l != null && l.getDesenhada() && l.getDesenhadaTimestamp() > ultimaTime) {
                ultima = l;
                ultimaTime = l.getDesenhadaTimestamp();
            }
        }
        return ultima;
    }

    private Linha encontraLinha(Array<Linha> linhas, float x1, float y1, float x2, float y2) {
        for (Linha linha : linhas) {
            boolean matchDireto = Math.abs(linha.getX1() - x1) < margemErro && Math.abs(linha.getY1() - y1) < margemErro
                    &&
                    Math.abs(linha.getX2() - x2) < margemErro && Math.abs(linha.getY2() - y2) < margemErro;

            boolean matchInverso = Math.abs(linha.getX1() - x2) < margemErro
                    && Math.abs(linha.getY1() - y2) < margemErro &&
                    Math.abs(linha.getX2() - x1) < margemErro && Math.abs(linha.getY2() - y1) < margemErro;

            if (matchDireto || matchInverso) {
                return linha;
            }
        }
        return null;
    }

    public void desenhaQuadrados(Camera camera, BitmapFont font, SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Quadrado quadrado : quadrados) {
            if (quadrado.getDesenhado()) {
                shapeRenderer.setColor(quadrado.getCor());
                shapeRenderer.rect(quadrado.getX(), quadrado.getY(), espaco, espaco);
            }
        }
        shapeRenderer.end();

        desenhaInicial(camera, font, batch);
    }

    private void desenhaInicial(Camera camera, BitmapFont font, SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        
        batch.begin();
        for (Quadrado quadrado : quadrados) {
            if (quadrado.getDesenhado()) {
                float x = quadrado.getX() + espaco / 2f - 15;
                float y = quadrado.getY() + espaco / 2f + 10;
                font.setColor(Color.WHITE);
                font.draw(batch, String.valueOf(quadrado.getInicialJogador()), x, y);
            }
        }
        batch.end();

        shapeRenderer.end();
    }

    public Array<Quadrado> getQuadrados() {
        return quadrados;
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (quadrados != null) {
            quadrados.clear();
        }
    }
    
}
