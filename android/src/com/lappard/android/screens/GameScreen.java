package com.lappard.android.screens;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.codebutler.android_websockets.WebSocketClient;
import com.lappard.android.data.NetworkCommand;
import com.lappard.android.entity.Entity;
import com.lappard.android.entity.Floor;
import com.lappard.android.entity.Player;
import com.lappard.android.interfaces.TouchListener;
import com.lappard.android.logic.ContactSolver;
import com.lappard.android.logic.level.ServerLevelGenerator;
import com.lappard.android.manager.LevelManager;
import com.lappard.android.manager.NetworkManager;

import java.util.List;
import java.util.Vector;

public class GameScreen extends Screen implements TouchListener {

    public static float PIXEL_PER_METER = 50;

    private List<Entity> entities;
    private World world;
    private OrthographicCamera cam;
    private Player player;
    private LevelManager levelManager;


    public GameScreen() {

    }

    @Override
    public void create() {
        float height = Gdx.graphics.getHeight() / PIXEL_PER_METER,
              width = Gdx.graphics.getWidth() / PIXEL_PER_METER;
        cam = new OrthographicCamera(width, height);
        cam.position.set(width/2f, height/2f, 0);
        cam.update();

        world = new World(new Vector2(0, -9.81f), true);
        entities = new Vector<Entity>();

        world.setContactListener(new ContactSolver());

        levelManager = new LevelManager(world, new ServerLevelGenerator(world));

        player = new Player(1, 2, world);
        entities.add(player);
        entities.add(new Floor(Gdx.graphics.getWidth() /2f, 1, Gdx.graphics.getWidth(), 2, world));


        NetworkManager.getInstance().connect();
        NetworkManager.getInstance().on(NetworkManager.ACTION_CONNECTION_ESTABLISHED, new NetworkManager.EventListener() {


            @Override
            public void onEvent(NetworkCommand cmd) {
                levelManager.expandLevel();
            }
        });

    }

    @Override
    public void update() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        for(Entity e: entities){
            e.update();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        /*for(Entity e: entities){
            e.render(spriteBatch);
        }*/
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body b : bodies){
            ((Entity)b.getUserData()).render(spriteBatch);
        }
        spriteBatch.end();
    }


    @Override
    public void destroy() {

    }

    @Override
    public void touchUp() {
        Log.d("GameScreen", "jump!");
        player.jump();
    }
}
