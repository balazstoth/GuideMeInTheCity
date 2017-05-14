package hu.uniobuda.nik.guideme;

/**
 * Created by tothb on 2017. 04. 09..
 */

import android.Manifest;
import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.uniobuda.nik.guideme.Models.Category;


public class LandscapeFragment_main extends Fragment
{
    List<Category> categories_enum = Arrays.asList(Category.values());
    List<String> categories_string = new ArrayList<String>();
    ListView categories;
    TextView textView_selectedCategory;

    static ListView elements;
    static String selectedCategory;
    static LandscapeListAdapter ListViewAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Looper looper = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                //return;
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

        View v = inflater.inflate(R.layout.main_landscape, container, false);
        textView_selectedCategory = (TextView) v.findViewById(R.id.textView_selectedCategory2_l);

        //Set categories
        CopyCategories();
        categories = (ListView) v.findViewById(R.id.listView_category_l);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories_string);
        categories.setAdapter(listViewAdapter);
        selectedCategory = categories_string.get(0);
        categories.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
               //Toast.makeText(getActivity(),categories_string.get(position),Toast.LENGTH_SHORT).show();
                selectedCategory = categories_string.get(position);
                textView_selectedCategory.setText(selectedCategory);
                ListViewAdapter.RefreshList(LoginActivity.dbh.List(selectedCategory));
                ListViewAdapter.notifyDataSetChanged();
            }
        });

        //Set 'add' button
        Button btn_add = (Button)v.findViewById(R.id.imageButton_add_l);
        final Intent addIntent_l = new Intent(getActivity(),AddActivity.class);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectedCategory = categories.getSelectedItem().toString();
                startActivity(addIntent_l);
            }
        });


        //Select the appropiate items to the list
        ListViewAdapter = new LandscapeListAdapter(LoginActivity.dbh.List(selectedCategory));
        ListViewAdapter.notifyDataSetChanged();
        elements = (ListView)v.findViewById(R.id.listView_items_l);
        elements.setAdapter(ListViewAdapter);

        //Set selected category label
        textView_selectedCategory.setText(selectedCategory);

        return v;
    }

    public static void RefreshList()
    {
        ListViewAdapter.RefreshList(LoginActivity.dbh.List(selectedCategory));
        ListViewAdapter.notifyDataSetChanged();
    }

    private void CopyCategories()
    {
        for(int i = 0; i < categories_enum.size(); i++)
            categories_string.add(categories_enum.get(i).toString());
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
