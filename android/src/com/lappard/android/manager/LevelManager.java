package com.lappard.android.manager;

import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.data.Level;
import com.lappard.android.entity.Entity;
import com.lappard.android.logic.level.LevelGenerator;

import java.util.List;

public class LevelManager implements LevelGenerator.EntitiesAvailableListener {

    private LevelGenerator generator;
    private World world;

    public LevelManager(World world, LevelGenerator generator){
        this.world = world;
        this.generator = generator;
        generator.setEntitiesAvailableListener(this);
    }

    public void expandLevel(){
        generator.requestNextEntities();
    }


    @Override
    public void onEntitiesAvailable(List<Entity> generatedEnities) {

    }
}
