package com.beconnected;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class Baterry extends Service {
	private int level;
	private NotificationManager myNotificationManager;
	private int notificationIdOne = 111;
	private boolean battery = true;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		battery();

	}

	public void battery() {
		this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

	}

	private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			// int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
			// int icon_small = intent.getIntExtra(
			// BatteryManager.EXTRA_ICON_SMALL, 0);
			level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			// int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,
			// 0);
			// boolean present = intent.getExtras().getBoolean(
			// BatteryManager.EXTRA_PRESENT);
			// int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
			// int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
			// String technology = intent.getExtras().getString(
			// BatteryManager.EXTRA_TECHNOLOGY);
			// int temperature = intent.getIntExtra(
			// BatteryManager.EXTRA_TEMPERATURE, 0);
			// int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,
			// 0);

			if (level >= 20) {

				displayNotificationOne();
			
				
			}

		}
	};

	protected void displayNotificationOne() {

		// Invoking the default notification service

//		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//				this);
		NotificationManager	mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	//	mBuilder.setContentTitle("ATENCION!!!");
//
	//	mBuilder.setContentText("Atención su bateria esta por debajo del 20%.");
//
	//	mBuilder.setTicker("Busque su centro de recarga mas cercano.");

	//	mBuilder.setSmallIcon(R.drawable.ic_logo_bc);
	//	mBuilder.setSound(soundUri);

		// Increase notification number every time a new notification arrives

		// mBuilder.setNumber(++numMessagesOne);

		// Creates an explicit intent for an Activity in your app

		Intent resultIntent = new Intent(this, TabsUsuario.class);
     
		resultIntent.putExtra("battery", battery);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				resultIntent, 0);
		// This ensures that navigating backward from the Activity leads out of
		// the app to Home page
		
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				// .setSmallIcon(R.drawable.ic_stat_gcm)
				.setContentTitle("Be Connected")
				.setSmallIcon(R.drawable.ic_logo_bc)
				.setStyle(new NotificationCompat.BigTextStyle().bigText("Atención su bateria esta por debajo del 20%."))
				.setAutoCancel(true).setSound(soundUri).setContentText("Busque su centro de recarga mas cercano.");

		mBuilder.setContentIntent(contentIntent);
		mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(0, mBuilder.build());

	//	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

		// Adds the back stack for the Intent

//	stackBuilder.addParentStack(TabsUsuario.class);

		// Adds the Intent that starts the Activity to the top of the stack

//		stackBuilder.addNextIntent(resultIntent);

//		PendingIntent resultPendingIntent =

//		stackBuilder.getPendingIntent(

	//	0,

	//	PendingIntent.FLAG_ONE_SHOT // can only be used once

		//		);

		// start the activity when the user clicks the notification text

	//	mBuilder.setContentIntent(resultPendingIntent);
	//	mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
	//	myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// pass the Notification object to the system

	//	myNotificationManager.notify(notificationIdOne, mBuilder.build());

	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
