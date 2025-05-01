package com.davi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Linhas {

    // Aqui estamos declarando as variáveis
    // ShapeRenderer é usado para desenhar formas
    // como linhas e círculos
    // Ele é usado para desenhar as linhas na tela
    private ShapeRenderer shapeRenderer;
    // O Vector2 é uma classe que representa um vetor 2D
    // e que tem métodos para calcular a distância entre dois pontos
    // e para calcular a posição dos pontos na tela
    // O ponto inicial e o ponto final são os pontos que representam a linha
    // O ponto inicial é o ponto onde a linha começa
    // O ponto final é o ponto onde a linha termina
    Vector2 pontoInicial = new Vector2();
    Vector2 pontoFinal = new Vector2();
    // O linhaSelecionada é uma variável booleana que indica se a linha foi selecionada ou não
    private boolean linhaSelecionada = false;
    // O pessoaSelecionou é uma variável booleana que indica se a pessoa selecionou a linha 
    // ou se foi o robo que sera adicionado
    private boolean pessoaSelecionou = false;

    //inicia linha
    public Linhas(ShapeRenderer shapeRenderer, float inicioEixoX, float inicioEixoY, float finalEixoX, float finalEixoY) {
        this.shapeRenderer = shapeRenderer;
        this.pontoInicial.x = inicioEixoX;
        this.pontoInicial.y = inicioEixoY;
        this.pontoFinal.x = finalEixoX;
        this.pontoFinal.y = finalEixoY;
    }

    //verifica se o mouse esta sobre a linha no eixo X
    public boolean mouseOverX(){
        //gdx.input.getX() pega a posicao do mouse no eixo X
        //pontoInicial.x e pontoFinal.x pega a posicao do ponto inicial e final da linha no eixo X
        if (pontoInicial.x < Gdx.input.getX() && Gdx.input.getX() < pontoFinal.x) {
            return true;
        } else {
            return false;
        }
    }

    //verifica se o mouse esta sobre a linha no eixo Y
    public boolean mouseOverY(){
        //gdx.input.getY() pega a posicao do mouse no eixo Y
        //pontoInicial.y e pontoFinal.y pega a posicao do ponto inicial e final da linha no eixo Y
        if (pontoInicial.y < Gdx.input.getY() && Gdx.input.getY() < pontoFinal.y) {
            return true;
        } else {
            return false;
        }
    }

    //verifica se o mouse esta sobre a linha
    public boolean mouseOver(){
        if (mouseOverX() && mouseOverY()) {
            return true;
        } else {
            return false;
        }
    }
    //verifica se o mouse esta sobre a linha e se ele é clicado
    public void linhaSelecionada(){
        //gdx.input.isTouched() verifica se o mouse esta clicado
        //se o mouse estiver sobre a linha e o mouse estiver clicado, a linha 
        //supostamente é selecionada
        if (mouseOver() && Gdx.input.isTouched()) {
            linhaSelecionada = true;
            pessoaSelecionou = true;
        } else {
            
        }
    }

    /*
     * A logica da função abaixo esta um pouco confusa, e quebrada, mas o que ela deveria
     * fazer é mudar a cor da linha da linha para azul quando o usuario clicar na linha (if 1),
     * mudar para vermelho quando o robo que sera adcionado a selecionar (else if 1), quando nada
     * acima acontecer ele puxar outra vereificacao (else 1)quando o usuario passar o mouse onde
     * seria a linha, ela aparecer porem transparente (if 2),quando nenhuma das opcoes acontecer
     * era para acontecer nada, mas como os if deram errado,eu colocoquei para elas aparecerem de 
     * qualquer jeito (else 2), e por fim, o codigo faz uma verificacao no final. - o erro deve estar
     *  ai - 
     */
    public void desenharLinhas() {
        //if 1
        if(linhaSelecionada && pessoaSelecionou){ 
            //ShapeRenderer.ShapeType.Filled indica que a linha sera preenchida
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0,0,1,1); 
            //rectLine desenha a linha entre o ponto inicial e o ponto final
            //5f indica a espessura da linha
            //se eu usar Line ao inves de rectLine, a linha fica mais fina
            //e nao posso mudar a espessura dela
            shapeRenderer.rectLine(pontoInicial, pontoFinal, 5f);
            shapeRenderer.end();
        }
        //else if 1
        else if(linhaSelecionada && !pessoaSelecionou){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1,0,0,1); 
            shapeRenderer.rectLine(pontoInicial, pontoFinal, 5f);
            shapeRenderer.end();
        }
        //else 1
        else {
            //if 2
            if(mouseOver()){
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1,0,0,0.5f); 
                shapeRenderer.rectLine(pontoInicial, pontoFinal, 5f);
                shapeRenderer.end();
            }
            //else 2
            else {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1,0,0,0.5f); 
                shapeRenderer.rectLine(pontoInicial, pontoFinal, 5f);
                shapeRenderer.end();
            }
            //verificacao final
            linhaSelecionada();
        }
    }
}
