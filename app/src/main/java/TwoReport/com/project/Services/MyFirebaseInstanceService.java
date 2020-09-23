package TwoReport.com.project.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import TwoReport.com.project.R;

public class MyFirebaseInstanceService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s); Log.d("TOKENFIREBASE",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("NOTIFICACIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOON");
        System.out.println(remoteMessage.getNotification().getTitle());
        System.out.println(remoteMessage.getNotification().getBody());
        System.out.println("NOTIFICACIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOON");

        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }
    private void showNotification(String title, String body){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"chanel_1")
                .setSmallIcon(R.drawable.ic_report)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }
}

