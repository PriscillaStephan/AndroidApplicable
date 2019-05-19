package prescilla.com.eurisko.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import prescilla.com.eurisko.R;

import static prescilla.com.eurisko.utils.Config.PREFS_NAME;


public class Methods {

    static public String getPref(Context c, String STOTREDkey) {
        String mobileNum = "";
        try {
            SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
            mobileNum = settings.getString(STOTREDkey, "");
        } catch (Exception ex) {
        }
        return mobileNum;
    }

    static public boolean clearPref(Context c) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
        return true;
    }

    static public boolean savePre(Context c, String value, String key) {

        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public static String getStringDate(Date date) {
        return DateFormat.getDateTimeInstance().format(date);
    }

    static public void createNotification(Context context,String tit, String txt) {
        String ADMIN_CHANNEL_ID = "ADMIN_CHANNEL_ID";
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String title = tit;
        String text = txt;
        //Setting up Notification channels for android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager,ADMIN_CHANNEL_ID);
        }
        int notificationId = new Random().nextInt(60000);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.android)
                .setContentTitle(title)
                .setContentText(text)
                .setSound(defaultSoundUri);

        if (notificationManager != null) {
            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static public void setupChannels(NotificationManager notificationManager,String channel) {
        CharSequence adminChannelName = "notifications_admin_channel_name";
        String adminChannelDescription = "notifications_admin_channel_description";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(channel, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }


}
