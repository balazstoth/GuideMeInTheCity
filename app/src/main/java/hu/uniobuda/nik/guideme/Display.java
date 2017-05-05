package hu.uniobuda.nik.guideme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Tamás on 2017. 04. 22..
 */

public class Display extends Activity {
    String username;
    DatabaseHelperMonument helper;
    Cursor cursor;
    List<Monument> monumentList;
    ListView listView;
    ArrayAdapter<Monument> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        QueryMonuments();

        username = getIntent().getStringExtra("UserName");
        TextView tv =(TextView) findViewById(R.id.userName);
        tv.setText(username);
    }

    public List<String> toList(List<Monument> list)
    {
        List<String> retList = new ArrayList<String>();
        for(int i=0;i<list.size();i++)
        {
            retList.add("Name: " + list.get(i).getName() + "\n" + "Built in: " + list.get(i).getDate() + ", Rate: " + list.get(i).getRate());
        }

        return retList;
    }

    public void QueryMonuments()
    {
        helper = new DatabaseHelperMonument(this);
        cursor = helper.getReadableDatabase().rawQuery("select * from Monuments", null);
        monumentList = new ArrayList<Monument>();
        while (cursor.moveToNext())
        {
            monumentList.add(new Monument(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7), BitmapConvert.fromBytesToImage(cursor.getBlob(8))));
        }

        adapter = new ArrayAdapter<Monument>(this, android.R.layout.simple_list_item_1, monumentList);
        listView =(ListView) findViewById(R.id.monumentsListView);
        listView.setAdapter(adapter);

    }

    public void AcceptDecline(final View view) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Would you like to add this monument ?")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor cursor = helper.getReadableDatabase().rawQuery("select ID FROM "+helper.getDatabaseName()+
                                "WHERE ", null);

                        String update = "UPDATE "+helper.getDatabaseName()+" SET isEnabled = ok WHERE";
                        helper.getWritableDatabase().execSQL(update);
                    }
                })
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .create();
        myAlert.show();
    }
}
