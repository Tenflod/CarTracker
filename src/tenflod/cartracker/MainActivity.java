package tenflod.cartracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {
	public static final String VEHICLE_DETAILS_POSITION = "vehicle details id";
	
	static final int ADD_VEHICLE_REQUEST = 1;
	static final int SETTINGS_REQUEST = 2;

	ListView list;
	Vehicle[] vehicles;
	CustomList adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getVehicleList();
	}
	
	private void getVehicleList() {
		vehicles = Vehicle.getAll(getApplicationContext());

		adapter = new CustomList(this, vehicles);

		list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MainActivity.this, VehicleActivity.class);
				intent.putExtra(VEHICLE_DETAILS_POSITION, position);
				startActivity(intent);
			}
		});
		
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void addVehicleClick(View view) {
		Intent intent = new Intent(this, AddVehicleActivity.class);
		startActivityForResult(intent, ADD_VEHICLE_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == ADD_VEHICLE_REQUEST) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				getVehicleList();
			}
		} else if (resultCode == SETTINGS_REQUEST) {
			getVehicleList();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case R.id.action_settings:
				openSettings();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void openSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivityForResult(intent, SETTINGS_REQUEST);
	}
}
