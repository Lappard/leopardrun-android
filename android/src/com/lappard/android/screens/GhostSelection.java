package com.lappard.android.screens;


import android.util.Log;

import com.google.gson.Gson;
import com.lappard.android.R;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.logic.GameData;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.network.NetworkManager;
import com.lappard.android.network.NetworkResult;
import com.lappard.android.util.Event;
import com.squareup.otto.Subscribe;

public class GhostSelection extends MenuScreen{

    public static final String METHOD_GET_SAVEGAMES = "getSaveGames";

    public GhostSelection() {
        super(AssetManager.TEXTURE_BACKGROUND);
        Event.getBus().register(this);
    }

    @Override
    public void show() {
        super.show();

        layout.add(createLabel(R.string.ghost_selection_header)).center().colspan(4);
        layout.row();
        font = AssetManager.getInstance().getFont(AssetManager.FONT_OPENSANS_16);


        if(NetworkManager.getInstance().isConnected()){
            onConnectionEstablished(null);
        }else{
            NetworkManager.getInstance().connect();
        }

    }

    @Subscribe
    public void onConnectionEstablished(NetworkManager.ConnectionEstablishedEvent e){
        NetworkCommand command = new NetworkCommand();
        command.method = METHOD_GET_SAVEGAMES;
        NetworkManager.getInstance().emit(command);
    }

    @Subscribe
    public void onGamesAvailable(NetworkManager.MessageReceivedEvent message){
        NetworkResult result = message.result;
        if(result.method.equals(METHOD_GET_SAVEGAMES)){
            for(GameData game : result.process.Games){
                Log.d("Ghost selection", game.GameName);
                layout.add(createLabel("" + game.PlayerScore));
                layout.add(createLabel(game.Owner)).padLeft(32);
                layout.add(createLabel(game.GameName)).padLeft(32);
                layout.add(createImageButton(128, 64, AssetManager.TEXTURE_BLOCK,
                        R.string.play, createScreenTransition(GameScreen.class))).padLeft(32).padTop(5);
                layout.row();
            }
        }
    }
}
