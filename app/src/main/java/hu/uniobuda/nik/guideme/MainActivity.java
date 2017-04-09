package hu.uniobuda.nik.guideme;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity
{
    List<Category> categories_enum = Arrays.asList(Category.values());
    List<String> categories_string = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CopyCategories();
        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, categories_string);
        spinnerCategory.setAdapter(spinnerAdapter);
    }

    private void CopyCategories()
    {
        for(int i = 0; i < categories_enum.size(); i++)
            categories_string.add(categories_enum.get(i).toString());
    }
}
