package ro.pub.cs.systems.eim.practicaltest01var05;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var05Service extends Service {
	ProcessingThread processingThread;

	public PracticalTest01Var05Service() {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		processingThread = new ProcessingThread(getApplicationContext(), intent.getStringExtra(Constants.SERVICE_TEXT_KEY));
		processingThread.start();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onDestroy() {
		processingThread.stopThread();
		super.onDestroy();
	}
}