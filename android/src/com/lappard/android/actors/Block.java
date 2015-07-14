package com.lappard.android.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.screens.GameScreen;

public class Block extends Obstacle {

    public static float SIZE = 50;

    public Block(World world, float x, float y) {
        super(world, new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BLOCK)),
                SIZE,
                SIZE,
                x + 1.5f * SIZE / GameScreen.PIXEL_PER_METER,
                y + 1.5f * SIZE / GameScreen.PIXEL_PER_METER

        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
