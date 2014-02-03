package tenflod.cartracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class AddVehicleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vehicle);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_vehicle, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void saveVehicleClick(View view) {

		EditText nicknameTxt = (EditText) findViewById(R.id.nicknameTxt);

		// Nickname cannot be empty.
		if (nicknameTxt.getText().toString().matches("")) {
			// Error message.
			Crouton.makeText(AddVehicleActivity.this,
					"Please give this vehicle a nickname.", Style.ALERT).show();
			// Set focus on nickname EditText.
			nicknameTxt.requestFocus();
			// Show keyboard.
			InputMethodManager imm = (InputMethodManager) AddVehicleActivity.this
					.getApplicationContext().getSystemService(
							Context.INPUT_METHOD_SERVICE);
			if (imm != null) {
				// only will trigger it if no physical keyboard is open
				imm.showSoftInput(nicknameTxt, 0);
			}
		} else {
			Toast.makeText(AddVehicleActivity.this, "Vehicle added.",
					Toast.LENGTH_SHORT).show();
			AddVehicleActivity.this.finish();
		}
	}
}
