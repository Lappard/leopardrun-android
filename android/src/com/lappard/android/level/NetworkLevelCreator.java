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

public class NetworkLevelCreator extends LevelCreator {

    public static final String METHOD_CREATE_LEVEL = "createLevel";

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



    @Subscribe
    public void executeRequest(NetworkManager.ConnectionEstablishedEvent e) {
        NetworkCommand command = new NetworkCommand();
        command.method = METHOD_CREATE_LEVEL;
        Event.getBus().post(command);
    }

    @Subscribe
    public void deliverReceivedLevelParts(NetworkManager.MessageReceivedEvent event) {
        if (event.result.process.level != null) {
            for (LevelData.LevelObject[] part : event.result.process.level.levelparts) {
                receivedParts.add(part);
            }
            List<Actor> parsedActors = parseLevel(event.result.process.level);
            Event.getBus().post(new Level(parsedActors));
        }
    }


}
