package com.tp1.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Jogador {
    private String nome;
    private int pontos;
    private Color cor;
    //private TouchableAction acao;
    private Vector2 posicaoMouse;

    public Jogador(String nome, Color cor) {
        this.nome = nome;
        this.cor = cor;
        this.pontos = 0;
        //this.acao = null;
        posicaoMouse = new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    public String getNome() {
        return nome;
    }
    public int getPontos() {
        return pontos;
    }
    public Color getCor() {
        return cor;
    }
    public void update() {
        posicaoMouse.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
    }
    public Vector2 getPosicaoMouse() {
        return posicaoMouse;
    }
    public boolean realizaJogada(Array<Linha> linhasHorizontais, Array<Linha> linhasVerticais) {
    if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
        float x = posicaoMouse.x;
        float y = posicaoMouse.y;

        // Verifica se o clique foi em uma linha horizontal
        for (Linha linha : linhasHorizontais) {
            if ((x >= linha.getX1() && x <= linha.getX2()) &&
                (y >= linha.getY1() - 10 && y <= linha.getY1() + 10) &&
                !linha.isDesenhada()) {
                linha.setCor(cor);
                linha.setDesenhada(true);
                
                return true;
            }
        }

        for (Linha linha : linhasVerticais) {
            if ((x >= linha.getX1() - 10 && x <= linha.getX1() + 10) &&
                (y >= linha.getY1() && y <= linha.getY2()) &&
                !linha.isDesenhada()) {
                linha.setDesenhada(true);
                linha.setCor(cor); // Cor do jogador
                return true;
            }
        }
    }
    return false; // Jogada inválida
}
    public void incrementaPontos() {
        this.pontos++;
    }
    public void setCor(Color cor) {
        this.cor = cor;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    public void setPosicaoMouse(Vector2 posicaoMouse) {
        this.posicaoMouse = posicaoMouse;
    }

}
