package com.davi.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TelaMenu implements Screen{
    
    private Texture imgLogoAMI;
    private Texture imgCarregando;
    private FitViewport portTela;
    private SpriteBatch spriteBatch;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private boolean apertouDEL = false;
    private float tempo = 0;

    private Main game;
    public TelaMenu(Main game) {
        this.game = game;
    }

    private void create() {

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 12;
        font = fontGenerator.generateFont(fontParameter); 

        spriteBatch = new SpriteBatch();
        portTela = new FitViewport(800, 500);
        imgLogoAMI = new Texture("logo.png");
        imgCarregando = new Texture("winXPLoading.jpeg");
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        portTela.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();

        float worldWidth = portTela.getWorldWidth();
        float worldHeight = portTela.getWorldHeight();

        if (apertouDEL) {
            tempo += Gdx.graphics.getDeltaTime();
            if (tempo > 2) {
                tempo = 0;
                apertouDEL = false;
                game.setScreen(new TelaJogo(game));
            } else {
                spriteBatch.draw(imgCarregando, 0, 0, worldWidth, worldHeight);
            }        

        } else {

            spriteBatch.draw(imgLogoAMI, -15, worldHeight - 80, 174, 100);

            font.setColor(Color.WHITE);
            font.draw(spriteBatch, "AMIBIOS(C)2007 American Megatrends, Inc.", 0, worldHeight - 80);
            font.draw(spriteBatch, "ASUS P5KPL ACPI BIOS Revision 0603", 0, worldHeight - 100);
            font.draw(spriteBatch, "CPU : Intel(R) Pentium(R) Dual CPU E2180 @ 2.00GHz", 0, worldHeight - 120);
            font.draw(spriteBatch, " Speed : 2.51 GHz    Count : 2", 0, worldHeight - 140);
            font.draw(spriteBatch, " ", 0, worldHeight - 160);
            font.draw(spriteBatch, "Press DEL to run Setup", 0, worldHeight - 180);
            font.draw(spriteBatch, "Press F8 for BBS POPUP", 0, worldHeight - 200);
            font.draw(spriteBatch, "DDR2-667 in Dual Channel Inter leaved Mode", 0, worldHeight - 220);
            font.draw(spriteBatch, "Initializing USB Controllers .. Done.", 0, worldHeight - 240);
            font.draw(spriteBatch, "3584MB OK", 0, worldHeight - 260);
            font.draw(spriteBatch, "(C) American Megatrends, Inc.", 0, 20);
            font.draw(spriteBatch, "64-0603-000001-00101111-022908-Bearlake-A0B20000-Y2KC", 0, 40);
        }


        spriteBatch.end();
    }

    @Override
    public void show() {
        create();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            apertouDEL = true;
        }
        draw();
    }

    @Override
    public void resize(int width, int height) {
        portTela.update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        imgLogoAMI.dispose();
        fontGenerator.dispose();
    }

}
