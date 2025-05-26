package com.tp1.dotsandboxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class Computador extends Jogador {

    private int dificuldade;

    public Computador(Color cor, int dificuldade) {
        super("Computador", cor);
        this.dificuldade = dificuldade;
    }

    public void jogar(Array<Linha> linhasHorizontais, Array<Linha> linhasVerticais) {
        if (dificuldade == 1) {
            if(!modoDificil(linhasHorizontais, linhasVerticais)){
                modoBasico(linhasHorizontais, linhasVerticais);
            }
        }else{
            modoBasico(linhasHorizontais, linhasVerticais);
        }
    }

    private void modoBasico(Array<Linha> linhasHorizontais, Array<Linha> linhasVerticais) {
        Array<Linha> disponiveis = new Array<>();
        for (int i = 0; i < linhasHorizontais.size; i++) {
            Linha linha = linhasHorizontais.get(i);
            if (!linha.getDesenhada()) {
                linha.setCor(getCor());
                disponiveis.add(linha);
            }
        }
        for (int i = 0; i < linhasVerticais.size; i++) {
            Linha linha = linhasVerticais.get(i);
            if (!linha.getDesenhada()) {
                linha.setCor(getCor());
                disponiveis.add(linha);
            }
        }
        if (disponiveis.size > 0) {
            int idx = (int) (Math.random() * disponiveis.size);
            Linha linha = disponiveis.get(idx);
            linha.setDesenhada(true);
        }
    }

    private boolean modoDificil(Array<Linha> linhasHorizontais, Array<Linha> linhasVerticais) {
        for (int i = 0; i < linhasHorizontais.size; i++) {
            Linha linha = linhasHorizontais.get(i);
            if (!linha.getDesenhada() && completaQuadrado(linha, linhasHorizontais, linhasVerticais)) {
                linha.setCor(getCor());
                linha.setDesenhada(true);
                return true;
            }
        }
        for (int i = 0; i < linhasVerticais.size; i++) {
            Linha linha = linhasVerticais.get(i);
            if (!linha.getDesenhada() && completaQuadrado(linha, linhasHorizontais, linhasVerticais)) {
                linha.setCor(getCor());
                linha.setDesenhada(true);
                return true;
            }
        }
        return false;
    }

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
        return l1 != null && l1.getDesenhada() &&
                l2 != null && l2.getDesenhada() &&
                l3 != null && l3.getDesenhada();
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

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

}
