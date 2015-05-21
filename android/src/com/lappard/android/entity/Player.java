package com.lappard.android.entity;

import android.graphics.Rect;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity{

    private Rectangle rect;
    private boolean onGround = false;

    private TextureRegion currentFrame;
    private TextureRegion[] walkingFrames;

    private Animation walkingAnimation;

    public Player(float x, float y, World world){
        Texture walkSheet = new Texture("Runner.png");
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/6, walkSheet.getHeight());              // #10

        walkingFrames = new TextureRegion[6];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 6; j++) {
                walkingFrames[index++] = tmp[i][j];
            }
        }

        walkingAnimation = new Animation(0.025f, walkingFrames);

        rect = new Rectangle(x, y, 1, 2);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2f, rect.getHeight() / 2f);

        initPhysics(shape, x, y, world, true);
        body.setUserData(this);
    }

    public void jump() {
        Log.d("GameLogic", "Player wants to jump. Is on ground: "+onGround);
        if(onGround)
            body.applyLinearImpulse(0, 20, body.getPosition().x, body.getPosition().y, true);
    }

    @Override
    public void update() {
        Vector2 pos = body.getPosition();
        rect.setPosition(pos);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        this.currentFrame = this.walkingAnimation.getKeyFrame(Gdx.graphics.getDeltaTime(), true);
        spriteBatch.begin();
        spriteBatch.draw(this.currentFrame, this.currentFrame.getRegionHeight(), this.currentFrame.getRegionWidth());
        spriteBatch.end();
    }


    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
