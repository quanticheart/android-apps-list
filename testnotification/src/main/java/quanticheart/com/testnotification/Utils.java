package quanticheart.com.testnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Utils {

    public static void createMyNotification(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent LaunchIntent = null;
        String apppack = "br.com.appfactory.movida";
        String name = "";
        try {
            if (pm != null) {
                ApplicationInfo app = context.getPackageManager().getApplicationInfo(apppack, 0);
                name = (String) pm.getApplicationLabel(app);
                LaunchIntent = pm.getLaunchIntentForPackage(apppack);
                LaunchIntent.setAction("teste");
                LaunchIntent.putExtra("ll", "testes notification 2");
            }

            Log.e("Found it:", name);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        PendingIntent pIntent = PendingIntent.getActivity(context, 0, LaunchIntent, 0);
        Notification noti = new Notification.Builder(context)
                .setTicker("TESTES")
                .setContentTitle("TESTE")
                .setContentText("TESTES")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pIntent).getNotification();
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }
}