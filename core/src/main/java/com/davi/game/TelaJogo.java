package com.davi.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TelaJogo implements Screen {

    private Texture backgroundTexture;
    private Music music;
    private FitViewport portTela;
    private SpriteBatch spriteBatch;

    private Main game;
    public TelaJogo(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        portTela = new FitViewport(800, 500);
        create();
    }

    private void create() {
        backgroundTexture = new Texture("backgroundjogo.jpg");
        music = Gdx.audio.newMusic(Gdx.files.internal("musicgame.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
    }

    @Override
    public void resize(int width, int height) {
        portTela.update(width, height, true);
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        portTela.apply();
        spriteBatch.setProjectionMatrix(portTela.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = portTela.getWorldWidth();
        float worldHeight = portTela.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        spriteBatch.end();
    }

    @Override
    public void render(float delta) {
        draw();
    }
    

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        music.dispose();
    }
}
