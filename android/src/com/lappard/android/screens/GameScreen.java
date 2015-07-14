package com.lappard.android.screens;

import android.provider.MediaStore;

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
import com.lappard.android.actors.Background;
import com.lappard.android.actors.FireWall;
import com.lappard.android.actors.Ghost;
import com.lappard.android.actors.PhysicsActor;
import com.lappard.android.actors.Player;
import com.lappard.android.audio.AudioManager;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.level.Level;
import com.lappard.android.level.LevelCreator;
import com.lappard.android.level.NetworkLevelCreator;
import com.lappard.android.logic.ScoreManager;
import com.lappard.android.screens.events.ScreenCreationEvent;
import com.lappard.android.screens.events.ScreenRemoveEvent;
import com.lappard.android.util.ContactHandler;
import com.lappard.android.util.Event;
import com.squareup.otto.Subscribe;


public class GameScreen implements IScreen {
    public static final float PIXEL_PER_METER = 100;
    private boolean _isActive = false;
    private boolean receivedFirstLevelPart = false;

    protected Game game;
    protected UiScreen ui;
    protected SpriteBatch batch;
    protected Stage stage;
    protected World world;
    protected Box2DDebugRenderer debugRenderer;
    protected LevelCreator levelCreator;

    private Player player;
    private Ghost ghost;
    private PhysicsActor fireWall;
    private Background background;
    private PhysicsActor lastActor;


    public GameScreen() {
        Event.getBus().register(this);

        batch = new SpriteBatch();
        if (LeopardRun.DEBUG_MODE)                //bods, joints, AABBs, inact, velo, contact
            debugRenderer = new Box2DDebugRenderer(true, false, false, false, true, true);

        world = new World(new Vector2(0, -9.81f * 2), false);
        world.setContactListener(new ContactHandler());


    }

    public void setLevelCreator(LevelCreator creator){
        creator.setWorld(world);
        this.levelCreator = creator;
    }

    public void createGhost(long[] jumpTimes){
        ghost = new Ghost(world, 4, 12, jumpTimes);
    }

    @Override
    public void show() {
        ui = new UiScreen();
        Event.getBus().post(new ScreenCreationEvent(ui));

        stage = new Stage(new ExtendViewport(1280f / PIXEL_PER_METER, 720f / PIXEL_PER_METER));


        Gdx.input.setInputProcessor(stage);
        background = new Background(world, 0, 0);
        stage.addActor(background);


        player = new Player(world, 4, 12);
        stage.addActor(player);

        if(ghost != null){
            stage.addActor(ghost);
        }

        fireWall = new FireWall(world, -3, -0.5f);
        stage.addActor(fireWall);


        stage.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.jump();
                return true;
            }
        });


        if(levelCreator != null){
            levelCreator.requestLevelData();
        }


    }

    @Override
    public void render(float delta) {
        if (_isActive && receivedFirstLevelPart) {
            world.step(delta, 4, 2);
            stage.act(delta);
            //camera smoothly follows player
            stage.getCamera().position.x += (player.getPosition().x - stage.getCamera().position.x) / 10f;
            background.alignToPlayer(player);
            ScoreManager.getInstance().update();

            if (levelNeedsToBeExtended()) {
                lastActor = null;
                levelCreator.requestLevelData();
            }
        }
        batch.begin();
        stage.draw();
        if (false && LeopardRun.DEBUG_MODE)
            debugRenderer.render(world, stage.getViewport().getCamera().combined);
        batch.end();

    }

    /**
     * Check whether level needs to be extended
     * this is the case if the distance between last actor added (should be
     * actor which is furthest right) and the camera is smaller then the screen width
     *
     * @return
     */
    private boolean levelNeedsToBeExtended() {
        if (lastActor != null) {
            float distance = lastActor.getPosition().x - stage.getCamera().position.x;
            if (distance < stage.getViewport().getWorldWidth()) {
                return true;
            }
        }
        return false;
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
    public void extendLevel(Level level) {
        receivedFirstLevelPart = true;
        for (Actor actor : level.actors) {
            stage.addActor(actor);
        }
        lastActor = (PhysicsActor) level.actors.get(level.actors.size() - 1);

        //since setting Z-Index doesn't have an effect (is overriden by stages), remove
        //firewall and re-add it to stage so it's on top of everything else
        stage.getActors().removeValue(fireWall, true);
        stage.addActor(fireWall);

    }

    @Override
    public void setActive() {
        _isActive = true;
        ScoreManager.getInstance().startGame();

        AudioManager.getInstance().playSound(AssetManager.SOUND_THEME,true);
    }

    @Subscribe
    public void onGameOver(Player.IsDeadEvent event){
        AudioManager.getInstance().pauseAll();
        _isActive = false;
        Event.getBus().unregister(this);
        ScoreManager.getInstance().endGame(event.jumps, levelCreator.getRawData());
        Event.getBus().post(new ScreenRemoveEvent(ui));
        Event.getBus().post(new ScreenRemoveEvent(this));
        Event.getBus().post(new ScreenCreationEvent(new GameOver()));
    }
}
