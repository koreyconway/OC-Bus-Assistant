package org.sgnexus.octranspoassistant;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String OC_TRANSPO_PHONE = "560-560";
	private static final int BUS_STOP_CODE_MAX_LENGTH = 4;
	private EditText mTxtStopCode;
	private EditText mTxtBusRoute;
	private RadioGroup mRdgDirection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTxtStopCode = (EditText) this.findViewById(R.id.txtStopCode);
		mTxtBusRoute = (EditText) this.findViewById(R.id.txtRouteNumber);
		mRdgDirection = (RadioGroup) this.findViewById(R.id.rdgDirection);

		mTxtStopCode.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if ((BUS_STOP_CODE_MAX_LENGTH - 1) == start && 1 == count) {
					mTxtBusRoute.requestFocus();
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void hideKeyboard() {
		if (getWindow() != null && getWindow().getDecorView() != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getWindow().getDecorView()
					.getWindowToken(), 0);
		}
	}

	public void onClick(View v) {
		sendSMS(mTxtStopCode.getText().toString(), mTxtBusRoute.getText()
				.toString(), getDirection());
	}

	private String getDirection() {
		int checkedId = mRdgDirection.getCheckedRadioButtonId();
		switch (checkedId) {
		case R.id.rdDirection1:
			return "1";
		case R.id.rdDirection2:
			return "2";
		default:
			return "";
		}
	}

	private void sendSMS(String stopCode, String busCode, String direction) {
		String message = (stopCode + " " + busCode + " " + direction).trim();

		if (message.length() > 0) {
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(OC_TRANSPO_PHONE, null, message, null, null);
			Log.d("test", "message sent");
			Toast.makeText(this, "Sent: " + message, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Nothing to send", Toast.LENGTH_SHORT).show();
		}
	}

}
