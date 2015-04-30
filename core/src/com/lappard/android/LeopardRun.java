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

public class LeopardRun extends ApplicationAdapter {
	SpriteBatch batch;
    Sprite spaceship;
    Socket socket;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        Texture img = new Texture("spaceship.png");
        spaceship = new Sprite(img, img.getWidth(), img.getHeight());
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                spaceship.setCenter(screenX, Gdx.graphics.getHeight() - screenY);
                return true;
            }
        });
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
