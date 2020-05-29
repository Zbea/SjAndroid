package com.hazz.kuangji.socket;

import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


public class WebSocket extends WebSocketClient {

    private static final String TAG = "WebSocket";

    private volatile static WebSocket sWebSocket;

    private static final String TOPIC_POSITION = "position";
    private static final String TOPIC_ORDER_BOOK = "orderbook";
    private static final String TOPIC_POSITION_PUSH = "position-push";
    private static final String TOPIC_SNAPSHOT = "snapshot";
    private static final String TOPIC_LOGIN = "login";
    private static final String TOPIC_ORDER_STATUS = "orderstatus";
    private static final String TOPIC_TICK = "tick";
    private static final String TOPIC_K = "k";
    private static final String TOPIC_HISTICK = "histick";

    private String mLastSubscribeDepth;
    private static final int DEPTH_LENGTH = 7;

    private static final String USDT = "USDT";
    private static final String BC = "BC";
    private static final String BTC = "BTC";
    private static final String AUC = "AUC";
    private static long lastClickTime = 0;

    public static final int MIN_CLICK_DELAY_TIME = 2000;

    private Gson mGson;

    private boolean mConnecting = false;

    //    private Map<String, ArrayList<SocketListener>> mListeners = new HashMap<>();
//    private Map<String, ArrayList<SocketTickListener>> mListenerTick = new HashMap<>();
    private LinkedBlockingQueue<Request> mRequestQueue = new LinkedBlockingQueue<>();
    private String mMarket;

    private WebSocket(URI serverUri) {
        super(serverUri);
        mGson = new Gson();
    }


    public static WebSocket getInstance() {
        if (sWebSocket == null) {
            synchronized (WebSocketClient.class) {
                if (sWebSocket == null) {
                    try {
                        sWebSocket = new WebSocket(new URI("ws://p_hb_ws.ratafee.nl/"));
                    } catch (URISyntaxException e) {
                        Log.d("junjun", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
        return sWebSocket;
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("junjun", "onOpen");
        if (mConnecting) {
            mConnecting = false;
        }
    }


    @Override
    public void onMessage(String message) {
        Log.d("junjun", message);
    }


    @Override
    public void onClose(int code, String reason, boolean remote) {
        Logger.d("onClose " + code + " " + reason + " " + remote);
       // sWebSocket = null;
    }

    @Override
    public void onError(Exception ex) {
        Logger.d("onError: " + ex.getMessage());
    }

    public void tryToConnect() {
        Logger.d("tryToConnect " + getReadyState());
        if (getReadyState() == READYSTATE.NOT_YET_CONNECTED && !mConnecting) {
            mConnecting = true;
            connect();
        }
    }

    public void requestK(String symbol, String period) {
        if (isOpen()) {
            Kbody kBody = new Kbody();
            kBody.req = symbol;
            kBody.id =period;
            String s = mGson.toJson(kBody);
            Logger.d(s);
            send(s);
        } else {
            queue(new Request(symbol, period));
        }
    }

    private void queue(Request top) {
        try {
            mRequestQueue.put(top);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public class Request {
        String topic;
        List<String> params = new ArrayList<>();

        public Request(String topic) {
            this.topic = topic;
        }

        public Request(String topic, String... params) {
            this.topic = topic;
            for (int i = 0; i < params.length; i++) {
                this.params.add(params[0]);
            }

        }
    }
}
