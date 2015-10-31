package com.beconnected;

import com.beconnected.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GcmIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private static final String TAG = "GcmIntentService";
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

private  Typeface cFont;
	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// This loop represents the service doing some work.
				for (int i = 0; i < 5; i++) {
					Log.i(TAG,
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				sendNotification(extras.getString("Notice"));

				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(String msg) {

		boolean Promo = false;

		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		if (msg.contains("promo")) {
			Promo = true;
		}

		Intent notificationIntent = new Intent(getApplicationContext(),
				SplashActivity.class);
		//Bundle extras = new Bundle();
		//extras.putBoolean("PROMO", Promo);
		//extras.putString(key, value);
		//notificationIntent.putExtras(extras);
		notificationIntent.putExtra("PROMO", Promo);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		// new Intent(this, TabsUsuario.class), 0);

		// PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
		// new Intent(this, TabsUsuario.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				// .setSmallIcon(R.drawable.ic_stat_gcm)
				.setContentTitle("Be Connected")
				.setSmallIcon(R.drawable.ic_logo_bc)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setAutoCancel(true).setSound(soundUri).setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(0, mBuilder.build());

	}
}