package com.lappard.android.screens;

import android.util.Log;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lappard.android.LeopardRun;
import com.lappard.android.R;
import com.lappard.android.actors.Floor;
import com.lappard.android.actors.Player;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.level.Level;
import com.lappard.android.level.LevelCreator;
import com.lappard.android.level.NetworkLevelCreator;
import com.lappard.android.logic.ScoreManager;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.network.NetworkManager;
import com.lappard.android.screens.events.ScreenCreationEvent;
import com.lappard.android.util.ContactHandler;
import com.lappard.android.util.Event;
import com.squareup.otto.Subscribe;

import java.util.List;


public class GameScreen implements IScreen {

    public static final float PIXEL_PER_METER = 100;
    private boolean _isActive = false;
    protected Game game;
    protected SpriteBatch batch;
    protected Stage stage;
    protected NetworkManager network;
    protected World world;
    protected Box2DDebugRenderer debugRenderer;
    protected LevelCreator levelCreator;

    private Player player;
    private Sprite background;


    public GameScreen() {
        Event.getBus().register(this);

        batch = new SpriteBatch();
        if (LeopardRun.DEBUG_MODE)                //bods, joints, AABBs, inact, velo, contact
            debugRenderer = new Box2DDebugRenderer(true, false, false, false, true, true);
        network = new NetworkManager();
        network.connect();

    }

    @Override
    public void show() {
        Event.getBus().post(new ScreenCreationEvent(new UiScreen()));
        world = new World(new Vector2(0, -9.81f * 2), false);
        world.setContactListener(new ContactHandler());
        stage = new Stage(new ExtendViewport(1280f / PIXEL_PER_METER, 720f / PIXEL_PER_METER));

        Gdx.input.setInputProcessor(stage);
        levelCreator = new NetworkLevelCreator(network, world);
        background = new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BACKGROUND));
        Image bgactor = new Image(background);
        bgactor.setFillParent(true);
        stage.addActor(bgactor);



        player = new Player(world, 4, 12);


        stage.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.jump();
                return true;
            }
        });

        stage.addActor(player);
        stage.addActor(new Floor(world, 4, 4));
        stage.addActor(new Floor(world, 6, 3));
        stage.addActor(new Floor(world, 8, 2));
        stage.addActor(new Floor(world, 10, 1));

    }

    @Subscribe
    public void onConnectionEstablished(NetworkManager.ConnectionEstablishedEvent e){
        levelCreator.requestLevelData();
    }

    @Override
    public void render(float delta) {
        if(_isActive){
            world.step(delta, 4, 2);
            stage.act(delta);
            //camera smoothly follows player
            stage.getCamera().position.x += (player.getPosition().x - stage.getCamera().position.x) / 10f;
            ScoreManager.getInstance().update();
        }
        batch.begin();
        stage.draw();
        if (LeopardRun.DEBUG_MODE)
            debugRenderer.render(world, stage.getViewport().getCamera().combined);
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

    @Subscribe
    public void extendLevel(Level level){
        for (Actor actor : level.actors) {
            stage.addActor(actor);
        }
    }

    @Override
    public void setActive() {
        _isActive = true;
        ScoreManager.getInstance().startGame();
    }
}
