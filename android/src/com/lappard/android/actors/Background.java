package com.lappard.android.actors;

import android.util.Log;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;

public class Background extends PhysicsActor {

    public static float WIDTH = 1280;
    public static float HEIGHT = 720;

    public Background(World world, float x, float y) {
        this.sprite = new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BACKGROUND));
        this.sprite.setSize(WIDTH, HEIGHT);
        initPhysicsAsBox(world, x, y, BodyDef.BodyType.KinematicBody, true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public void alignToPlayer(Player player) {
        sprite.setPosition(player.getPosition().x, sprite.getY());
    }
}
