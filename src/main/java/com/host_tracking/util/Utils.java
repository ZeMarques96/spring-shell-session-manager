package com.host_tracking.util;

import com.host_tracking.model.entities.OpennedTime;

public class Utils {

    public static OpennedTime convertTime(Long diff){
        Long hours = diff / 60;
        Long minutes = diff % 60;
        return new OpennedTime(hours, minutes);
    }
}
