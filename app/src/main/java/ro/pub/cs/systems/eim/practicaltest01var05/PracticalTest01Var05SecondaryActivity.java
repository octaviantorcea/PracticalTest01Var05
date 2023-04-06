package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {

	TextView textView;
	Button verifyButton;
	Button cancelButton;

	class ButtonAction implements View.OnClickListener {

		Button associatedButton;

		public ButtonAction(Button associatedButton) {
			this.associatedButton = associatedButton;
		}

		@Override
		public void onClick(View view) {
			if (associatedButton.getId() == R.id.verifyButton) {
				setResult(RESULT_OK);
				finish();
			} else if (associatedButton.getId() == R.id.cancelButton) {
				setResult(RESULT_CANCELED);
				finish();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_var05_secondary);

		textView = findViewById(R.id.secondaryTextView);
		verifyButton = findViewById(R.id.verifyButton);
		cancelButton = findViewById(R.id.cancelButton);

		Intent intent = getIntent();

		verifyButton.setOnClickListener(new ButtonAction(verifyButton));
		cancelButton.setOnClickListener(new ButtonAction(cancelButton));

		if (intent != null) {
			textView.setText(intent.getStringExtra(Constants.BOTTOM_TEXT_KEY));
		}
	}
}
