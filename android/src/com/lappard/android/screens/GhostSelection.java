package com.lappard.android.screens;


import android.util.Log;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.lappard.android.R;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.logic.GameData;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.network.NetworkManager;
import com.lappard.android.network.NetworkResult;
import com.lappard.android.util.Event;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.Comparator;

public class GhostSelection extends MenuScreen{

    public static final String METHOD_GET_SAVEGAMES = "getSaveGames";

    public GhostSelection() {
        super(AssetManager.TEXTURE_BACKGROUND);
        Event.getBus().register(this);
    }

    @Override
    public void show() {
        super.show();

        layout.remove();
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);

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

        //Sort by Playerscore
        Arrays.sort(result.process.games, new Comparator<GameData>() {
            @Override
            public int compare(GameData lhs, GameData rhs) {
                //swap lhs and rhs so highest score is on top
                return Float.compare(rhs.playerScore, lhs.playerScore);
            }
        });
        if(result.method.equals(METHOD_GET_SAVEGAMES)){
            for(GameData game : result.process.games){
                Log.d("Ghost selection", game.gameName);
                layout.add(createLabel(String.format("%.1g%n",game.playerScore)));
                layout.add(createLabel(game.owner)).padLeft(32);
                layout.add(createLabel(game.gameName)).padLeft(32);
                layout.add(createImageButton(128, 64, AssetManager.TEXTURE_BLOCK,
                        R.string.play, createScreenTransition(GameScreen.class))).padLeft(32).padTop(5);
                layout.row();
            }
        }
    }
}
