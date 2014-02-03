package tenflod.cartracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView list;
	Vehicle[] vehicles;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        vehicles = new Vehicle[4];
        vehicles[0] = new Vehicle("Lancelot", 2006, "Mitsubishi", "Lancer", R.drawable.lancer);
        vehicles[1] = new Vehicle("Green Machine", 2000, "Mercury", "Sable", R.drawable.sable);
        vehicles[2] = new Vehicle("Red Fusion", 2007, "Ford", "Fusion", R.drawable.fusion);
        vehicles[3] = new Vehicle("Tracer", 1998, "Mercury", "Tracer", R.drawable.tracer);
        
        
        CustomList adapter = new CustomList(MainActivity.this, vehicles);
        
        list=(ListView)findViewById(R.id.list);
	        list.setAdapter(adapter);
	        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                                    int position, long id) {
	                Toast.makeText(MainActivity.this, "You Clicked at " +vehicles[+ position], Toast.LENGTH_SHORT).show();
	            }
	        });
    }
    
    public void addVehicleClick(View view) {
    	Intent intent = new Intent(MainActivity.this, AddVehicleActivity.class);
    	MainActivity.this.startActivity(intent);
    }
}
