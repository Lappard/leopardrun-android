package com.lappard.android.util;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class Event {

    private static Bus bus;

    public static Bus getBus(){
        if(bus == null){
            bus = new Bus(ThreadEnforcer.ANY);
        }
        return bus;
    }
}
