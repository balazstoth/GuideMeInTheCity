package hu.uniobuda.nik.guideme;

/**
 * Created by tothb on 2017. 04. 09..
 */

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LandscapeFragment_main extends Fragment
{
    List<Category> categories_enum = Arrays.asList(Category.values());
    List<String> categories_string = new ArrayList<String>();
    ListView categories;
    TextView textView_selectedCategory;

    static ListView elements;
    static String selectedCategory;
    static LandscapeListAdapter ListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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


    private void CopyCategories()
    {
        for(int i = 0; i < categories_enum.size(); i++)
            categories_string.add(categories_enum.get(i).toString());
    }
}
