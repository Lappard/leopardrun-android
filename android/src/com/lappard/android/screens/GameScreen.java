package com.lappard.android.screens;

import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.codebutler.android_websockets.WebSocketClient;
import com.lappard.android.entity.Entity;
import com.lappard.android.entity.Floor;
import com.lappard.android.entity.Player;
import com.lappard.android.entity.Screen;
import com.lappard.android.interfaces.TouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;
import java.util.Vector;

public class GameScreen extends Screen implements TouchListener {
    private static final String TAG_WS = "WebSocket";
    private static final String WEBSOCKET_URL = "ws://jonathanwiemers.de:1337";

    private List<Entity> entities;
    WebSocketClient socket;
    String guid;

    World world;

    Body ground;

    public GameScreen() {

        world = new World(new Vector2(0, -98f), true);
        entities = new Vector<Entity>();

        entities.add(new Player(200, 500, world));
        entities.add(new Floor(Gdx.graphics.getWidth() /2f, 30, Gdx.graphics.getWidth(), 60, world));
    }

    public void render () {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        for(Entity e: entities){
            e.update();
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for(Entity e: entities){
            e.render();
        }
    }

    @Override
    public void touchUp() {
    }
}
