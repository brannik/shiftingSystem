package com.example.shiftingsystem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class client {
    notify not = new notify();
    public void getJSON(final String urlWebService) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Log.d("DEBUG",s);
                try {
                    strToArray(s);
                } catch (JSONException e) {
                    Log.d("DEBUG","ERROR -> " + e);
                }
                // populate public array with all data returned from php page
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {



                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    public void strToArray(String st) throws JSONException {
        JSONArray jArray = new JSONArray(st);
        for(int i=0;i<jArray.length();i++){
            JSONObject obj = jArray.getJSONObject(i).getJSONObject("notification");
            int id = obj.getInt("id");
            String sender = obj.getString("sender");
            int type = obj.getInt("type");
            String text = obj.getString("text");
            String timeS = obj.getString("time");

            not.makeNotification(id,sender,type,text,timeS);
        }
    }

    public void callAsynchronousTask() {
        Handler handler = new Handler();
        // Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                Log.d("DEBUG", "Called on main thread");
                String id = MainActivity.getDevId();
                getJSON("http://192.168.0.101/core/api.php?action=requestData&id=" + id);
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 100000);
            }
        };
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
    }


}
