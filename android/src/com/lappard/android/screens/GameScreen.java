package com.lappard.android.screens;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.codebutler.android_websockets.WebSocketClient;
import com.lappard.android.entity.Entity;
import com.lappard.android.entity.Floor;
import com.lappard.android.entity.Player;
import com.lappard.android.interfaces.TouchListener;
import com.lappard.android.logic.ContactSolver;

import java.util.List;
import java.util.Vector;

public class GameScreen extends Screen implements TouchListener {

    private List<Entity> entities;

    private World world;
    private OrthographicCamera cam;
    private Player player;


    public GameScreen() {

    }

    @Override
    public void create() {
        cam = new OrthographicCamera(24, 13.5f);
        cam.position.set(24/2f, 13.5f/2f, 0);
        cam.update();

        world = new World(new Vector2(0, -9.81f), true);
        entities = new Vector<Entity>();

        world.setContactListener(new ContactSolver());

        player = new Player(5, 5, world);
        entities.add(player);
        entities.add(new Floor(Gdx.graphics.getWidth() /2f, 1, Gdx.graphics.getWidth(), 2, world));
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
        for(Entity e: entities){
            e.render(spriteBatch);
        }
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
