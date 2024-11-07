package com.example.tp1_musique;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerialiserExoPlayer {
    private static final String FILENAME = "exoPlayer.ser";

    public static void savePlayer(Context context, long position) {
        try (FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeLong(position);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static long loadPlayer(Context context) {
        try (FileInputStream fis = context.openFileInput(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readLong();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
