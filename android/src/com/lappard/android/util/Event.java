package com.lappard.android.util;

import com.squareup.otto.Bus;

public class Event {

    private static Bus bus;

    public static Bus getBus(){
        if(bus == null){
            bus = new Bus();
        }
        return bus;
    }
}
