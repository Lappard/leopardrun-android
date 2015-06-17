package com.lappard.android.manager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.codebutler.android_websockets.WebSocketClient;
import com.google.gson.Gson;
import com.lappard.android.data.Level;
import com.lappard.android.data.NetworkCommand;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class NetworkManager {
    private static final String TAG_WS = "WebSocket";
    private static final String WEBSOCKET_URL = "ws://jonathanwiemers.de:1337";

    public static final String ACTION_CONNECTION_ESTABLISHED = "connected";
    public static final String ACTION_CREATE_LEVEL = "createLevel";

    private WebSocketClient socket;
    private HashMap<String,List<EventListener>> listeners;
    private Gson gson;

    private static NetworkManager instance;

    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return  instance;
    }

    public interface EventListener{
        public void onEvent(NetworkCommand cmd);
    }

    private NetworkManager(){
        gson = new Gson();
        listeners = new HashMap<String, List<EventListener>>();
        prepareConnection();
    }

    private void prepareConnection() {
        socket = new WebSocketClient(URI.create(WEBSOCKET_URL),new WebSocketClient.Listener() {
            @Override
            public void onConnect() {
                Log.d(TAG_WS, "Connected to " + WEBSOCKET_URL);
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG_WS, "Message:" + message);
                NetworkCommand cmd = gson.fromJson(message, NetworkCommand.class);
                if(cmd.method == null){
                    cmd.method = ACTION_CONNECTION_ESTABLISHED;
                }
                if(listeners.containsKey(cmd.method)){
                    for(EventListener l : listeners.get(cmd.method)){
                        l.onEvent(cmd);
                    }
                }
            }

            @Override
            public void onMessage(byte[] data) {
                Log.d(TAG_WS, "ByteMessage:" + data.toString());
            }

            @Override
            public void onDisconnect(int code, String reason) {
                Log.d(TAG_WS, "Disconnected from " + WEBSOCKET_URL + " with Code " + code
                        + ". Reason: " + reason);
            }

            @Override
            public void onError(Exception error) {
                Log.e(TAG_WS, "Exceptional Error:" + error.toString());
                error.printStackTrace();
            }
        }, null);
    };

    public void on(String eventName, EventListener listener){
        if(!listeners.containsKey(eventName)){
            listeners.put(eventName, new Vector<EventListener>());
        }
        listeners.get(eventName).add(listener);
    }

    public void emit(String eventName){
        NetworkCommand cmd = new NetworkCommand();
        cmd.method = eventName;
        socket.send(gson.toJson(cmd));
    }

    public void connect() {
        socket.connect();
    }
}
