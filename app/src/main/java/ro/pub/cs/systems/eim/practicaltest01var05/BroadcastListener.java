package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastListener extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("BROADCAST RECEIVED", intent.getStringExtra(Constants.JUST_ONE_BUTTON));
	}
}
