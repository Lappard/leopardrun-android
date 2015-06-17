package com.lappard.android.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.util.AnimatedSprite;

public class Player extends PhysicsActor {

    public Player(World world) {
        sprite = new AnimatedSprite(new Texture("cat.png"), 5, 2, 0.1f);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(210, 210);
        initPhysics(shape, 10, 10, world, false);
    }

}
