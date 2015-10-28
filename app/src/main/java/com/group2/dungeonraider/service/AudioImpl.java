package com.group2.dungeonraider.service;

import android.util.Log;

/**
 * Created by Ajinkya on 10/27/2015.
 */
public class AudioImpl implements Audio {
//sdf
    private static final String LOG = AudioImpl.class.getSimpleName();

    @Override
    public boolean mute() {
        Log.d(LOG, "mute() -> Mute game.");
        return false;

    }

    @Override
    public boolean unmute() {
        return false;
    }

    @Override
    public boolean play() {
        return false;
    }
}
