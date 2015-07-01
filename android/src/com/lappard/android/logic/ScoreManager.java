package com.lappard.android.logic;

import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Jonathan on 01.07.15.
 */
public class ScoreManager {
    private final long _scorePerSekound = 50;
    private static ScoreManager _instance;
    private float _score;
    private long _startTime;
    private long _endTime;

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

    public void endGame() {
        _endTime = TimeUtils.millis();
    }

    public void update() {
        long mills = TimeUtils.millis();

        float timeGone = mills - _startTime;
        _score = (timeGone / 1000) * _scorePerSekound;
    };
}
