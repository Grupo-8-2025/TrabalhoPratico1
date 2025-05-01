package com.davi.game;

import com.badlogic.gdx.Screen;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 * !!!MUITO IMPORTANTE!!!
 * 
 * VEJA QUE A Screen IMPLEMENTA A INTERFACE SCREEN
 * ELA É O QUE VAI SER RENDERIZADO NA TELA
 */
public class TelaJogo implements Screen {

    // Aqui estamos declarando as variáveis
    // Textura é usada para carregar imagens
    private Texture backgroundTexture;
    // Música é usada para carregar músicas
    private Music music;
    // FitViewport é usado para definir a área de visualização
    private FitViewport portTela;
    // SpriteBatch é usado para desenhar imagens na tela
    private SpriteBatch spriteBatch;
    // ShapeRenderer é usado para desenhar formas
    // como linhas e círculos
    private ShapeRenderer shapeRenderer;
    // Pontos é um array que armazena os pontos
    // que serão desenhados na tela
    private Pontos [] pontos = new Pontos[36];
    // Linhas é um vetor que armazena as linhas
    // que serão desenhadas na tela
    private Vector<Linhas> linhas = new Vector<Linhas>();

    // Aqui estamos criando um objeto do tipo Main
    // que é a classe principal do jogo
    // e que vai ser usado para chamar as telas
    // e gerenciar o jogo
    // O Main é o ponto de entrada do jogo
    // e é onde o jogo começa a ser executado
    private Main game;
    public TelaJogo(Main game) {
        this.game = game;
    }

    /*
     * !!!PARTE IMPORTANTE DO CODIGO!!!
     * Aqui estamos implementando a interface Screen
     * TUDO que estiver dentro dessa INTERFACE
     * deve ser implementado aqui COM O @OVERRIDE
     * O @Override é uma anotação que indica que o método está sobrescrevendo um método da superclasse
     * Aqui estamos implementando os métodos da interface Screen
     */

    // Aqui estamos implementando o método show()
    // O show() é chamado quando a tela é exibida pela primeira vez
    // Aqui estamos inicializando o spriteBatch
    // e o FitViewport
    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        portTela = new FitViewport(800, 500);
        create();
    }

    // Aqui estamos implementando o método create()
    // O create() é chamado quando a tela é criada
    private void create() {
        //Aqui estamos iniciando o backgroundTexture
        backgroundTexture = new Texture("backgroundjogo.jpg");
        //Aqui estamos iniciando a música
        // A música é carregada a partir do arquivo musicgame.mp3
        // Gdx.files.internal() é usado para carregar arquivos internos
        // Gdx.audio.newMusic() é usado para criar um objeto de música
        // O arquivo deve estar na pasta assets
        // A música é definida como em loop
        // e o volume é definido como 0.5
        // Em play() a música é iniciada
        music = Gdx.audio.newMusic(Gdx.files.internal("musicgame.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();

        // Aqui estamos iniciando o ShapeRenderer
        // O ShapeRenderer é usado para desenhar formas
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        float worldWidth = portTela.getWorldWidth();
        float worldHeight = portTela.getWorldHeight();

        // Aqui estamos criando os pontos
        for (int i = 0; i < pontos.length; i++) {
            pontos[i] = new Pontos(shapeRenderer, worldWidth, worldHeight); 
        }

        // Aqui estamos criando as linhas
        // As linhas são criadas a partir dos pontos
        // As linhas são desenhadas entre os pontos
        Linhas linha;
        for (int i = 0; i < pontos.length; i++) {
            if ((i+1) % 6 != 0) {
                linha = new Linhas(shapeRenderer, pontos[i].getEixoX(), pontos[i].getEixoY(), pontos[i+1].getEixoX(), pontos[i+1].getEixoY());
                //.add é padrao do java para adicionar um elemento a um vetor
                linhas.add(linha);
                
            } else {

            }
            if (i+6 < pontos.length) {
                linha = new Linhas(shapeRenderer, pontos[i].getEixoX(), pontos[i].getEixoY(), pontos[i+6].getEixoX(), pontos[i+6].getEixoY());
                linhas.add(linha);
            } else {

            }
        }
    }

    // Aqui estamos implementando o método resize()
    // O resize() é chamado quando a tela é redimensionada
    // Aqui estamos atualizando o FitViewport
    // O FitViewport é usado para definir a área de visualização
    // O FitViewport é usado para manter a proporção da tela
    // O FitViewport é usado para ajustar a tela
    @Override
    public void resize(int width, int height) {
        portTela.update(width, height, true);
    }

    // Aqui estamos implementando o método draw()
    // O draw() é chamado quando a tela é desenhada
    private void draw() {
        /*
         * Aqui estamos fazendo o procendimento padrao de limpesa da tela
         * O ScreenUtils.clear(Color.BLACK) limpa a tela com a cor preta
         * O portTela.apply() aplica a viewport na tela
         * O spriteBatch.setProjectionMatrix(portTela.getCamera().combined)
         * define a matriz de projeção da câmera
         * O spriteBatch.begin() inicia o desenho na tela
         * O spriteBatch.end() finaliza o desenho na tela
         * O porTela.getWorldWidth() e o portTela.getWorldHeight() 
         * pegam a largura e altura da tela segundo o ajuste do usuario
         */
        ScreenUtils.clear(Color.BLACK);
        portTela.apply();
        spriteBatch.setProjectionMatrix(portTela.getCamera().combined);
        spriteBatch.begin();

        // o getWorldWidth() e o getWorldHeight() pegam a largura e altura da tela
        float worldWidth = portTela.getWorldWidth();
        float worldHeight = portTela.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        spriteBatch.end();
        // Aqui estamos desenhando as linhas e depois os pontos
        for (Linhas linha : linhas) {
            linha.desenharLinhas();
        }
        for (Pontos ponto : pontos) {
            ponto.desenharPontos();
        }
    }

    // Aqui estamos implementando o método render()
    // O render() é chamado quando a tela é renderizada
    // O render() é chamado a cada quadro
    // Aqui estamos chamando o draw()
    // O draw() é o método que desenha a tela
    @Override
    public void render(float delta) {
        draw();
    }
    
    // Aqui estamos implementando o método pause()
    // O pause() é chamado quando a tela é pausada
    @Override
    public void pause() {
        
    }

    // Aqui estamos implementando o método resume()
    // O resume() é chamado quando a tela é retomada
    @Override
    public void resume() {
        
    }

    // Aqui estamos implementando o método hide()
    // O hide() é chamado quando a tela é ocultada
    @Override
    public void hide() {
        
    }

    // Aqui estamos implementando o método dispose()
    // O dispose() é chamado quando a tela é descartada
    // Aqui estamos fazendo o procedimento padrão de descarte
    /*
     * !!!MUITO IMPORTANTE!!!
     * 
     * O dispose() é usado para liberar os recursos usados pela tela
     * ENTAO NUNCA ESQUECA DE CHAMAR O dispose() NO FINAL DO JOGO
     */
    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        music.dispose();
        for (Pontos ponto : pontos) {
            if (ponto != null) ponto.dispose();
        }
    }
}
