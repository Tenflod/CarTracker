package tenflod.cartracker;

import tenflod.cartacker.data.VehicleContract.VehicleEntry;
import tenflod.cartacker.data.VehicleDbHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Vehicle {
	
	Integer id;
	String nickname;
	Integer year;
	String make;
	String model;
	Integer imageId = 0;
	
	public Vehicle(String nickname, Integer year, String make, String model)//, Integer imageId)
	{
		this.nickname = nickname;
		this.year = year;
		this.make = make;
		this.model = model;
//		this.imageId = imageId;
	}
	
	public Vehicle(Integer id, String nickname, Integer year, String make, String model)//, Integer imageId)
	{
		this.id = id;
		this.nickname = nickname;
		this.year = year;
		this.make = make;
		this.model = model;
//		this.imageId = imageId;
	}
	
	public String getFullName()	{
		if (this.year != 0) {
			return String.format("%s %s %s", year.toString(), make, model);
		}
		else {
			return String.format("%s %s", make, model);
		}
			
	}
	
	public static Vehicle getById(Context context, Integer p_id) {
		Cursor c = VehicleDbHelper.getById(context, p_id);
		c.moveToFirst();
		
		Integer id = c.getInt(0);
		String nickname = c.getString(1);
		Integer year = c.getInt(2);
		String make = c.getString(3);
		String model = c.getString(4);
		
		return new Vehicle(id, nickname, year, make, model);
	}
	
	public static Vehicle[] getAll(Context context)	{
		Integer i = 0;
		
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			VehicleEntry._ID,
		    VehicleEntry.COLUMN_NAME_NICKNAME,
		    VehicleEntry.COLUMN_NAME_YEAR,
		    VehicleEntry.COLUMN_NAME_MAKE,
		    VehicleEntry.COLUMN_NAME_MODEL
		    };
		
		Cursor c = VehicleDbHelper.getAll(context, projection);
		
		Vehicle[] vehicleList = new Vehicle[c.getCount()];
		
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			Integer id = c.getInt(0);
			String nickname = c.getString(1);
			Integer year = c.getInt(2);
			String make = c.getString(3);
			String model = c.getString(4);
			
			Vehicle vehicle = new Vehicle(id, nickname, year, make, model);
			vehicleList[i++] = vehicle;
		    c.moveToNext();
		}
		
		return vehicleList;
	}

	public long save(Context context) {
    	VehicleDbHelper mDbHelper = new VehicleDbHelper(context);
    	
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(VehicleEntry.COLUMN_NAME_NICKNAME, nickname);
		values.put(VehicleEntry.COLUMN_NAME_YEAR, year);
		values.put(VehicleEntry.COLUMN_NAME_MAKE, make);
		values.put(VehicleEntry.COLUMN_NAME_MODEL, model);

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
				VehicleEntry.TABLE_NAME,
				VehicleEntry.COLUMN_NAME_NULLABLE,
		        values);
		
		this.id = (int) newRowId;
		
		return newRowId;
	}
	
	public static void deleteAll(Context context) {
		VehicleDbHelper.deleteAll(context);
	}
}
