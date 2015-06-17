package com.lappard.android.logic.level;

import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.entity.Entity;

import java.util.List;

/**
 * Created by Jonas on 04.06.2015.
 */
public abstract class LevelGenerator {

    protected EntitiesAvailableListener listener;
    protected World world;

    public LevelGenerator(World world){
        this.world = world;
    }

    public interface EntitiesAvailableListener{
        public void onEntitiesAvailable(List<Entity> generatedEnities);
    }

    public abstract void requestNextEntities();
    public void setEntitiesAvailableListener(EntitiesAvailableListener eal){
        listener = eal;
    }
}
