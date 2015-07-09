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

public class Player extends PhysicsActor {

    private long startTime = -1;
    private List<Long> jumpTimes;

    public class IsDeadEvent{
        public long[] jumps;


        public IsDeadEvent(){}

        public  IsDeadEvent(long[] jumps){
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
        sprite = new AnimatedSprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_CAT), 5, 2, 0.1f);
        sprite.setSize(90, 90);
        initPhysicsAsBox(world, x, y, BodyDef.BodyType.DynamicBody);
        jumpTimes = new Vector<>();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(3f, body.getLinearVelocity().y);
        //set startTime on first update
        if(startTime == -1)
            startTime = TimeUtils.millis();
        //body.applyLinearImpulse(new Vector2(0.4f, 0), body.getWorldCenter(), true);
        if(body.getPosition().y < -2){
            Event.getBus().post(new IsDeadEvent(jumpTimes));
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
    }

    public void jump() {
        if (canJump) {
            jumpTimes.add(TimeUtils.millis() - startTime);
            body.setLinearVelocity(body.getLinearVelocity().x, 15);
            //body.applyLinearImpulse(new Vector2(0, 12), body.getWorldCenter(), true);
            canJump = false;
            AudioManager.getInstance().playSound(AssetManager.SOUND_JUMP);
        }

    }


}
