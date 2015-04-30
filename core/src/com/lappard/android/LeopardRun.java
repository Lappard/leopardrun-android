package com.lappard.android;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.Socket;

import java.io.IOException;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import sun.rmi.runtime.Log;

public class LeopardRun extends ApplicationAdapter {

    private static final String TAG_WS = "WebSocket";
    private static final String WEBSOCKET_URL = "ws://jonathanwiemers.de:1337";

	SpriteBatch batch;
    Sprite spaceship;
    WebSocketConnection socket = new WebSocketConnection();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        Texture img = new Texture("spaceship.png");
        spaceship = new Sprite(img, img.getWidth(), img.getHeight());
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                spaceship.setCenter(screenX, Gdx.graphics.getHeight() - screenY);
                return true;
            }
        });
        try {
            socket.connect(WEBSOCKET_URL, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Gdx.app.log(TAG_WS, "Status: Connected to " + WEBSOCKET_URL);
                    socket.sendTextMessage("Hello, world!");
                }

                @Override
                public void onTextMessage(String payload) {
                    Gdx.app.log(TAG_WS, "Got echo: " + payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    Gdx.app.log(TAG_WS, "Connection lost.");
                }
            });
        } catch (WebSocketException e) {

            Gdx.app.log(TAG_WS, e.toString());
        }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        spaceship.draw(batch);
		batch.end();
	}
}
