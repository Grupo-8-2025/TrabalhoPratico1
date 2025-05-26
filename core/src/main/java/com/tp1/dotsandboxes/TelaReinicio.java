package com.tp1.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TelaReinicio implements Screen {

    private DotsAndBoxes game;
    private BitmapFont font;
    private SpriteBatch batch;
    private boolean venceu;
    private Botao botaoReiniciar;
    private Botao botaoVoltarInicio;

    public TelaReinicio(DotsAndBoxes game, boolean venceu) {
        this.game = game;
        this.venceu = venceu;
    }

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/GAMERIA.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 60;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        batch = new SpriteBatch();
        botaoReiniciar = new Botao(150, 80, 200, 50, "Reiniciar Jogo");
        botaoVoltarInicio = new Botao(450, 80, 200, 50, "Voltar ao Menu");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(64f/255f, 222f/255f, 180f/255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Clique para reiniciar", 100, 200);
        botaoReiniciar.desenhaBotao();
        botaoVoltarInicio.desenhaBotao();

        if(botaoReiniciar.identificaClick(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            if (Gdx.input.justTouched()) {
                game.reiniciarJogo();
                game.setScreen(null);
            }
        }
        if(botaoVoltarInicio.identificaClick(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            if (Gdx.input.justTouched()) {
                game.reiniciarJogo();
                game.setScreen(new TelaMenu(game));
            }
        }

        if(venceu) {
            font.setColor(Color.YELLOW);
            font.draw(batch, "Parabéns, Você venceu!", 40, 400);
        } else {
            font.setColor(Color.RED);
            font.draw(batch, "Você perdeu!", 220, 400);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
}