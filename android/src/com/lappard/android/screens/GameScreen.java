package com.lappard.android.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lappard.android.LeopardRun;
import com.lappard.android.actors.Floor;
import com.lappard.android.actors.Player;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.level.LevelCreator;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.network.NetworkManager;
import com.lappard.android.util.ContactHandler;

import java.util.List;


public class GameScreen implements Screen {

    public static final float PIXEL_PER_METER = 100;

    protected Game game;
    protected SpriteBatch batch;
    protected Stage stage;
    protected NetworkManager network;
    protected World world;
    protected Box2DDebugRenderer debugRenderer;
    protected LevelCreator level;

    private Player player;
    private Sprite background;


    public GameScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        if (LeopardRun.DEBUG_MODE)                //bods, joints, AABBs, inact, velo, contact
            debugRenderer = new Box2DDebugRenderer(true, false, false, false, true, true);
        network = new NetworkManager();
        network.connect();

    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f * 2), false);
        world.setContactListener(new ContactHandler());
        stage = new Stage(new ExtendViewport(1280f / PIXEL_PER_METER, 720f / PIXEL_PER_METER));
        Gdx.input.setInputProcessor(stage);
        level = new LevelCreator(network, world);

        background = new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BACKGROUND));
        //background.setSize(1280f / PIXEL_PER_METER, 720f / PIXEL_PER_METER);

        player = new Player(world, 4, 12);


        stage.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.jump();
                return true;
            }
        });

        stage.addActor(player);
        stage.addActor(new Floor(world, 4, 4));

        if(network.isConnected()){
            extendLevel();
        }else{
            network.on(NetworkManager.ACTION_CONNECTION_ESTABLISHED, new NetworkManager.EventListener() {
                @Override
                public void onEvent(NetworkCommand cmd) {
                    network.off(NetworkManager.ACTION_CONNECTION_ESTABLISHED, this);
                    extendLevel();
                }
            });
        }


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta, 4, 2);
        stage.act(delta);
        stage.getCamera().position.x += 0.045f;

        batch.begin();
        background.draw(batch);
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

    private void extendLevel(){
        level.queryLevelPart(new LevelCreator.PartAvailableListener() {
            @Override
            public void onPartAvailable(List<Actor> part) {
                for (Actor actor : part) {
                    stage.addActor(actor);
                }
            }
        });
    }
}
