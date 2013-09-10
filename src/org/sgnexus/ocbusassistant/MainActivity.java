package org.sgnexus.ocbusassistant;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private static final String OC_TRANSPO_PHONE = "560-560";
	private EditText mTxtStopCode;
	private EditText mTxtBusRoute;
	private EditText mTxtDirection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTxtStopCode = (EditText) this.findViewById(R.id.txtStopCode);
		mTxtBusRoute = (EditText) this.findViewById(R.id.txtRouteNumber);
		mTxtDirection = (EditText) this.findViewById(R.id.txtDirection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		sendSMS(mTxtStopCode.getText().toString(), mTxtBusRoute.getText()
				.toString(), mTxtDirection.getText().toString());
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
