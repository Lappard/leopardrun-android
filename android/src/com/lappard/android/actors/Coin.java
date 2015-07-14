package com.lappard.android.actors;

import android.util.Log;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.logic.ScoreManager;

public class Coin extends PhysicsActor {
    public static float SIZE = 70;

    public Coin(World world, float x, float y) {
        AnimatedSprite sprite = new AnimatedSprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_COIN),6,1,0.1f);
        this.sprite = sprite;
        this.sprite.setSize(SIZE,SIZE);
        this.collisionCategory = Obstacle.COLLISION_CATEGORY;
        this.collisionMask = Player.COLLISION_CATEGORY;
        super.initPhysicsAsBox(world,x,y, BodyDef.BodyType.StaticBody,true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void onContact(Actor other, Contact contact) {
        if(other instanceof FireWall){
            sprite.setColor(0.2f, 0.1f, 0.1f, 1);
        } else if (other instanceof Player) {
            this.remove();
            ScoreManager.getInstance().addCoin();
        }
    }
}
