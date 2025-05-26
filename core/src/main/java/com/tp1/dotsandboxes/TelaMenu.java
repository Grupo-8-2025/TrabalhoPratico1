package com.tp1.dotsandboxes;

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

public class TelaMenu implements Screen {

    private Texture imgLogoAMI;
    private Texture imgCarregando;
    private FitViewport portTela;
    private SpriteBatch spriteBatch;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private boolean apertarEnter = false;
    private float tempo = 0;
    private DotsAndBoxes game;
    private String nomeJogador = "";
    private boolean digitandoNome = true;
    private int dificuldadeSelecionada = -1;
    private boolean escolhendoDificuldade = false;

    public TelaMenu(DotsAndBoxes game) {
        this.game = game;
    }

    private void create() {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 20;
        font = fontGenerator.generateFont(fontParameter);

        spriteBatch = new SpriteBatch();
        portTela = new FitViewport(800, 500);
        imgLogoAMI = new Texture("logo.png");
        imgCarregando = new Texture("winXPLoading.jpeg");
    }

    void desenharTelaInicial(BitmapFont font, SpriteBatch spriteBatch, Texture imgLogoAMI, float worldHeight) {
        spriteBatch.draw(imgLogoAMI, -15, worldHeight - 80, 174, 100);
        font.setColor(Color.WHITE);

        font.draw(spriteBatch, "AMIBIOS(C)2007 American Megatrends, Inc.", 0, worldHeight - 80);
        font.draw(spriteBatch, "ASUS P5KPL ACPI BIOS Revision 0603", 0, worldHeight - 100);
        font.draw(spriteBatch, "CPU : Intel(R) Pentium(R) Dual CPU E2180 @ 2.00GHz", 0, worldHeight - 120);
        font.draw(spriteBatch, " Speed : 2.51 GHz    Count : 2", 0, worldHeight - 140);
        font.draw(spriteBatch, " ", 0, worldHeight - 160);
        font.draw(spriteBatch, "APERTE ENTER PARA COMECAR", 0, worldHeight - 180);
        font.draw(spriteBatch, "***POR FAVOR NAO COLOQUE EM TELA CHEIA***", 0, worldHeight - 200);
        font.draw(spriteBatch, "APERTE ESC PARA ABRIR AS CONFIGURACOES", 0, worldHeight - 220);
        font.draw(spriteBatch, "DDR2-667 in Dual Channel Inter leaved Mode", 0, worldHeight - 240);
        font.draw(spriteBatch, "Initializing USB Controllers .. Done.", 0, worldHeight - 260);
        font.draw(spriteBatch, "3584MB OK", 0, worldHeight - 280);
        font.draw(spriteBatch, "(C) American Megatrends, Inc.", 0, 20);
        font.draw(spriteBatch, "64-0603-000001-00101111-022908-Bearlake-A0B20000-Y2KC", 0, 40);
        font.draw(spriteBatch, "Digite seu nome:", 0, worldHeight - 300);
        font.draw(spriteBatch, nomeJogador + (digitandoNome ? "|" : ""), 0, worldHeight - 320);
        if (!digitandoNome && escolhendoDificuldade) {
            font.draw(spriteBatch, "Digite 0 para Facil ou 1 para Dificil:", 0, worldHeight - 340);
        }
    }

    void verificadorTempoCarregando(float tempo, boolean apertarEnter, DotsAndBoxes game, SpriteBatch spriteBatch,
        Texture imgCarregando, float worldWidth, float worldHeight) {
        if (tempo > 2) {
            tempo = 0;
            apertarEnter = false;
            game.setNomeJogador(nomeJogador);
            game.setDificuldadeIA(dificuldadeSelecionada);
            game.setScreen(null);
        } else {
            spriteBatch.draw(imgCarregando, 0, 0, worldWidth, worldHeight);
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        portTela.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        spriteBatch.begin();

        float worldWidth = portTela.getWorldWidth();
        float worldHeight = portTela.getWorldHeight();

        if (apertarEnter) {
            tempo += Gdx.graphics.getDeltaTime();
            verificadorTempoCarregando(tempo, apertarEnter, game, spriteBatch, imgCarregando, worldWidth, worldHeight);
        } else {
            desenharTelaInicial(font, spriteBatch, imgLogoAMI, worldHeight);
        }

        spriteBatch.end();
    }

    @Override
    public void show() {
        create();
    }

    @Override
    public void render(float delta) {
        if (digitandoNome) {
            verificaDigitacaoNome();
        } else if (escolhendoDificuldade) {
            verificaDificuldade();
        }
        
        draw();
    }

    private void verificaDigitacaoNome() {
        if (digitandoNome) {
            for (int i = 0; i < 26; i++) {
                if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.A + i)) {
                    nomeJogador += (char) ('A' + i);
                }
            }
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.BACKSPACE) && nomeJogador.length() > 0) {
                nomeJogador = nomeJogador.substring(0, nomeJogador.length() - 1);
            }
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER) && nomeJogador.length() > 0) {
                digitandoNome = false;
                escolhendoDificuldade = true;
            }
        }
    }

    private void verificaDificuldade() {
        if (escolhendoDificuldade) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_0)) {
                dificuldadeSelecionada = 0;
                escolhendoDificuldade = false;
                apertarEnter = true;
            } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_1)) {
                dificuldadeSelecionada = 1;
                escolhendoDificuldade = false;
                apertarEnter = true;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        portTela.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
    
    @Override
    public void dispose() {
        spriteBatch.dispose();
        imgLogoAMI.dispose();
        fontGenerator.dispose();
    }

}