package com.lappard.android.level;


import android.util.Log;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.actors.Block;
import com.lappard.android.actors.Floor;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.network.NetworkManager;
import com.lappard.android.network.NetworkResult;
import com.lappard.android.util.Event;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Vector;

public class NetworkLevelCreator implements LevelCreator{

    public static final String LEVEL_CREATION_METHOD = "createLevel";

    public static final String OBJECT_TYPE_BLOCK = "b";
    public static final String OBJECT_TYPE_GROUND = "g";

    private NetworkManager network;
    private World world;


    public NetworkLevelCreator(NetworkManager network, World world) {
        this.network = network;
        this.world = world;
        Event.getBus().register(this);
    }

    @Override
    public void requestLevelData() {
        NetworkCommand command = new NetworkCommand();
        command.method = LEVEL_CREATION_METHOD;
        Event.getBus().post(command);
    }

    @Subscribe
    public void deliverReceivedLevelParts(NetworkManager.MessageReceivedEvent event){
        if(event.result.process.level != null){
            List<Actor> actors = new Vector<>();
            actors.addAll(createActors(event.result.process.level.levelparts[0]));
            //for(LevelData.LevelObject[] part : event.result.process.level.levelparts){

            //}
            Event.getBus().post(new Level(actors));
        }
    }


    private List<Actor> createActors(LevelData.LevelObject[] part) {
        List<Actor> actors = new Vector<>();
        for (LevelData.LevelObject obj : part) {
            switch (obj.type) {
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
