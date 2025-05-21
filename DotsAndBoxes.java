package com.tp1.dotsandboxes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class DotsAndBoxes extends Game {
    private Bolinhas bolinhas;
    private Linhas linhas;
    private Quadrados quadrados;
    private Jogador jogador1;
    private OrthographicCamera camera;
//private BitmapFont font;
//private SpriteBatch batch;

    @Override
    public void create() {

        bolinhas = new Bolinhas(6f, 8f, 50f, Color.RED);
        linhas = new Linhas(6f, 8f, 50f, Color.RED);
        jogador1 = new Jogador("Jogador 1", Color.GREEN);
        quadrados = new Quadrados(6f, 8f, 50f, jogador1.getCor());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

       // font = new BitmapFont();
///batch = new SpriteBatch();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255f, 255f, 255f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        /*batch.begin();
        font.setColor(Color.GREEN);
        font.getData().setScale(2f);
        font.draw(batch, "Jogador: " + jogador1.getPontos(), 20, Gdx.graphics.getHeight() - 20);
        font.setColor(Color.ORANGE);
        font.draw(batch, "IA: " + linhas.getPontosIA(), 20, Gdx.graphics.getHeight() - 50);
        batch.end();*/
        
        jogador1.update();

        boolean jogadorFezJogada = jogador1.realizaJogada(linhas.getLinhasHorizontais(), linhas.getLinhasVerticais());

        linhas.desenhaLinhas(camera, jogador1);
        quadrados.verificaQuadrados(linhas.getLinhasHorizontais(), linhas.getLinhasVerticais());
        quadrados.desenhaQuadrados(camera);
        bolinhas.desenhaBolinhas(camera, jogador1);

        if (jogadorFezJogada) {
            linhas.jogarIA();
        }
        super.render();

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void dispose() {
        if (bolinhas != null) {
            bolinhas.dispose();
        }
        /*if (font != null)
            font.dispose();
        if (batch != null)
            batch.dispose();*/
        super.dispose();
    }

}