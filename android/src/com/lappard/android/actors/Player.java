package com.lappard.android.actors;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.lappard.android.audio.AudioManager;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.util.Event;

import java.util.List;
import java.util.Vector;

public class Player extends Leopard {

    public static final short COLLISION_CATEGORY = 0x1;


    public class IsDeadEvent{
        public long[] jumps;


        public IsDeadEvent(){}

        public IsDeadEvent(long[] jumps){
            this.jumps = jumps;
        }

        public  IsDeadEvent(List<Long> jumps){
            int size = jumps.size();
            this.jumps = new long[size];
            for(int i = 0; i < size; i++){
                this.jumps[i] = jumps.get(i);
            }
        }
    }

    private boolean canJump;

    public Player(World world, float x, float y) {
        super(world, x, y, AssetManager.TEXTURE_CAT, COLLISION_CATEGORY);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        AnimatedSprite temp = (AnimatedSprite) sprite;
        if(body.getPosition().y < HEIGHT_DEATH){
            Event.getBus().post(new IsDeadEvent(jumpTimes));
        }
        if (canJump && !temp.getLooping()) {
            temp.setLooping(true);
        }
    }

    @Override
    public void onContact(Actor other, Contact contact) {
        if (other instanceof Obstacle) {
            canJump = true;
        }
        if(other instanceof FireWall){
            Event.getBus().post(new IsDeadEvent(jumpTimes));
        }
        if(other instanceof Ghost){
            contact.setEnabled(false);
        }
    }

    @Override
    public void jump() {
        if (canJump) {
            jumpTimes.add(TimeUtils.millis() - startTime);
            super.jump();
            canJump = false;
            AudioManager.getInstance().playSound(AssetManager.SOUND_JUMP);
        }

    }

}
