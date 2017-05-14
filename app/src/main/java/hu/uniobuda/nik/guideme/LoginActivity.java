package hu.uniobuda.nik.guideme;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {
    Button btn_create, btn_login;
    EditText editName, editPassword;
    DatabaseHelperUser helper;
    TextView textUsername, textPassword;
    public static DatabaseHelperMonument dbh;

    public Location mlocation;
    LocationManager mLocationManager;
    Criteria criteria;

    final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mlocation = location;
            Log.d("Location Changes", location.toString());
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
        setContentView(R.layout.activity_login);

        Button btn_tourist = (Button) findViewById(R.id.btn_tourist);
        final Intent touristActivity = new Intent(this, MainActivity.class);
        helper = new DatabaseHelperUser(this);

        editName = (EditText) findViewById(R.id.userNameBox);
        editPassword = (EditText) findViewById(R.id.passwordBox);

        btn_create = (Button) findViewById(R.id.btn_newuser);
        btn_login = (Button) findViewById(R.id.btn_login);

        textUsername = (TextView) findViewById(R.id.textView_userName);
        textPassword = (TextView) findViewById(R.id.textView_password);

        final Intent userActivity = new Intent(this, SiqnUpActivity.class);
        final Intent displayActivity = new Intent(this, DisplayActivity.class);

        //Create new dataBase object
        dbh = new DatabaseHelperMonument(LoginActivity.this);

        btn_tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                //Context context = getCont Context;

                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
                //}

                mLocationManager.requestSingleUpdate(criteria, locationListener, looper);

                startActivity(touristActivity);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(userActivity);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editName.getText().toString().matches("") && !editPassword.getText().toString().matches("")) {
                    if (helper.Search(editName.getText().toString()).equals(editPassword.getText().toString())) {
                        displayActivity.putExtra("UserName", editName.getText().toString());


                        editName.setVisibility(View.GONE);
                        textUsername.setVisibility(View.GONE);
                        editPassword.setVisibility(View.GONE);
                        textPassword.setVisibility(View.GONE);
                        //btn_login.setVisibility(View.GONE);
                        btn_create.setVisibility(View.GONE);
                        startActivity(displayActivity);
                    } else {
                        Toast.makeText(LoginActivity.this, "Username or password not correct !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill username and password fields!", Toast.LENGTH_LONG).show();
                }

            }
        });
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

