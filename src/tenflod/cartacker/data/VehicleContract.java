package tenflod.cartacker.data;

import android.provider.BaseColumns;

public class VehicleContract {
	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
	public VehicleContract() {}
	
	/* Inner class that defines the table contents */
    public static abstract class VehicleEntry implements BaseColumns {
        public static final String TABLE_NAME = "vehicles";
        public static final String COLUMN_NAME_NICKNAME = "nickname";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_MAKE = "make";
        public static final String COLUMN_NAME_MODEL = "model";

        public static final String COLUMN_NAME_NULLABLE = "year";
    }
}
