package com.lappard.android.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;
import com.lappard.android.level.LevelData;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.util.Event;

public class ScoreManager {
    public static final String METHOD_SAVE_GAME = "saveGame";
    public static final String PREFS_PLAYERNAME = "preferences_playername";
    public static final String KEY_PLAYERNAME = "playername";
    public static final int COIN_SCORE = 500;

    private static final long SCORE_PER_SECOND = 50;
    private static ScoreManager _instance;
    private float _score;
    private long _startTime;
    private long _endTime;
    private int _collectedCoins = 0;
    public String _playerName = "AndroidUser";
    Preferences prefs = Gdx.app.getPreferences(PREFS_PLAYERNAME);

    private ScoreManager() {
        _playerName = prefs.getString(KEY_PLAYERNAME, "AndroidUser");
    }


    public static ScoreManager getInstance() {
        if (_instance == null) _instance = new ScoreManager();
        return _instance;
    }


    public float getScore() {
        return _score + (_collectedCoins * COIN_SCORE);
    }

    public void setPlayername(String playername) {
        _playerName = playername;
        prefs.putString(KEY_PLAYERNAME, playername);
        prefs.flush();
    }

    public void startGame() {
        _startTime = TimeUtils.millis();
    }

    public void endGame(long[] playerJumps, LevelData level) {
        _endTime = TimeUtils.millis();

        NetworkCommand gameOverCommand = new NetworkCommand();
        gameOverCommand.method = METHOD_SAVE_GAME;
        GameData game = new GameData();
        game.actions = playerJumps;
        game.owner = _playerName;

        //create more or less random name
        String[] vs = new String[]{"a", "e", "i", "o", "u"};
        String[] cs = new String[]{"b", "d", "f", "g", "k", "l", "m", "n", "p", "r", "s", "t"};
        String c = cs[(int)(Math.random() * cs.length)];
        game.gameName =  cs[(int)(Math.random() * cs.length)]
                        + vs[(int)(Math.random() * vs.length)]
                        + c + c + vs[(int)(Math.random() * vs.length)]
                        + cs[(int)(Math.random() * cs.length)];

        game.date = (int)TimeUtils.millis();
        game.playerScore = (int)_score;
        game.level = level;

        gameOverCommand.gameData = game;
        Event.getBus().post(gameOverCommand);
    }

    public void update() {
        long mills = TimeUtils.millis();

        float timeGone = mills - _startTime;
        _score = (timeGone / 1000) * SCORE_PER_SECOND;
    }

    public void addCoin() {
        _collectedCoins++;
    }
}
