package hozorghiyab.customClasses;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.hozorghiyab.R;

public class CreateNotification {

    public void createNotificationChannel(Context c){

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = c.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(Context c, final Class<? extends Activity> activity,String title,
                                 String secondText,String thridText) {
        Intent intent = new Intent(c, activity);
        PendingIntent pendingIntent = PendingIntent.getActivity(c,0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "CHANNEL_ID")
                .setSmallIcon(R.drawable.date_icon)
                .setContentTitle(title)
                .setContentText(secondText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(thridText))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());

    }

}