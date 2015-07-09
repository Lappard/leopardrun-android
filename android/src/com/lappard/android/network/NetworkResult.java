package com.lappard.android.network;

import com.lappard.android.level.LevelData;
import com.lappard.android.logic.GameData;


public class NetworkResult extends NetworkCommand {

    public class Process {
        public LevelData level;
        public GameData[] games;
    }

    public Process process;
}
