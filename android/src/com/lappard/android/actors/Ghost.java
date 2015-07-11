package com.lappard.android.actors;


import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.graphic.AssetManager;

public class Ghost extends Leopard {
    public Ghost(World world, float x, float y) {
        super(world, x, y, AssetManager.TEXTURE_GHOST);
    }
}
