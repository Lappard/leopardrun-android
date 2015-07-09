package com.lappard.android.actors;

import android.util.Log;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;

public class Background extends PhysicsActor {

    public static float WIDTH = 1980;
    public static float HEIGHT = 1080;

    public Background(World world, float x, float y) {
        this.sprite = new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BACKGROUND));
        this.sprite.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.sprite.setSize(WIDTH, HEIGHT);
        initPhysicsAsBox(world, x, y, BodyDef.BodyType.KinematicBody, true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    public void alignToPlayer(Player player) {
        body.setTransform(player.getPosition().x - sprite.getWidth() / 2f, 0, 0);
        sprite.setRegion((int)(player.getPosition().x * 100), 0, (int)WIDTH, (int)HEIGHT);
    }
}

