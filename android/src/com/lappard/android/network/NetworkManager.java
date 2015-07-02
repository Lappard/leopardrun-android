package com.lappard.android.network;

import android.util.Log;

import com.badlogic.gdx.Net;
import com.codebutler.android_websockets.WebSocketClient;
import com.google.gson.Gson;
import com.lappard.android.util.Event;
import com.squareup.otto.Subscribe;

import java.net.URI;

public class NetworkManager {
    private static final String TAG_WS = "WebSocket";
    private static final String WEBSOCKET_URL = "ws://jonathanwiemers.de:1337";

    private static NetworkManager instance;

    public class ConnectionEstablishedEvent{
        public String guid;
    }

    public class ErrorEvent{
        public Exception error;
    }

    public class ConnectionClosedEvent{
        public int code;
        public String reason;
    }

    public class MessageReceivedEvent{
        public NetworkResult result;
    }

    private WebSocketClient socket;
    private Gson gson;
    private boolean connected;



    public NetworkManager(){
        gson = new Gson();
        connected = false;
        prepareConnection();
        Event.getBus().register(this);
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
                NetworkResult result = gson.fromJson(message, NetworkResult.class);
                if(result.method == null){
                    connected = true;
                    ConnectionEstablishedEvent event = new ConnectionEstablishedEvent();
                    Event.getBus().post(event);
                }else{
                    MessageReceivedEvent event = new MessageReceivedEvent();
                    event.result = result;
                    Event.getBus().post(event);
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
                ConnectionClosedEvent event = new ConnectionClosedEvent();
                event.code = code;
                event.reason = reason;
                Event.getBus().post(event);
            }

            @Override
            public void onError(Exception error) {
                Log.e(TAG_WS, "Exceptional Error:" + error.toString());
                error.printStackTrace();
                ErrorEvent event = new ErrorEvent();
                event.error = error;
                Event.getBus().post(event);

            }
        }, null);
    };

    @Subscribe
    public void emit(NetworkCommand command){
        socket.send(gson.toJson(command));
    }

    public boolean isConnected(){
        return connected;
    }

    public void connect() {
        socket.connect();
    }

    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }


}
