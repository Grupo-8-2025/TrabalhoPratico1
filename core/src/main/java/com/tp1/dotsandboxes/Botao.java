package com.tp1.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Botao {

    private ShapeRenderer shape, margem;
    private float posX, posY, largura, altura;
    private String texto;
    private BitmapFont font;
    private SpriteBatch batch;

    Botao(float x, float y, float largura, float altura, String texto) {
        shape = new ShapeRenderer();
        margem = new ShapeRenderer();
        batch = new SpriteBatch();


        this.posX = x;
        this.posY = y;
        this.largura = largura;
        this.altura = altura;
        this.texto = texto;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 25;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
    }

    public void desenhaBotao() {
        margem.begin(ShapeRenderer.ShapeType.Filled);
        margem.setColor(Color.BLACK);
        margem.rect(posX - 5, posY - 5, largura+10, altura+10);
        margem.end();
        
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BLUE );
        shape.rect(posX, posY, largura, altura);
        shape.end();

        batch.begin();
        font.draw(batch, texto, posX + 13, posY + 35);
        batch.end();
    }

    public boolean identificaClick(float mouseX, float mouseY) {
        return (mouseX >= posX && mouseX <= posX + largura && mouseY >= posY && mouseY <= posY + altura);
    }

}
