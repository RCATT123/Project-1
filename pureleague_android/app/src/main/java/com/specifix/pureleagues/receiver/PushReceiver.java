package com.specifix.pureleagues.receiver;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.ParsePushBroadcastReceiver;
import com.specifix.pureleagues.R;
import com.specifix.pureleagues.activity.MainActivity;
import com.specifix.pureleagues.activity.SplashActivity;
import com.specifix.pureleagues.api.UserManager;
import com.specifix.pureleagues.model.PushMessage;
import com.specifix.pureleagues.model.Team;
import com.specifix.pureleagues.model.User;

import java.util.List;

public class PushReceiver extends ParsePushBroadcastReceiver {
    public static final String EVENT_TYPE = "event_type";
    public static final String LIST_POSITION = "list_position";
    public static final String FROM_PUSH = "from_push";
    public static final String PUSH_TEAM_ID = "push_team_id";
    private static final int NOTIFICATION_ID = 4329;

    public static int counter = 0;

    protected void onPushReceive(Context context, Intent intent) {
        PushMessage message = new Gson().fromJson(intent.getStringExtra("com.parse.Data"), PushMessage.class);

        Log.d("Wraith", "Push received " + message.toString());
        boolean showPush = false;
        List<Team> teams = UserManager.getInstance().getCurrentUser().getTeams();
        if (teams != null) {
            for (Team team : teams) {
                if (Long.parseLong(message.getTeamId()) == team.getTeamId()) {
                    showPush = team.isAllowNotifications();
                }
            }
        }

        if (showPush) {
            showNotification(context, message);
        }
    }

    private void showNotification(Context context, PushMessage message) {

        User currentUser = UserManager.getInstance().getCurrentUser();
        if (currentUser == null)
            return;

        if (currentUser.getObjectId().equals(message.getSenderId()))
            return;

        if (UserManager.getInstance().getUserAge() < UserManager.MIN_ALLOWED_AGE)
            return;

//        if (MainActivity.isActive()) {
//            return;
//        }

        counter++;
        String messageText = message.getMessage();
        if (TextUtils.isEmpty(messageText)) {
            messageText = "[New image]";
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(message.getTitle())
                        .setContentText(messageText)
                        .setNumber(counter)
                        .setSound(defaultSoundUri)
                        .setAutoCancel(true);

        Intent intent = new Intent(context, SplashActivity.class);
        if (!TextUtils.isEmpty(message.getEventType())) {
            intent.putExtra(FROM_PUSH, true);
            intent.putExtra(EVENT_TYPE, message.getEventType());
            intent.putExtra(LIST_POSITION, message.getListPosition());
            intent.putExtra(PUSH_TEAM_ID, message.getTeamId());
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SplashActivity.class);

        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }

        return false;
    }
}
