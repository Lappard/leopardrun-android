package com.lappard.android.logic;

import com.badlogic.gdx.utils.TimeUtils;
import com.lappard.android.level.LevelData;
import com.lappard.android.network.NetworkCommand;
import com.lappard.android.util.Event;

/**
 * Created by Jonathan on 01.07.15.
 */
public class ScoreManager {
    public static final String METHOD_SAVE_GAME = "saveGame";

    private static final long SCORE_PER_SECOND = 50;
    private static ScoreManager _instance;
    private float _score;
    private long _startTime;
    private long _endTime;
    public String _playerName = "AndroidUser";

    private ScoreManager() {

    }


    public static ScoreManager getInstance() {
        if (_instance == null) _instance = new ScoreManager();
        return _instance;
    }


    public float getScore() {
        return _score;
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
        game.playerScore = _score;
        game.level = level;

        gameOverCommand.gameData = game;
        Event.getBus().post(gameOverCommand);
    }

    public void update() {
        long mills = TimeUtils.millis();

        float timeGone = mills - _startTime;
        _score = (timeGone / 1000) * SCORE_PER_SECOND;
    };
}
