package com.lappard.android.logic.level;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.data.Level;
import com.lappard.android.data.NetworkCommand;
import com.lappard.android.entity.Box;
import com.lappard.android.entity.Entity;
import com.lappard.android.entity.Floor;
import com.lappard.android.manager.NetworkManager;

import java.util.List;
import java.util.Vector;

/**
 * Created by Jonas on 04.06.2015.
 */
public class ServerLevelGenerator extends LevelGenerator implements NetworkManager.EventListener{

    private static final String TYPE_BOX = "b";
    private static final String TYPE_GROUND= "g";

    public ServerLevelGenerator(World world){
        super(world);
        NetworkManager.getInstance().on(NetworkManager.ACTION_CREATE_LEVEL, this);
    }

    @Override
    public void requestNextEntities() {
        NetworkManager.getInstance().emit(NetworkManager.ACTION_CREATE_LEVEL);
    }

    @Override
    public void onEvent(NetworkCommand cmd) {
        if(listener != null){
            List<Entity> generatedEntities = new Vector<Entity>();
            for (Level.LevelObject[] part : cmd.process.level.levelparts) {
                for (Level.LevelObject o : part) {
                    if(o.type.equals(TYPE_BOX) ){
                        generatedEntities.add(new Box(o.x, o.y, world));
                    } else if(o.type.equals(TYPE_GROUND)){
                        generatedEntities.add(new Floor(o.x, o.y, world));
                    }

                }
            }
            listener.onEntitiesAvailable(generatedEntities);
        }
    }
}
