package tenflod.cartacker.data;

import tenflod.cartacker.data.VehicleContract.VehicleEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VehicleDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Vehicles.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + VehicleEntry.TABLE_NAME + " (" +
        		VehicleEntry._ID + " INTEGER PRIMARY KEY," +
        		VehicleEntry.COLUMN_NAME_NICKNAME + TEXT_TYPE + COMMA_SEP +
        		VehicleEntry.COLUMN_NAME_MAKE + TEXT_TYPE + COMMA_SEP +
        		VehicleEntry.COLUMN_NAME_MODEL + TEXT_TYPE + COMMA_SEP +
        		VehicleEntry.COLUMN_NAME_YEAR + TEXT_TYPE + COMMA_SEP +
        " )";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + VehicleEntry.TABLE_NAME;

    public VehicleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
