package com.lappard.android.actors;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.lappard.android.graphic.AssetManager;


public class Ghost extends Leopard {

    public static final short COLLISION_CATEGORY = 0x1 << 2;


    public Ghost(World world, float x, float y, long[] jumpTimes) {
        super(world, x, y, AssetManager.TEXTURE_GHOST, COLLISION_CATEGORY);
        for(long timestamp : jumpTimes){
            this.jumpTimes.add(timestamp);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(jumpTimes.size() > 0 && TimeUtils.millis() - startTime > jumpTimes.get(0)){
            jump();
            jumpTimes.remove(0);
        }

    }

    @Override
    public void onContact(Actor other, Contact contact) {
        if (other instanceof FireWall) {
            this.remove();
        }
    }
}
