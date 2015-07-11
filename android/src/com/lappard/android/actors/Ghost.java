package com.lappard.android.actors;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.lappard.android.graphic.AssetManager;


public class Ghost extends Leopard {


    public Ghost(World world, float x, float y, long[] jumpTimes) {
        super(world, x, y, AssetManager.TEXTURE_GHOST);
        for(long timestamp : jumpTimes){
            this.jumpTimes.add(timestamp);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(TimeUtils.millis() - startTime > jumpTimes.get(0)){
            jump();
            jumpTimes.remove(0);
        }

    }

    @Override
    public void onContact(Actor other, Contact contact) {
        if(other instanceof Player){
            contact.setEnabled(false);
        }
    }
}
