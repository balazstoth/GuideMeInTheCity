package hu.uniobuda.nik.guideme;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import hu.uniobuda.nik.guideme.Models.Category;

public class AddActivity extends Activity
{
    int _year, _month,_day;
    Button btn_datePicker;
    Button btn_camera;
    Button btn_gallery;
    Button btn_add;
    EditText editText_name;
    EditText editText_desc;
    Spinner spnr_category;
    List<Category> categories_enum = Arrays.asList(Category.values());
    List<String> categories_string = new ArrayList<String>();
    ImageView iv;
    Bitmap picture;

    Location mlocation;
    LocationManager mLocationManager;
    Criteria criteria;

    final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mlocation = location;
            Log.d("Location Changes", location.toString());
            //latitude.setText(String.valueOf(location.getLatitude()));
            //longitude.setText(String.valueOf(location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Status Changed", String.valueOf(status));
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Provider Enabled", provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Provider Disabled", provider);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        iv = (ImageView)findViewById(R.id.imageView_add_p);

        //Set the spinner
        CopyCategories();
        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinner_category_add_p);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories_string);
        spinnerCategory.setAdapter(spinnerAdapter);

        //Date Picker button
        btn_datePicker = (Button)findViewById(R.id.button_date_add_p);
        btn_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //Camera button
        btn_camera = (Button)findViewById(R.id.button_photo_take_add_p);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
            }
        });


        btn_gallery = (Button)findViewById(R.id.button_photo_select_add_p);
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //i.setType("image*//*");
                startActivityForResult(i, 1);
            }
        });


        //Add new item
        editText_name = (EditText) findViewById(R.id.EditText_name_add_p);
        editText_desc = (EditText) findViewById(R.id.EditText_desc_add_p);
        spnr_category = (Spinner)findViewById(R.id.spinner_category_add_p);
        btn_add = (Button)findViewById(R.id.button_add_add_p);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Looper looper = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            } else {
                mLocationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        }


        if(mLocationManager.isProviderEnabled("gps")){
            mLocationManager.requestSingleUpdate(criteria, locationListener, looper);
        }
        else{
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
        }

        btn_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(FieldControl())
                {
                    try
                    {
                        String date = _year+"."+_month+"."+_day;
                        double latit = mlocation.getLatitude();
                        double longit = mlocation.getLongitude();
                        LoginActivity.dbh.Insert(editText_name.getText().toString(),editText_desc.getText().toString(),date,spnr_category.getSelectedItem().toString(),"0","0","false",BitmapConvert.fromImageToBytes(picture), latit, longit);

                        if(PortraitFragment_main.selectedCategory != null)
                        {
                            PortraitFragment_main.ListViewAdapter.RefreshList(LoginActivity.dbh.List(PortraitFragment_main.selectedCategory));
                            PortraitFragment_main.ListViewAdapter.notifyDataSetChanged();
                        }

                        if (LandscapeFragment_main.selectedCategory != null)
                        {
                            LandscapeFragment_main.ListViewAdapter.RefreshList(LoginActivity.dbh.List(LandscapeFragment_main.selectedCategory));
                            LandscapeFragment_main.ListViewAdapter.notifyDataSetChanged();
                        }

                        Toast.makeText(AddActivity.this,"Item added succesfully!",Toast.LENGTH_SHORT).show();
                    }
                    catch(SQLiteException ex)
                    {
                        Toast.makeText(AddActivity.this,"Item already added!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean FieldControl()
    {
        if(editText_name.getText().toString().equals("") || editText_desc.getText().toString().equals(""))
        {
            Toast.makeText(AddActivity.this,"Field cannot be empty!",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(_year == 0)
        {
            Toast.makeText(AddActivity.this,"Select a date!",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(picture == null)
        {
            Toast.makeText(AddActivity.this,"Select a picture!",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void showDatePickerDialog()
    {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        Calendar c = Calendar.getInstance();
        c.set(0, 0, 1);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            _year = year;
            _month = monthOfYear;
            _day = dayOfMonth;
            btn_datePicker.setText(_year+"/"+(_month+1)+"/"+_day);
        }
    };

    private void CopyCategories()
    {
        for(int i = 0; i < categories_enum.size(); i++)
            categories_string.add(categories_enum.get(i).toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //Camera
        if (requestCode == 0 && resultCode == RESULT_OK && data != null ) {
            if(data.getExtras().get("data") != null)
            {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                iv.setImageBitmap(image);
                picture = image;
            }
        }
        else
        {
            //Album
            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                try
                {
                    InputStream is = getContentResolver().openInputStream(selectedImage);
                    Bitmap image = BitmapFactory.decodeStream(is);
                    iv.setImageBitmap(image);
                    picture = image;
                } catch (FileNotFoundException e)
                {
                    e.getMessage();
                }

               /* String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));*/
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    mLocationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        }
    }
}
