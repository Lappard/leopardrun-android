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

    SpriteBatch batch;
    Sprite sprite;
    Texture img;
    World world;
    Body body;

    Body ground;

    public GameScreen() {

        entities = new Vector<Entity>();

        batch = new SpriteBatch();
        img = new Texture("spaceship.png");
        sprite = new Sprite(img);

        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2);

        world = new World(new Vector2(0, -98f), true);

        entities.add(new Player(200, 500, world));
        entities.add(new Floor(0,0,Gdx.graphics.getWidth(), 20, world));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void render () {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.end();

        for(Entity e: entities){
            e.update();
        }

        for(Entity e: entities){
            e.render();
        }
    }

    @Override
    public void touchUp() {
        this.body.setLinearVelocity(0,500);
    }
}
