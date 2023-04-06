package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.Context;
import android.content.Intent;

public class ProcessingThread extends Thread {
	Context context;
	String textToSend;
	String[] parsedText;
	Boolean isRunning = false;

	public ProcessingThread(Context context, String textToSend) {
		this.context = context;
		this.textToSend = textToSend;
		this.isRunning = true;

		parsedText = textToSend.split(",");
	}

	@Override
	public void run() {
		while (isRunning) {
			for (String s : parsedText) {
				Intent intent = new Intent();

				intent.setAction(Constants.BROADCAST_ACTION);
				intent.putExtra(Constants.JUST_ONE_BUTTON, s);

				context.sendBroadcast(intent);

				try {
					sleep(5000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void stopThread() {
		this.isRunning = false;
	}
}
