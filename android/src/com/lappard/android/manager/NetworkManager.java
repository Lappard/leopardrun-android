package com.lappard.android.manager;

import android.util.Log;

import com.codebutler.android_websockets.WebSocketClient;

import java.net.URI;

public class NetworkManager {
    private static final String TAG_WS = "WebSocket";
    private static final String WEBSOCKET_URL = "ws://jonathanwiemers.de:1337";

    private WebSocketClient socket;

    public NetworkManager(){
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
    }

    public void connect(){
        socket.connect();
    }
}
