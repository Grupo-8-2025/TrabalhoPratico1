package com.tp1.dotsandboxes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */

public class DotsAndBoxes extends Game {

    private Pontos bolinhas;
    private Linhas linhas;
    private Quadrados quadrados;
    private Jogador jogador1;
    private OrthographicCamera camera;
    private Computador computador;
    private boolean turnoJogador = true;
    private Texture backgroundJogo;
    private BitmapFont font;
    private SpriteBatch batch;
    private Music musicGame;
    private Sound erroSound;

    @Override
    public void create() {
        setScreen(new TelaMenu(this));
        criaObjetosJogo();
        criaTextos();
        criaMusica();
    }

    private void criaObjetosJogo(){
        backgroundJogo = new Texture(Gdx.files.internal("assets/backgroundGame.png"));
        bolinhas = new Pontos(6f, 8f, 50f, Color.RED); // numBolinhas
        jogador1 = new Jogador("Jogador 1", Color.BLUE);
        linhas = new Linhas(6f, 8f, 50f, jogador1.getCor());
        quadrados = new Quadrados(6f, 9f, 50f, jogador1.getCor());
        computador = new Computador(Color.YELLOW, 1);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
    }

    private void criaTextos() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/GAMERIA.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 35;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
    }

    private void criaMusica() {
        musicGame = Gdx.audio.newMusic(Gdx.files.internal("assets/musicgame.mp3"));
        musicGame.setLooping(true);
        musicGame.setVolume(0.5f);
        musicGame.play();

        erroSound = Gdx.audio.newSound(Gdx.files.internal("assets/erro.mp3"));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255f, 255f, 255f, 1);

        verificaTurno();
        desenhaTexto();
        desenhaItens();

        verificaFinal();
        super.render();
    }

    private void desenhaItens(){
        linhas.desenhaLinhas(camera, jogador1);
        quadrados.verificaQuadrados(linhas.getLinhasHorizontais(), linhas.getLinhasVerticais(), this);
        quadrados.desenhaQuadrados(camera, font, batch);
        bolinhas.desenhaBolinhas(camera, jogador1);
    }

    private void desenhaTexto(){
        batch.begin();
        batch.draw(backgroundJogo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.setColor(jogador1.getCor());
        font.draw(batch, jogador1.getNome() + " " + jogador1.getPontos(), 20,
                Gdx.graphics.getHeight() - 20);
        font.setColor(computador.getCor());
        font.draw(batch, "Computador: " + computador.getPontos(), 20, Gdx.graphics.getHeight() - 50);
        batch.end();
        jogador1.update();
    }

    private void verificaTurno() {
        if (turnoJogador) {
            boolean jogou = jogador1.realizaJogada(linhas.getLinhasHorizontais(), linhas.getLinhasVerticais());
            int fechados = quadrados.verificaQuadrados(linhas.getLinhasHorizontais(), linhas.getLinhasVerticais(),
                    this);
            if (jogou && fechados == 0) {
                turnoJogador = false;
            }
        } else {
            computador.jogar(linhas.getLinhasHorizontais(), linhas.getLinhasVerticais());
            int fechados = quadrados.verificaQuadrados(linhas.getLinhasHorizontais(), linhas.getLinhasVerticais(),
                    this);
            if (fechados == 0) {
                turnoJogador = true;
            }
        }
    }

    public void verificaFinal() {
        if (jogador1.getPontos() + computador.getPontos() == (int) (Math.pow(bolinhas.getNumBolinhas() - 1, 2))) {
            if (jogador1.getPontos() > computador.getPontos()) {
                setScreen(new TelaReinicio(this, true));
            } else {
                setScreen(new TelaReinicio(this, false));
            }
        }
    }

    public Jogador getJogadorPorCor(Color cor) {
        if (jogador1.getCor().equals(cor))
            return jogador1;
        if (computador.getCor().equals(cor))
            return computador;
        return null;
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    public void reiniciarJogo() {
        bolinhas = new Pontos(bolinhas.getNumBolinhas(), 8f, 50f, Color.RED);
        jogador1 = new Jogador(jogador1.getNome(), jogador1.getCor());
        linhas = new Linhas(bolinhas.getNumBolinhas(), 8f, 50f, jogador1.getCor());
        quadrados = new Quadrados(bolinhas.getNumBolinhas(), 9f, 50f, jogador1.getCor());
        computador = new Computador(computador.getCor(), computador.getDificuldade());
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/GAMERIA.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 35;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch = new SpriteBatch();
    }

    public void setNomeJogador(String nome) {
        if (jogador1 != null) {
            jogador1.setNome(nome);
        }
    }

    public void setDificuldadeIA(int dificuldade) {
        if (computador != null) {
            computador.setDificuldade(dificuldade);
        }
    }

    public void tocarErro() {
        if (erroSound != null)
            erroSound.play();
    }

    public Sound getErroSound() {
        return erroSound;
    }

    @Override
    public void dispose() {
        if (bolinhas != null) {
            bolinhas.dispose();
        }
        if (font != null) {
            font.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
        if (linhas != null) {
            linhas.dispose();
        }
        if (quadrados != null) {
            quadrados.dispose();
        }
        super.dispose();
    }

}