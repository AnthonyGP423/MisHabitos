package com.sise.mishabitos.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import com.sise.mishabitos.R;
import com.sise.mishabitos.utils.NotificationUtils;
public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "HABITO_CHANNEL_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        String titulo = intent.getStringExtra("titulo");
        String mensaje = intent.getStringExtra("mensaje");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // ✅ Mostramos la notificación usando NotificationUtils
        NotificationUtils.mostrarNotificacion(context, titulo, mensaje);

        // Opcional: También mostrar un Toast (puedes quitarlo si quieres)
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();


        //  canal (Android 8+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notificaciones de Hábitos",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(titulo != null ? titulo : "¡Hora de tu hábito!")
                .setContentText(mensaje != null ? mensaje : "No olvides registrar tu progreso.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
