package tenflod.cartracker;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class AddVehicleActivity extends Activity {
	
	private Uri mImageCaptureUri;
	private ImageView mImageView;	

	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vehicle);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		final String [] items			= new String [] {"From Camera", "From SD Card"};				
		ArrayAdapter<String> adapter	= new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
		AlertDialog.Builder builder		= new AlertDialog.Builder(this);

		builder.setTitle("Select Image");
		builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int item ) {
				if (item == 0) {
					Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File file		 = new File(Environment.getExternalStorageDirectory(),
							   			"tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
					mImageCaptureUri = Uri.fromFile(file);

					try {			
						intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
						intent.putExtra("return-data", true);

						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (Exception e) {
						e.printStackTrace();
					}			

					dialog.cancel();
				} else {
					Intent intent = new Intent();

	                intent.setType("image/*");
	                intent.setAction(Intent.ACTION_GET_CONTENT);

	                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}
			}
		} );

		final AlertDialog dialog = builder.create();

		mImageView = (ImageView) findViewById(R.id.img);

		((Button) findViewById(R.id.imagePickerBtn)).setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode != RESULT_OK) 
	    	return;

		switch (requestCode) {
			case PICK_FROM_CAMERA:				
				pickFromCamera();
				break;
			case PICK_FROM_FILE:
				pickFromFile(data.getData());
				
				break;
			default:
				return;
		}	
	}
	
	public void pickFromCamera() {
		String path	= mImageCaptureUri.getPath();
		Bitmap bitmap  = decodeSampledBitmapFromResource(path, Vehicle.MAX_THUMBNAIL_DIMENSION, Vehicle.MAX_THUMBNAIL_DIMENSION);
		
		mImageView.setImageBitmap(bitmap);
	}
	
	public void pickFromFile(Uri data) {
		String path = getRealPathFromURI(mImageCaptureUri); //from Gallery
		Bitmap bitmap 	= null;		

		if (path == null)
			path = mImageCaptureUri.getPath(); //from File Manager

		if (path != null) 
			bitmap 	= decodeSampledBitmapFromResource(path, Vehicle.MAX_THUMBNAIL_DIMENSION, Vehicle.MAX_THUMBNAIL_DIMENSION);
		
		mImageView.setImageBitmap(bitmap);	
	}
	
	public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(path, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}

	public String getRealPathFromURI(Uri contentUri) {
	    String res = null;
	    String[] proj = { MediaStore.Images.Media.DATA };
	    Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
	    if(cursor.moveToFirst()){;
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       res = cursor.getString(column_index);
	    }
	    cursor.close();
	    return res;
	}

	public void saveVehicleClick(View view) {
		EditText nicknameTxt = (EditText) findViewById(R.id.nicknameTxt);

		// Nickname cannot be empty.
		if (nicknameTxt.getText().toString().matches("")) {
			// Error message.
			Crouton.makeText(AddVehicleActivity.this, "Please give this vehicle a nickname.", Style.ALERT).show();
			// Set focus on nickname EditText.
			nicknameTxt.requestFocus();
			// Show keyboard.
			InputMethodManager imm = (InputMethodManager) AddVehicleActivity.this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null) {
				// only will trigger it if no physical keyboard is open
				imm.showSoftInput(nicknameTxt, 0);
			}
		} else {
			EditText yearTxt = (EditText) findViewById(R.id.yearTxt);
			EditText makeTxt = (EditText) findViewById(R.id.makeTxt);
			EditText modelTxt = (EditText) findViewById(R.id.modelTxt);
			
			Toast.makeText(AddVehicleActivity.this, "Vehicle added.", Toast.LENGTH_SHORT).show();
			
			String nickname = nicknameTxt.getText().toString();
			Integer year = null;
			if (yearTxt.getText().toString().length() > 0) {
				year = Integer.parseInt(yearTxt.getText().toString());
			}
			String make = makeTxt.getText().toString();
			String model = modelTxt.getText().toString();
			
			Vehicle vehicle = new Vehicle(nickname, year, make, model);
			vehicle.save(AddVehicleActivity.this.getApplicationContext());
			
			AddVehicleActivity.this.setResult(RESULT_OK);
			AddVehicleActivity.this.finish();
		}
	}
}
