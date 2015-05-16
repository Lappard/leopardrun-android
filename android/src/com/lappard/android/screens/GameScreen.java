package com.lappard.android.screens;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.codebutler.android_websockets.WebSocketClient;
import com.lappard.android.entity.Entity;
import com.lappard.android.entity.Floor;
import com.lappard.android.entity.Player;
import com.lappard.android.interfaces.TouchListener;

import java.util.List;
import java.util.Vector;

public class GameScreen extends Screen implements TouchListener {
    private static final String TAG_WS = "WebSocket";
    private static final String WEBSOCKET_URL = "ws://jonathanwiemers.de:1337";

    private List<Entity> entities;
    WebSocketClient socket;
    String guid;

    World world;
    Player player;

    public GameScreen() {

    }

    @Override
    public void create() {
        world = new World(new Vector2(0, -9.81f), true);
        entities = new Vector<Entity>();

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
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        for(Entity e: entities){
            e.render(spriteBatch, shapeRenderer);
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
