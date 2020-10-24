package pt.ipp.estg.dwdm.notifyme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "channel 1";
    public static final int NOTIFICATION_ID = 1;

    // NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    // Button update = (Button) findViewById(R.id.update_button);
    // Button cancel = (Button) findViewById(R.id.cancel_button);
    // Button notifyMe = (Button) findViewById(R.id.notify_button); ////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        Button update = (Button) findViewById(R.id.update_button); // *
        Button cancel = (Button) findViewById(R.id.cancel_button); // *
        update.setEnabled(false);
        cancel.setEnabled(false);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            // Creates a notification manager object
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        // Set up the pending intent that is delivered when the notification is clicked
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent updateIntent = new Intent(this, MainActivity.class);
        PendingIntent piUpdate = PendingIntent.getActivity(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification with all the parameters.
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.warning)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(R.drawable.update, getString(R.string.update), piUpdate)
                .setDeleteIntent(notificationPendingIntent);

        return builder;
    }

    public void sendNotification(View view) {
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        NotificationManagerCompat notifyManager = NotificationManagerCompat.from(this);
        notifyManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        Button notifyMe = (Button) findViewById(R.id.notify_button); // *
        Button update = (Button) findViewById(R.id.update_button); // *
        Button cancel = (Button) findViewById(R.id.cancel_button); // *

        notifyMe.setEnabled(false);
        update.setEnabled(true);
        cancel.setEnabled(true);

    }

    public void cancelNotification(View view) {
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); // *
        mNotifyMgr.cancel(NOTIFICATION_ID);

        Button notifyMe = (Button) findViewById(R.id.notify_button); // *
        Button update = (Button) findViewById(R.id.update_button); // *
        Button cancel = (Button) findViewById(R.id.cancel_button); // *

        notifyMe.setEnabled(true);
        update.setEnabled(false);
        cancel.setEnabled(false);

    }

    public void updateNotification(View view) {
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); // *

        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);

        NotificationCompat.Builder nBuilder = getNotificationBuilder();

        nBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle((getString(R.string.notification_new_title))));


        mNotifyMgr.notify(NOTIFICATION_ID, nBuilder.build());
        Button update = (Button) findViewById(R.id.update_button); // *
        update.setEnabled(false);


    }





}