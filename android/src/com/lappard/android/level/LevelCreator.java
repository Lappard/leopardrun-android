package com.lappard.android.level;


import android.util.Log;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.actors.Block;
import com.lappard.android.actors.Floor;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.network.NetworkManager;

import java.util.List;
import java.util.Vector;

public class LevelCreator {

    public static final String OBJECT_TYPE_BLOCK = "b";
    public static final String OBJECT_TYPE_GROUND = "g";

    private NetworkManager network;
    private World world;
    private List<LevelData.LevelObject[]> levelParts;
    private int deliveredParts = 0;

    public interface PartAvailableListener{
        void onPartAvailable(List<Actor> part);
    }

    public LevelCreator(NetworkManager network, World world){
        this.network = network;
        this.world = world;
        levelParts = new Vector<>();
    }

    public void queryLevelPart(PartAvailableListener listener){
        boolean success = deliverLevelParts(listener);
        if(!success){
            fetchLevelPartsFromServer(listener);
        }

    }

    private boolean deliverLevelParts(PartAvailableListener listener){
        if(levelParts.size() > deliveredParts) {
            List<Actor> result = createActors(levelParts.get(deliveredParts));
            deliveredParts++;
            listener.onPartAvailable(result);
            return true;
        }
        return false;
    }

    private void fetchLevelPartsFromServer(final PartAvailableListener listener){

        network.on(NetworkManager.ACTION_CREATE_LEVEL, new NetworkManager.EventListener() {
            @Override
            public void onEvent(NetworkCommand cmd) {
                network.off(NetworkManager.ACTION_CREATE_LEVEL, this);

                if(cmd.process.level != null) {
                    for(LevelData.LevelObject[] part : cmd.process.level.levelparts){
                        levelParts.add(part);
                    }
                    deliverLevelParts(listener);
                }

            }
        });
        network.emit(NetworkManager.ACTION_CREATE_LEVEL);
    }

    private List<Actor> createActors(LevelData.LevelObject[] part){
        List<Actor> actors = new Vector<>();
        for(LevelData.LevelObject obj : part){
            switch (obj.type){
                case OBJECT_TYPE_BLOCK:
                    actors.add(new Block(world, obj.x * 2, obj.y * 2 - 1));
                    break;
                case OBJECT_TYPE_GROUND:
                    actors.add(new Floor(world, obj.x * 2, obj.y * 2 - 1));
                    break;
            }
        }

        return actors;
    }
}
