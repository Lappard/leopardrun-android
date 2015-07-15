package com.lappard.android.actors;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.lappard.android.audio.AudioManager;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.util.RemoveFeatherTask;

import java.util.List;
import java.util.Vector;

public class Leopard extends PhysicsActor {

    public static float VELOCITY_JUMP = 10;
    public static float VELOCITY_RUN = 3;
    public static float HEIGHT_DEATH = -2;
    public static int SIZE = 90;

    protected long startTime = -1;
    protected List<Long> jumpTimes;
    protected boolean onGround;

    protected boolean feather = false;


    public Leopard(World world, float x, float y, String textureName, short collisionCategory) {
        sprite = new AnimatedSprite(AssetManager.getInstance().getTexture(textureName), 5, 2, 0.1f);
        this.collisionCategory = collisionCategory;
        this.collisionMask = Obstacle.COLLISION_CATEGORY | FireWall.COLLISION_CATEGORY;
        sprite.setSize(SIZE, SIZE);
        initPhysicsAsBox(world, x, y, BodyDef.BodyType.DynamicBody);
        jumpTimes = new Vector<>();

    }

    public boolean hasFeather() {
        return feather;
    }

    public void setHasFeather(boolean hasFeather) {
        this.feather = hasFeather;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //set startTime on first update
        if(startTime == -1)
            startTime = TimeUtils.millis();
        //move
        body.setLinearVelocity(VELOCITY_RUN, body.getLinearVelocity().y);
    }

    public void jump(){
        body.setLinearVelocity(body.getLinearVelocity().x, VELOCITY_JUMP);
        onGround = false;
        AnimatedSprite animatedSprite = (AnimatedSprite) sprite;
        animatedSprite.setLooping(onGround);
    }

    public void applyFeather() {
        setHasFeather(true);
        AudioManager.getInstance().pauseAll();
        AudioManager.getInstance().playSound(AssetManager.SOUND_FEATHER, false);
        //sprite = new AnimatedSprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_FLY), 9, 1, 0.1f);
        Timer.schedule(new RemoveFeatherTask(this), 14);
    }

    @Override
    public void onContact(Actor other, Contact contact) {
        super.onContact(other, contact);
        if (other instanceof Obstacle) {
            onGround = true;
            AnimatedSprite animatedSprite = (AnimatedSprite) sprite;
            animatedSprite.setLooping(onGround);
        }
    }
}
