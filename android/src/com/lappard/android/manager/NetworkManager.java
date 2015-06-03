package com.lappard.android.manager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.codebutler.android_websockets.WebSocketClient;

import java.net.URI;
import java.util.List;
import java.util.Vector;

public class NetworkManager {
    private static final String TAG_WS = "WebSocket";
    private static final String WEBSOCKET_URL = "ws://jonathanwiemers.de:1337";

    private static final String ACTION_CREATE_LEVEL = "create level";

    private WebSocketClient socket;
    private List<EventListener> listeners;

    public interface EventListener{

        public void onEvent(String Event);
    }

    public NetworkManager(){
        listeners = new Vector<EventListener>();
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

    public void connect() {
        socket.connect();
    }
}
