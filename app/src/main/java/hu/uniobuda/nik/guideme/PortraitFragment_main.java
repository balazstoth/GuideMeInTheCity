package hu.uniobuda.nik.guideme;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tothb on 2017. 04. 09..
 */

public class PortraitFragment_main extends Fragment
{
    List<Category> categories_enum = Arrays.asList(Category.values());
    List<String> categories_string = new ArrayList<String>();
    static ListView elements;
    static String selectedCategory;
    public static DatabaseHelper dbh;
    static ListAdapter ListViewAdapter;
    Spinner spnr_category;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.main_portrait,container,false);
        CopyCategories();
        Spinner spinnerCategory = (Spinner) v.findViewById(R.id.spinner_category_p);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories_string);
        spinnerCategory.setAdapter(spinnerAdapter);

        spnr_category = (Spinner)v.findViewById(R.id.spinner_category_p);
        elements = (ListView)v.findViewById(R.id.listView_items_p);
        Button btn_add = (Button)v.findViewById(R.id.imageButton_add_p);
        final Intent addIntent_p = new Intent(getActivity(),AddActivity.class);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = spnr_category.getSelectedItem().toString();
                startActivity(addIntent_p);
            }
        });

        dbh = new DatabaseHelper(getActivity());

        ListViewAdapter = new ListAdapter(dbh.List(spnr_category.getSelectedItem().toString()));
        elements.setAdapter(ListViewAdapter);

        return v;
    }

    private void CopyCategories()
    {
        for(int i = 0; i < categories_enum.size(); i++)
            categories_string.add(categories_enum.get(i).toString());
    }

}
