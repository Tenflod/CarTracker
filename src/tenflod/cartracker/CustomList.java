package tenflod.cartracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<Vehicle>{
	private final Activity context;
	//private final String[] web;
	//private final Integer[] imageId;
	private final Vehicle[] vehicles;
	
	public CustomList(Activity context, Vehicle[] vehicles) { //	String[] web, Integer[] imageId) {
		super(context, R.layout.list_row, vehicles); //web);
		this.context = context;
		this.vehicles = vehicles;
		//this.web = web;
		//this.imageId = imageId;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		
		View rowView= inflater.inflate(R.layout.list_row, null, true);
		TextView txtNickname = (TextView) rowView.findViewById(R.id.txtNickname);
		TextView fullName = (TextView) rowView.findViewById(R.id.txtFullName);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		
		txtNickname.setText(vehicles[position].nickname);
		fullName.setText(vehicles[position].getFullName());
		imageView.setImageResource(vehicles[position].imageId);
		
		return rowView;
	}
}