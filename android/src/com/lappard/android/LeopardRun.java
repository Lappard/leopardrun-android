package com.lappard.android;

import android.content.Context;
import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.codebutler.android_websockets.WebSocketClient;
import com.lappard.android.util.CustomTrustManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.net.ssl.TrustManager;

public class LeopardRun extends ApplicationAdapter {

    private static final String TAG_WS = "WebSocket";
    private static final String WEBSOCKET_URL = "wss://jonathanwiemers.de:1337";

    private final Context context;

    SpriteBatch batch;
    Sprite spaceship;
    WebSocketClient socket;

    public LeopardRun(Context context){
        this.context = context;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        Texture img = new Texture("spaceship.png");
        spaceship = new Sprite(img, img.getWidth(), img.getHeight());


        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                spaceship.setCenter(screenX, Gdx.graphics.getHeight() - screenY);
                socket.send("{\"x\":" + screenX + ", \"y\":" + screenY + "}");
                return true;
            }
        });

        //setup secure websocket
        InputStream certFile = null;
        try {
            certFile = context.getAssets().open("cert/ssl.crt");
        } catch (IOException e) {
            Log.e("LeopardRun", e.getMessage());
        }
        TrustManager[] customTrustManagers =
                CustomTrustManagerFactory.getInstance().getCustomTrustManagersFromCertificate(certFile);

        Log.d("lr", "ctms:"+customTrustManagers.length);
        WebSocketClient.setTrustManagers(customTrustManagers);
        socket = new WebSocketClient(URI.create(WEBSOCKET_URL),new WebSocketClient.Listener() {
            @Override
            public void onConnect() {
                Gdx.app.log(TAG_WS, "Connected to " + WEBSOCKET_URL);
            }

            @Override
            public void onMessage(String message) {
                Gdx.app.log(TAG_WS, "Message:" + message);
            }

            @Override
            public void onMessage(byte[] data) {
                Gdx.app.log(TAG_WS, "ByteMessage:" + data.toString());
            }

            @Override
            public void onDisconnect(int code, String reason) {
                Gdx.app.log(TAG_WS, "Disconnected from " + WEBSOCKET_URL + " with Code " + code
                        + ". Reason: " + reason);
            }

            @Override
            public void onError(Exception error) {
                Gdx.app.error(TAG_WS, "Exceptional Error:" + error.toString());
                error.printStackTrace();
            }
        }, null);

        socket.connect();

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
