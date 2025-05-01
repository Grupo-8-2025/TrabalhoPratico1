package com.davi.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pontos {
    
    // Aqui estamos declarando as variáveis
    //static private int contador = 0;
    // contador é uma variável estática que conta o número de pontos
    // que foram criados
    // Ela é usada para calcular a posição dos pontos na tela
    // Ela é inicializada com 0 e é incrementada a cada vez que um ponto é criado
    static private int contador = 0;

    // ShapeRenderer é usado para desenhar formas
    // como linhas e círculos
    // Ele é usado para desenhar os pontos na tela
    private ShapeRenderer shapeRenderer;
    // os floats eixoX, eixoY podem ser substituidos por
    // um Vector2
    // que é uma classe que representa um vetor 2D
    // e que tem métodos para calcular a distância entre dois pontos
    // e para calcular a posição dos pontos na tela
    private float eixoX;
    private float eixoY;
    // raio é o raio do círculo que representa o ponto
    private float raio;

    // Aqui estamos calculando a posição dos pontos na tela
    // O método calcularX calcula a posição do ponto no eixo X
    // O método calcularY calcula a posição do ponto no eixo Y
    // O método calcularRaio calcula o raio do círculo que representa o ponto
    // Tudo isso é feito com base na largura e altura da tela
    private float calcularX(float worldWidth) {
        float x;
        x = ((contador % 6) * ((worldWidth - 40) / 6)) + 40;
        return x;
    }
    private float calcularY(float worldHeight) {
        float y;
        y = ((contador / 6) * ((worldHeight - 40) / 6)) + 60;
        return y;
    }
    private float calcularRaio(float worldWidth, float worldHeight) {
        float raio;
        if (worldHeight > worldWidth) {
            raio = (worldWidth - 40) / 36;   
        } else if (worldHeight < worldWidth) {
            raio = (worldHeight - 40) / 36;    
        } else{
            raio = (worldWidth - 40) / 36;
        }
        return raio;
    }


    // Aqui estamos criando um objeto do tipo Pontos
    // que é a classe que representa os pontos
    // e que vai ser usado para desenhar os pontos na tela
    // O construtor da classe Pontos recebe como parâmetros
    // o ShapeRenderer, a largura e a altura da tela
    public Pontos(ShapeRenderer shapeRenderer, float worldWidth, float worldHeight) {
        this.shapeRenderer = shapeRenderer;
        this.eixoX = calcularX(worldWidth);
        this.eixoY = calcularY(worldHeight);
        this.raio = calcularRaio(worldWidth, worldHeight);
        // Aqui estamos inicializando o contador
        // O contador é inicializado com 0
        // e é incrementado a cada vez que um ponto é criado
        contador++;
    }

    // Aqui estamos criando os métodos getEixoX(), getEixoY() e getRaio()
    // que são usados para obter a posição dos pontos na tela e o raio do círculo
    // que representa o ponto
    public float getEixoX() {
        return eixoX;
    }

    public float getEixoY() {
        return eixoY;
    }

    //Aqui desenhamos os pontos na tela
    // O método desenharPontos() é usado para desenhar os pontos na tela
    // O método begin() inicia o desenho
    // O método setColor() define a cor do ponto
    // O método circle() desenha o círculo que representa o ponto
    // O método end() finaliza o desenho

    public void desenharPontos() {
        //Vale ressaltar que no begin() temos que colocar
        //ShapeRenderer.ShapeType. para definir o tipo de forma
        // que queremos desenhar
        // O ShapeType.Filled é usado para desenhar formas preenchidas
        // O ShapeType.Line é usado para desenhar formas com linhas
        // O ShapeType.Point é usado para desenhar pontos
        // Estou usando Filled ao invés de Point
        // porque o ponto é muito pequeno e não aparece na tela
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1); 
        shapeRenderer.circle(eixoX, eixoY, raio); 
        shapeRenderer.end();
    }

    // Aqui estamos criando o método dispose()
    // que é usado para liberar os recursos usados pelo ShapeRenderer
    public void dispose() {
        shapeRenderer.dispose();
    }

}
