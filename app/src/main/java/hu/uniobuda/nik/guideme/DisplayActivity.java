package hu.uniobuda.nik.guideme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.uniobuda.nik.guideme.Models.Monument;

/**
 * Created by Tamás on 2017. 04. 22..
 */

public class DisplayActivity extends Activity {
    String username;
    DatabaseHelperMonument helper;
    Cursor cursor;
    List<Monument> monumentList;
    ListView listView;
    ArrayAdapter<Monument> adapter;
    Bundle actbundle;
    Monument actMonument;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        actbundle = savedInstanceState;
        QueryMonuments();

        username = getIntent().getStringExtra("UserName");
        TextView tv =(TextView) findViewById(R.id.userName);
        tv.setText(username);
    }

    /*public List<String> toList(List<Monument> list)
    {
        List<String> retList = new ArrayList<String>();
        for(int i=0;i<list.size();i++)
        {
            retList.add("Name: " + list.get(i).getName() + "\n" + "Built in: " + list.get(i).getDate() + ", Description: " + list.get(i).getDescription());
        }

        return retList;
    }*/

    public void QueryMonuments()
    {
        helper = new DatabaseHelperMonument(this);
        cursor = helper.getReadableDatabase().rawQuery("select * from Monuments where ISENABLED == 'false'", null);
        monumentList = new ArrayList<Monument>();
        while (cursor.moveToNext())
        {
            monumentList.add(new Monument(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7), BitmapConvert.fromBytesToImage(cursor.getBlob(8)), cursor.getDouble(9), cursor.getDouble(10)));
        }

        adapter = new ArrayAdapter<Monument>(this, android.R.layout.simple_list_item_1, monumentList);
        listView =(ListView) findViewById(R.id.monumentsListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AcceptDecline(view, adapter.getItem(position));
            }
        });

    }

    public void AcceptDecline(final View view, final Monument selected) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Would you like to add this monument ?")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    Monument actmon = selected;
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String update = "UPDATE Monuments SET ISENABLED = 'ok' WHERE NAME = '"+actmon.getName()+
                                "' and DATE = '"+actmon.getDate()+"' and CATEGORY = '"+actmon.getCategory()+"' and DESC = '"+actmon.getDescription()+"'" +
                                "and ISENABLED = 'false'";
                        helper.getWritableDatabase().execSQL(update);

                        onCreate(actbundle);
                    }
                })
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }
}
