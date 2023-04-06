package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

	Button topLeftButton;
	Button topRightButton;
	Button centerButton;
	Button bottomLeftButton;
	Button bottomRightButton;
	Button nextActivityButton;

	TextView bottomText;

	int totalPresses;

	BroadcastListener broadcastListener = new BroadcastListener();

	class BasicButtonAction implements View.OnClickListener {

		Button associatedButton;

		public BasicButtonAction(Button associatedButton) {
			this.associatedButton = associatedButton;
		}

		@Override
		public void onClick(View view) {
			if (bottomText.getText().toString().length() == 0) {
				bottomText.setText(associatedButton.getText());
			} else {
				bottomText.setText(bottomText.getText().toString()
						+ ","
						+ associatedButton.getText().toString());
			}

			totalPresses++;

			if (totalPresses == Constants.SERVICE_START_THRESHOLD) {
				Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);

				intent.putExtra(Constants.SERVICE_TEXT_KEY, bottomText.getText().toString());

				startService(intent);
			}
		}
	}

	class NextActivityAction implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
			intent.putExtra(Constants.BOTTOM_TEXT_KEY, bottomText.getText().toString());

			startActivityForResult(intent, Constants.NEXT_ACTIVITY_INTENT_CODE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if (requestCode == Constants.NEXT_ACTIVITY_INTENT_CODE) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "Verified", Toast.LENGTH_SHORT).show();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
			}

			bottomText.setText("");
			totalPresses = 0;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			totalPresses = savedInstanceState.getInt(Constants.TOTAL_PRESSES_KEY);
		}

		setContentView(R.layout.activity_practical_test01_var05_main);

		topLeftButton = findViewById(R.id.topLeftButton);
		topRightButton = findViewById(R.id.topRightButton);
		centerButton = findViewById(R.id.centerButton);
		bottomLeftButton = findViewById(R.id.bottomLeftButton);
		bottomRightButton = findViewById(R.id.bottomRightButton);
		nextActivityButton = findViewById(R.id.nextActivityButton);
		bottomText = findViewById(R.id.bottomText);

		topLeftButton.setOnClickListener(new BasicButtonAction(topLeftButton));
		topRightButton.setOnClickListener(new BasicButtonAction(topRightButton));
		centerButton.setOnClickListener(new BasicButtonAction(centerButton));
		bottomLeftButton.setOnClickListener(new BasicButtonAction(bottomLeftButton));
		bottomRightButton.setOnClickListener(new BasicButtonAction(bottomRightButton));
		nextActivityButton.setOnClickListener(new NextActivityAction());

		Toast.makeText(this, String.valueOf(totalPresses), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(Constants.TOTAL_PRESSES_KEY, totalPresses);
	}

	@Override
	protected void onDestroy() {
		stopService(new Intent(getApplicationContext(), PracticalTest01Var05Service.class));
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.BROADCAST_ACTION);

		registerReceiver(broadcastListener, intentFilter);
	}

	@Override
	protected void onPause() {
		unregisterReceiver(broadcastListener);
		super.onPause();
	}
}
