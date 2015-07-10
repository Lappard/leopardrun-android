package com.lappard.android.level;


import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.actors.Block;
import com.lappard.android.actors.Floor;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.network.NetworkManager;
import com.lappard.android.util.Event;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Vector;

public class NetworkLevelCreator implements LevelCreator {

    public static final String METHOD_CREATE_LEVEL = "createLevel";

    public static final String OBJECT_TYPE_BLOCK = "b";
    public static final String OBJECT_TYPE_GROUND = "g";

    private World world;

    private int lastX;
    private List<LevelData.LevelObject[]> receivedParts;

    public NetworkLevelCreator() {
        receivedParts = new Vector<>();
        Event.getBus().register(this);
    }

    @Override
    public void requestLevelData() {
        //request level data if connected
        if (NetworkManager.getInstance().isConnected()) {
            executeRequest(null);
        } else {
            //otherwise establish connection first
            NetworkManager.getInstance().connect();
        }

    }

    @Override
    public LevelData getRawData() {
        LevelData raw = new LevelData();
        int size = receivedParts.size();
        raw.levelparts = new LevelData.LevelObject[size][];
        for(int i=0; i < size; i++){
            raw.levelparts[i] = receivedParts.get(i);
        }
        return raw;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Subscribe
    public void executeRequest(NetworkManager.ConnectionEstablishedEvent e) {
        NetworkCommand command = new NetworkCommand();
        command.method = METHOD_CREATE_LEVEL;
        Event.getBus().post(command);
    }

    @Subscribe
    public void deliverReceivedLevelParts(NetworkManager.MessageReceivedEvent event) {
        if (event.result.process.level != null) {
            List<Actor> actors = new Vector<>();
            for (LevelData.LevelObject[] part : event.result.process.level.levelparts) {
                receivedParts.add(part);
                actors.addAll(createActors(part));
                lastX += part[part.length - 1].x;
            }
            Event.getBus().post(new Level(actors));
        }
    }


    private List<Actor> createActors(LevelData.LevelObject[] part) {
        List<Actor> actors = new Vector<>();
        for (LevelData.LevelObject obj : part) {
            switch (obj.type) {
                case OBJECT_TYPE_BLOCK:
                    actors.add(new Block(world, (lastX + obj.x) * 2, obj.y * 2 - 1));
                    break;
                case OBJECT_TYPE_GROUND:
                    actors.add(new Floor(world, (lastX + obj.x) * 2, obj.y * 2 - 1));
                    break;
            }
        }

        return actors;
    }
}
