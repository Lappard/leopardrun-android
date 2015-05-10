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
import com.lappard.android.entity.Screen;
import com.lappard.android.interfaces.TouchListener;
import com.lappard.android.manager.GameManager;
import com.lappard.android.util.CustomTrustManagerFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.net.ssl.TrustManager;

public class LeopardRun extends ApplicationAdapter {
    private final Context context;

    private GameManager gameManager = GameManager.getInstance();

    public LeopardRun(Context context){
        this.context = context;
    }

    public void create () {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                GameManager.getInstance().touchUp();
                return true;
            }
        });

        Screen s = gameManager.loadLevel();
        gameManager.registerTouchListener((TouchListener)s);
    }

    public void render () {
        gameManager.renderLevel();
    }



}
