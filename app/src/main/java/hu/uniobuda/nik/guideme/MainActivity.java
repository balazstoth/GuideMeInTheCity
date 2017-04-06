package hu.uniobuda.nik.guideme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> list = Arrays.asList(Category.values().toString());
    String[] t = {"a","b","c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,list.get(0).toString(),Toast.LENGTH_SHORT).show();
        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,t);
        spinnerCategory.setAdapter(spinneradapter);

    }
}
