package quanticheart.com.testnotification;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

/*
 * Copyright(c) Developed by John Alves at 2019/01/08 - BOOMMM!
 * .
 */

@SuppressWarnings("all")
public class NotificationSend {

    //==============================================================================================
    //
    // ** Init Vars
    //
    //==============================================================================================

    private static final String TAG = "Firebase Cloud MSG";


    //==============================================================================================
    //
    // ** Show NotificationSend
    //
    //==============================================================================================

    /**
     * @param context - init
     */
    public static void sendNotification(Context context, String title, String mensagem, String anotherAppPackageManifest) {
        Intent intent = createIntentFromAotherAPP(context, anotherAppPackageManifest);
        if (intent != null)
            showNotification(createNotificationManager(context), createNotificationBuilder(context, title, mensagem, intent));
    }

    /**
     * @param context - init
     */
    public static void sendNotification(Context context, String title, String mensagem, Intent intent) {
        showNotification(createNotificationManager(context), createNotificationBuilder(context, title, mensagem, intent));
    }

    //==============================================================================================
    //
    // ** Show NotificationSend
    //
    //==============================================================================================

    /**
     * @param notificationManager - sys manager
     * @param notification        - new notification
     */
    private static void showNotification(NotificationManager notificationManager, android.app.Notification notification) {
        notificationManager.notify(getRamdomID(), notification);
    }

    //==============================================================================================
    //
    // ** Create NotificationSend Builder
    //
    //==============================================================================================

    /**
     * @param context     - init
     * @param Title       - title
     * @param messageBody - msg notification
     * @return new notification
     */
    private static android.app.Notification createNotificationBuilder(Context context, String Title, String messageBody, Intent intentAnotherApp) {

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender().setHintHideIcon(true);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(getLargeIcon(context))
                .setSmallIcon(getIcon(context))
                .setColor(getColor())
                .setContentTitle(Title)
                .setContentText(messageBody)
                .setStyle(getBigTextStyle(messageBody))
                .setAutoCancel(true)
//                .setSound(getSound(context))
                .setVibrate(getVibration())
                .setChannelId(getChannelID(context))
                .setContentIntent(getPendingIntent(context, intentAnotherApp))
                .extend(wearableExtender);

        return notificationBuilder.build();
    }

    //==============================================================================================
    //
    // ** Create NotificationSend Manager
    //
    //==============================================================================================

    /**
     * @param context - init
     * @return - new NotificationManager
     */
    private static NotificationManager createNotificationManager(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(getChannelID(context), getChannelName(), getChannelImportance());
            notificationChannel.setDescription(getChannelDescription());
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(getChannelLightColor());
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(getVibration());
            notificationChannel.setBypassDnd(getBypassDND(notificationChannel));
            notificationChannel.setLockscreenVisibility(getChannelLockscreenVisibility());
//            notificationChannel.setSound(getSound(context), getAudioAttributes());
            notificationChannel.setGroup(createNewGroupNotification(context));
            notificationManager.createNotificationChannel(notificationChannel);

        }
        return notificationManager;
    }

    //==============================================================================================
    //
    // ** Create NotificationSend Group in Oreo
    //
    //==============================================================================================

    /**
     * @param context - init
     * @return new Group NotificationSend for oreo
     */
    private static String createNewGroupNotification(Context context) {
        String groupId = "group_id_11";
        CharSequence groupName = "Informativos";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));
        }
        return groupId;
    }

    //==============================================================================================
    //
    // ** Utils for Oreo
    //
    //==============================================================================================

    private static String getChannelName() {
        return "Infos";
    }

    private static String getChannelDescription() {
        return "Informações sobre a movida e suas promoções";
    }

    private static String getChannelID(Context context) {
        return context.getResources().getString(R.string.d_canal);
    }

    private static int getChannelLightColor() {
        return Color.YELLOW;
    }

    /**
     * IMPORTANCE_MAX: unused
     * IMPORTANCE_HIGH: shows everywhere, makes noise and peeks
     * IMPORTANCE_DEFAULT: shows everywhere, makes noise, but does not visually intrude
     * IMPORTANCE_LOW: shows everywhere, but is not intrusive
     * IMPORTANCE_MIN: only shows in the shade, below the fold
     * IMPORTANCE_NONE: a notification with no importance; does not show in the shade
     *
     * @return Importance
     */
    private static int getChannelImportance() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return NotificationManager.IMPORTANCE_HIGH;
        } else {
            return 0;
        }
    }

    /**
     * Set whether or not the notification should bypass Do Not Disturb mode (the INTERRUPTION_FILTER_PRIORITY value
     *
     * @param notificationChannel
     * @return true or false
     */
    private static boolean getBypassDND(NotificationChannel notificationChannel) {
        boolean canBypass = getCanBypassDND(notificationChannel);
        return false;
    }

    private static boolean getCanBypassDND(NotificationChannel notificationChannel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return notificationChannel.canBypassDnd();
        } else {
            return false;
        }
    }

    private static int getChannelLockscreenVisibility() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return android.app.Notification.VISIBILITY_PUBLIC;
        } else {
            return 0;
        }
    }

    private static AudioAttributes getAudioAttributes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
        } else {
            return null;
        }
    }

    //==============================================================================================
    //
    // ** Utils
    //
    //==============================================================================================

    private static int getColor() {
        return Color.parseColor("#ed7b00");
    }

    private static Bitmap getLargeIcon(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_logo);
    }

    private static int getIcon(Context context) {
        return R.drawable.ic_notification;
    }

    private static PendingIntent getPendingIntent(Context context, Intent intentAnotherApp) {
        Intent intent = intentAnotherApp;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    private static long[] getVibration() {
        return new long[]{0, 500, 110, 500, 110};
    }

    private static int getRamdomID() {
        Random rand = new Random();
        return rand.nextInt(1000) + 500;
    }

    //==============================================================================================
    //
    // ** NotificationSend Style
    //
    //==============================================================================================

    //getStyle BigText
    private static NotificationCompat.BigTextStyle getBigTextStyle(String mMessage) {
        return new NotificationCompat.BigTextStyle()
                .bigText(mMessage);
    }

    //==============================================================================================
    //
    // ** Get Packarge from another app
    //
    //==============================================================================================

    public static Intent createIntentFromAotherAPP(Context context, String anotherAppPackageManifest) {
        PackageManager pm = context.getPackageManager();
        Intent LaunchIntent = null;
        String apppack = anotherAppPackageManifest;
        String name = "";
        try {
            if (pm != null) {
                ApplicationInfo app = context.getPackageManager().getApplicationInfo(apppack, 0);
                name = (String) pm.getApplicationLabel(app);
                LaunchIntent = pm.getLaunchIntentForPackage(apppack);
//                LaunchIntent.setAction("teste");
//                LaunchIntent.putExtra("ll", "testes notification 2");
            }

            Log.e("Found it:", name);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return LaunchIntent;
    }
}
