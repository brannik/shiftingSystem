package com.example.shiftingsystem;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


public class notify extends AppCompatActivity {

    Context appContext = MainActivity.getAppContext();
    NotificationManager notificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

    }

    public void makeNotification(int id, String sender, int type, String text, String time){


        Log.d("DEBUG", "NOT -> type =" + type + " <|>ID=" +id + "<|>SENDER=" + sender + "<|>TEXT=" + text + "<|>TIMESTAMP=" + time);
    }
}
