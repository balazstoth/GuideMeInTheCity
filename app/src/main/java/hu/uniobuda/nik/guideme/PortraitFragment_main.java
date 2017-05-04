package hu.uniobuda.nik.guideme;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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
    //public static DatabaseHelperMonument dbh;
    static PortraitListAdapter ListViewAdapter;
    Spinner spinnerCategory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.main_portrait,container,false);

        //Set the spinner
        CopyCategories();
        spinnerCategory = (Spinner) v.findViewById(R.id.spinner_category_p);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories_string);
        spinnerCategory.setAdapter(spinnerAdapter);

        //Set OnItemSelected event for spinner
        selectedCategory = spinnerCategory.getSelectedItem().toString();
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedCategory = spinnerCategory.getSelectedItem().toString();
                ListViewAdapter.RefreshList(LoginActivity.dbh.List(selectedCategory));
                ListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Set 'add' button
        Button btn_add = (Button)v.findViewById(R.id.imageButton_add_p);
        final Intent addIntent_p = new Intent(getActivity(),AddActivity.class);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = spinnerCategory.getSelectedItem().toString();
                startActivity(addIntent_p);
            }
        });

        //Create new dataBase object
        //dbh = new DatabaseHelperMonument(getActivity());

        //Select the appropiate items to the list
        ListViewAdapter = new PortraitListAdapter(LoginActivity.dbh.List(spinnerCategory.getSelectedItem().toString()));
        ListViewAdapter.notifyDataSetChanged();
        elements = (ListView)v.findViewById(R.id.listView_items_p);
        elements.setAdapter(ListViewAdapter);

        return v;
    }

    private void CopyCategories()
    {
        for(int i = 0; i < categories_enum.size(); i++)
            categories_string.add(categories_enum.get(i).toString());
    }

}
