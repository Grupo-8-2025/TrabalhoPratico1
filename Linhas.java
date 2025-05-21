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
        linhasHorizontais = new Array<>();
        linhasVerticais = new Array<>();
        shape = new ShapeRenderer();
        margem = new ShapeRenderer();

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
            if (linha.isDesenhada() || linha.isTocada()) {
                shape.setColor(cor);
                shape.rectLine(linha.getX1(), linha.getY1(), linha.getX2(), linha.getY2(),
                        linha.getLargura() + incrementoLargura);
                linha.setTocada(false);
            }
        }
    }

    public void verificaColisao(float x, float y) {
        float auxMargem = 10f;
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
            // Verifica as linhas horizontais
            for (Linha linha : linhasHorizontais) {
                if ((x >= linha.getX1() && x <= linha.getX2()) &&
                        (y >= linha.getY1() - 10 && y <= linha.getY1() + 10)) {
                    if (!linha.isDesenhada()) {
                        linha.setDesenhada(true);
                        return;
                    }
                }
            }
            for (Linha linha : linhasVerticais) {
                if ((x >= linha.getX1() - 10 && x <= linha.getX1() + 10) &&
                        (y >= linha.getY1() && y <= linha.getY2())) {
                    if (!linha.isDesenhada()) {
                        linha.setDesenhada(true);
                        return;
                    }
                }
            }
        }
    }

    public void jogarIA() {
        // 1. Tenta completar um quadrado
        for (int i = 0; i < linhasHorizontais.size; i++) {
            Linha linha = linhasHorizontais.get(i);
            if (!linha.isDesenhada() && completaQuadrado(linha, linhasHorizontais, linhasVerticais)) {
                linha.setDesenhada(true);
                linha.setCor(Color.BLUE);
                return;
            }
        }
        for (int i = 0; i < linhasVerticais.size; i++) {
            Linha linha = linhasVerticais.get(i);
            if (!linha.isDesenhada() && completaQuadrado(linha, linhasHorizontais, linhasVerticais)) {
                linha.setDesenhada(true);
                linha.setCor(Color.BLUE);
                return;
            }
        }
    
        // 2. Se não for possível, marca uma linha aleatória (horizontal ou vertical)
        Array<Linha> disponiveis = new Array<>();
        for (int i = 0; i < linhasHorizontais.size; i++) {
            Linha linha = linhasHorizontais.get(i);
            if (!linha.isDesenhada()) {
                disponiveis.add(linha);
            }
        }
        for (int i = 0; i < linhasVerticais.size; i++) {
            Linha linha = linhasVerticais.get(i);
            if (!linha.isDesenhada()) {
                disponiveis.add(linha);
            }
        }
        if (disponiveis.size > 0) {
            int idx = (int)(Math.random() * disponiveis.size);
            Linha linha = disponiveis.get(idx);
            linha.setDesenhada(true);
            linha.setCor(Color.BLUE);
        }
    }
    /*private boolean completaQuadrado(Linha linha, Array<Linha> copiaLinhasHorizontais, Array<Linha> copiaLinhasVerticais) {
        float x1 = linha.getX1();
        float y1 = linha.getY1();
        float x2 = linha.getX2();
        float y2 = linha.getY2();
    
        if (y1 == y2) {
            Linha linhaCima = encontraLinha(copiaLinhasHorizontais, x1, y1 - linha.getLargura(), x2, y2 - linha.getLargura());
            Linha linhaEsquerda = encontraLinha(copiaLinhasVerticais, x1, y1 - linha.getLargura(), x1, y1);
            Linha linhaDireita = encontraLinha(copiaLinhasVerticais, x2, y2 - linha.getLargura(), x2, y2);
    
            return linhaCima != null && linhaCima.isDesenhada() &&
                   linhaEsquerda != null && linhaEsquerda.isDesenhada() &&
                   linhaDireita != null && linhaDireita.isDesenhada();
        }
    
        if (x1 == x2) {
            Linha linhaEsquerda = encontraLinha(copiaLinhasHorizontais, x1 - linha.getLargura(), y1, x2 - linha.getLargura(), y2);
            Linha linhaDireita = encontraLinha(copiaLinhasHorizontais, x1, y1, x2, y2);
            Linha linhaBaixo = encontraLinha(copiaLinhasVerticais, x1, y2, x2, y2 + linha.getLargura());
    
            return linhaEsquerda != null && linhaEsquerda.isDesenhada() &&
                   linhaDireita != null && linhaDireita.isDesenhada() &&
                   linhaBaixo != null && linhaBaixo.isDesenhada();
        }
    
        return false;
    }*/

    private boolean completaQuadrado(Linha linha, Array<Linha> linhasHorizontais, Array<Linha> linhasVerticais) {
        float x1 = linha.getX1();
        float y1 = linha.getY1();
        float x2 = linha.getX2();
        float y2 = linha.getY2();
        float espaco = Math.abs(x2 - x1) > 0 ? Math.abs(x2 - x1) : Math.abs(y2 - y1);
    
        if (y1 == y2) {
            return completaQuadradoHorizontal(x1, y1, x2, y2, espaco, linhasHorizontais, linhasVerticais);
        }
        if (x1 == x2) {
            return completaQuadradoVertical(x1, y1, x2, y2, espaco, linhasHorizontais, linhasVerticais);
        }
        return false;
    }
    
    private boolean completaQuadradoHorizontal(float x1, float y1, float x2, float y2, float espaco,
                                               Array<Linha> linhasHorizontais, Array<Linha> linhasVerticais) {
        if (verificaQuadrado(
                encontraLinha(linhasHorizontais, x1, y1 - espaco, x2, y2 - espaco),
                encontraLinha(linhasVerticais, x1, y1 - espaco, x1, y1),
                encontraLinha(linhasVerticais, x2, y2 - espaco, x2, y2))) {
            return true;
        }
        if (verificaQuadrado(
                encontraLinha(linhasHorizontais, x1, y1 + espaco, x2, y2 + espaco),
                encontraLinha(linhasVerticais, x1, y1, x1, y1 + espaco),
                encontraLinha(linhasVerticais, x2, y2, x2, y2 + espaco))) {
            return true;
        }
        return false;
    }
    
    private boolean completaQuadradoVertical(float x1, float y1, float x2, float y2, float espaco,
                                             Array<Linha> linhasHorizontais, Array<Linha> linhasVerticais) {
        if (verificaQuadrado(
                encontraLinha(linhasVerticais, x1 - espaco, y1, x2 - espaco, y2),
                encontraLinha(linhasHorizontais, x1 - espaco, y1, x1, y1),
                encontraLinha(linhasHorizontais, x2 - espaco, y2, x2, y2))) {
            return true;
        }
        if (verificaQuadrado(
                encontraLinha(linhasVerticais, x1 + espaco, y1, x2 + espaco, y2),
                encontraLinha(linhasHorizontais, x1, y1, x1 + espaco, y1),
                encontraLinha(linhasHorizontais, x2, y2, x2 + espaco, y2))) {
            return true;
        }
        return false;
    }
    
    private boolean verificaQuadrado(Linha l1, Linha l2, Linha l3) {
        return l1 != null && l1.isDesenhada() &&
               l2 != null && l2.isDesenhada() &&
               l3 != null && l3.isDesenhada();
    }
    private Linha encontraLinha(Array<Linha> linhas, float x1, float y1, float x2, float y2) {
        for (Linha linha : linhas) {
            if (linha.getX1() == x1 && linha.getY1() == y1 &&
                    linha.getX2() == x2 && linha.getY2() == y2) {
                return linha;
            }
        }
        return null;
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