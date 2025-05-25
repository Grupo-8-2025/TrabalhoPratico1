package com.tp1.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Linhas {

    private Array<Linha> linhasHorizontais, linhasVerticais;
    private ShapeRenderer shape, margem;
    

    public Linhas(float numBolinhas, float largura, float distancia, Color corInicial) {
        iniciaLinhas();

        criaLinhasHorizontais(numBolinhas, largura, distancia, corInicial);
        criaLinhasVerticais(numBolinhas, largura, distancia, corInicial);
    }

    private void iniciaLinhas() {
        linhasHorizontais = new Array<>();
        linhasVerticais = new Array<>();
        shape = new ShapeRenderer();
        margem = new ShapeRenderer(); 
    }

    private void criaLinhasHorizontais(float numBolinhas, float largura, float distancia, Color corInicial) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        for (int i = 0; i < numBolinhas; i++) {
            for (int j = 0; j < numBolinhas - 1; j++) {
                Linha linha = new Linha(
                        screenWidth / 2 - screenWidth / 5 + j * distancia,
                        screenHeight / 2 - screenHeight / 4 + i * distancia,
                        screenWidth / 2 - screenWidth / 5 + (j + 1) * distancia,
                        screenHeight / 2 - screenHeight / 4 + i * distancia,
                        largura, corInicial);
                linhasHorizontais.add(linha);
            }
        }
    }

    private void criaLinhasVerticais(float numBolinhas, float largura, float distancia, Color corInicial) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        for (int i = 0; i < numBolinhas - 1; i++) {
            for (int j = 0; j < numBolinhas; j++) {
                Linha linha = new Linha(
                        screenWidth / 2 - screenWidth / 5 + j * distancia,
                        screenHeight / 2 - screenHeight / 4 + i * distancia,
                        screenWidth / 2 - screenWidth / 5 + j * distancia,
                        screenHeight / 2 - screenHeight / 4 + (i + 1) * distancia,
                        largura, corInicial);
                linhasVerticais.add(linha);
            }
        }    
    }

    public void desenhaLinhas(OrthographicCamera camera, Jogador jogador) {
        iniciaShapeRender(camera, shape);
        iniciaShapeRender(camera, margem);

        desenhaArray(linhasHorizontais, jogador.getCor(), shape, 0f);
        desenhaArray(linhasVerticais, jogador.getCor(), shape, 0f);
        desenhaArray(linhasHorizontais, Color.BLACK, margem, 3f);
        desenhaArray(linhasVerticais, Color.BLACK, margem, 3f);

        verificaColisao(jogador.getPosicaoMouse().x, jogador.getPosicaoMouse().y);
        verificaClique(jogador.getPosicaoMouse().x, jogador.getPosicaoMouse().y);

        margem.end();
        shape.end();
    }

    private void iniciaShapeRender(OrthographicCamera camera, ShapeRenderer forma) {
        forma.setProjectionMatrix(camera.combined);
        forma.begin(ShapeRenderer.ShapeType.Filled);
    }

    private void desenhaArray(Array<Linha> linhas, Color cor, ShapeRenderer shape, float incrementoLargura) {
        for (Linha linha : linhas) {
            if (linha.getDesenhada()){
                shape.setColor(linha.getCor());
                shape.rectLine(linha.getX1(), linha.getY1(), linha.getX2(), linha.getY2(),
                        linha.getLargura() + incrementoLargura);
                linha.setTocada(false);
            }
            else if (linha.getTocada()) {
                shape.setColor(cor);
                shape.rectLine(linha.getX1(), linha.getY1(), linha.getX2(), linha.getY2(),
                        linha.getLargura() + incrementoLargura);
                linha.setTocada(false);
            }
        }
    }

    public void verificaColisao(float x, float y) {
        float auxMargem = 3f;
        for (Linha linha : linhasHorizontais) {
            if ((x >= linha.getX1() - auxMargem && x <= linha.getX2() + auxMargem) &&
                    y >= linha.getY1() - auxMargem && y <= linha.getY1() + auxMargem) {
                linha.setTocada(true);
            }
        }
        for (Linha linha : linhasVerticais) {
            if (x >= linha.getX1() - auxMargem && x <= linha.getX1() + auxMargem &&
                    y >= linha.getY1() - auxMargem && y <= linha.getY2() + auxMargem) {
                linha.setTocada(true);
            }
        }
    }

    public void verificaClique(float x, float y) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (Linha linha : linhasHorizontais) {
                if ((x >= linha.getX1() && x <= linha.getX2()) &&
                        (y >= linha.getY1() && y <= linha.getY1())) {
                    if (!linha.getDesenhada()) {
                        linha.setDesenhada(true);
                        return;
                    }
                }
            }
            for (Linha linha : linhasVerticais) {
                if ((x >= linha.getX1() && x <= linha.getX1()) &&
                        (y >= linha.getY1() && y <= linha.getY2())) {
                    if (!linha.getDesenhada()) {
                        linha.setDesenhada(true);
                        return;
                    }
                }
            }
        }
    }
  
    public void dispose() {
        if (shape != null) {
            shape.dispose();
        }
        if (linhasHorizontais != null) {
            linhasHorizontais.clear();
        }
        if (linhasVerticais != null) {
            linhasVerticais.clear();
        }
    }

    public Array<Linha> getLinhasHorizontais() {
        return linhasHorizontais;
    }

    public Array<Linha> getLinhasVerticais() {
        return linhasVerticais;
    }

}