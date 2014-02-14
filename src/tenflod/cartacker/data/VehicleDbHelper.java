package tenflod.cartacker.data;

import tenflod.cartacker.data.VehicleContract.VehicleEntry;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VehicleDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Vehicles.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS " + VehicleEntry.TABLE_NAME + " (" +
        		VehicleEntry._ID + " INTEGER PRIMARY KEY," +
        		VehicleEntry.COLUMN_NAME_NICKNAME + TEXT_TYPE + COMMA_SEP +
        		VehicleEntry.COLUMN_NAME_MAKE + TEXT_TYPE + COMMA_SEP +
        		VehicleEntry.COLUMN_NAME_MODEL + TEXT_TYPE + COMMA_SEP +
        		VehicleEntry.COLUMN_NAME_YEAR + TEXT_TYPE + 
        " )";

    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + VehicleEntry.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES = "DELETE FROM " + VehicleEntry.TABLE_NAME;

    public VehicleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

		SQLiteDatabase db = VehicleDbHelper.this.getReadableDatabase();
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public static Cursor getById(Context context, Integer id) {
    	VehicleDbHelper mDbHelper = new VehicleDbHelper(context);
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		String query = String.format("SELECT * FROM %s WHERE %s = ?", VehicleEntry.TABLE_NAME, VehicleEntry._ID);
		String[] args = new String[] { id.toString() };
		Cursor c = db.rawQuery(query , args);
		
		return c;
    }
    public static Cursor getAll(Context context, String[] projection) {
    	VehicleDbHelper mDbHelper = new VehicleDbHelper(context);
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// How you want the results sorted in the resulting Cursor
		String sortOrder = VehicleEntry.COLUMN_NAME_NICKNAME + " DESC";

		Cursor c = db.query(
				VehicleEntry.TABLE_NAME,  // The table to query
				projection,               // The columns to return
				null,                     // The columns for the WHERE clause
				null,                     // The values for the WHERE clause
				null,                     // don't group the rows
				null,                     // don't filter by row groups
				sortOrder                 // The sort order
				);
		
		return c;
    }
    public static void deleteAll(Context context){
    	VehicleDbHelper mDbHelper = new VehicleDbHelper(context);
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
    	
		db.execSQL(SQL_DELETE_ENTRIES);
    }
}
