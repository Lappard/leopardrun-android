package com.lappard.android.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lappard.android.actors.Floor;
import com.lappard.android.actors.Player;


public class GameScreen implements Screen {

    public static float PIXEL_PER_METER = 10;

    protected Game game;
    protected SpriteBatch batch;
    protected Stage stage;
    protected World world;

    public GameScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), false);
        stage = new Stage(new ExtendViewport(1280f / PIXEL_PER_METER, 720f / PIXEL_PER_METER));
        
        stage.addActor(new Player(world, 40, 70));
        stage.addActor(new Floor(world, 0, 0));
        stage.addActor(new Floor(world, 12.8f * 1, 0));
        stage.addActor(new Floor(world, 12.8f * 2, 0));
        stage.addActor(new Floor(world, 12.8f * 3, 0));
        stage.addActor(new Floor(world, 12.8f * 4, 0));
        stage.addActor(new Floor(world, 12.8f * 5, 0));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta, 4, 2);
        stage.act(delta);

        batch.begin();
        stage.draw();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
