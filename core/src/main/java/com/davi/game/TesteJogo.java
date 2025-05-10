package com.davi.game;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import javax.sound.sampled.Line;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;

public class TesteJogo extends ApplicationAdapter {
    
    private Array<Rectangle> retangulos;
    private OrthographicCamera camera;
    private Vector3 mouse_position;
    private ShapeRenderer shape;
    private Array<Circle> circulos;
    private Line line;

    @Override
    public void create() {
        /*retangulos = new Array<Rectangle>();
        for (int l = 0; l < 10; l++) {
            for (int i = 0; i < 50; i++) {
                retangulos.add(new Rectangle(l * 50, i * 50, 50, 50));
            }
        }*/

        circulos = new Array<Circle>();
        for (int l = 0; l < 8; l++) {
            for (int i = 0; i < 8; i++) {
                circulos.add(new Circle(l * 50+275, i * 50+100, 5));
            }
        }

        camera = new OrthographicCamera();
        mouse_position = new Vector3();

        shape = new ShapeRenderer();
    }

    @Override
    public void render() {
        ScreenUtils.clear(205 / 255f, 255 / 255f, 138 / 255f, 1);

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouse_position);

        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeType.Filled);

        for (Circle circulo : circulos) {
            if (circulo.contains(mouse_position.x, mouse_position.y)) {
                shape.setColor(Color.BLUE);
            } else {
                shape.setColor(Color.PINK);
            }
            shape.circle(circulo.x, circulo.y, circulo.radius);
        }

        for (Circle circulo : circulos) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (circulo.contains(mouse_position.x, mouse_position.y)) {
                    
                }
            }
        }

        shape.end();
    }

    @Override
    public void dispose() {
        // Dispose of resources if needed
        shape.dispose();
    }
}
