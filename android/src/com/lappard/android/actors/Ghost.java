package com.lappard.android.actors;


import com.badlogic.gdx.physics.box2d.World;
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
}
