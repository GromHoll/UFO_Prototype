package com.gromholl.UfoPrototype.view;

import android.util.Log;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

/**
 * Created by GromHoll on 02.02.14.
 */
public class Sounds {

    private Sound shot;
    private Music music;

    public Sounds(final BaseGameActivity activity) {
        SoundFactory.setAssetBasePath("sounds/");
        MusicFactory.setAssetBasePath("sounds/");

        this.shot = getSound(activity, "shoot.ogg");
        this.music = getMusic(activity, "music.ogg");
    }

    private Sound getSound(final BaseGameActivity activity, String name) {
        try {
            return SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, name);
        } catch (IOException exc) {
            Log.d("UFO", "Sound not loaded", exc);
            System.exit(-1);
        }
        return null;
    }

    private Music getMusic(final BaseGameActivity activity, String name) {
        try {
            Music music = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, name);
            music.setLooping(true);
            return music;
        } catch (IOException exc) {
            Log.d("UFO", "Music not loaded", exc);
            exc.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public Sound getShot() {
        return shot;
    }

    public Music getMusic() {
        return music;
    }
}
