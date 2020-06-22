package hozorghiyab.activities;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.hozorghiyab.R;

import java.util.Timer;
import java.util.TimerTask;

import hozorghiyab.cityDetail.LoadData;
import hozorghiyab.customClasses.SharedPrefClass;

public class NotificationService extends Service {

    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 5;
    final static String MY_ACTION = "MY_ACTION";

    public static final String
            ACTION_LOCATION_BROADCAST = NotificationService.class.getName() + "LocationBroadcast",
            EXTRA_LATITUDE = "extra_latitude",
            EXTRA_LONGITUDE = "extra_longitude";

    private static final int
            MIN_TIME = 2000,
            MIN_DISTANCE = 1;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }



    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);


    }

    //we are going to use a handler to be able to run in our TimerTask
    Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000); //
        //timer.schedule(timerTask, 5000,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                        //TODO CALL NOTIFICATION FUNC
                        //YOURNOTIFICATIONFUNCTION();
                        String username = SharedPrefClass.getUserId(getBaseContext(),"user");
                        if (!username.isEmpty()){
                        String tedadPayamHayeKhandeNashode = LoadData.loadTeacherNameAndCountMessageNotReadForServise(getBaseContext(),username);

                        if (tedadPayamHayeKhandeNashode.equals("")||tedadPayamHayeKhandeNashode.equals("0")){

                        }else {
                            Intent intent = new Intent();
                            intent.setAction(MY_ACTION);
                            intent.putExtra("DATAPASSED", tedadPayamHayeKhandeNashode);
                            sendBroadcast(intent);

                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 10000ms

                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), "1")
                                            .setSmallIcon(R.drawable.hozorghiyabicon)
                                            .setSound(Uri.parse("android.resource://" + getBaseContext().getPackageName() + "/" + R.raw.sound))//*see note)
                                            .setContentTitle("یک پیام جدید")
                                            .setContentText("یک پیام جدید موجوده")
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());

// notificationId is a unique int for each notification that you must define
                                    notificationManager.notify(1, builder.build());

                                }
                            }, 2000);


                        }
                    }


                    }
                });
            }
        };
    }
}
