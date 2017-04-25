package hu.uniobuda.nik.guideme;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by tothb on 2017. 04. 10..
 */

public class PortraitFragment_add extends Fragment
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.add_portrait,container,false);
        iv = (ImageView)v.findViewById(R.id.imageView_add_p);

        //Set the spinner
        CopyCategories();
        Spinner spinnerCategory = (Spinner) v.findViewById(R.id.spinner_category_add_p);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories_string);
        spinnerCategory.setAdapter(spinnerAdapter);

        //Date Picker button
        btn_datePicker = (Button)v.findViewById(R.id.button_date_add_p);
        btn_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //Camera button
        btn_camera = (Button)v.findViewById(R.id.button_photo_take_add_p);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
            }
        });


        btn_gallery = (Button)v.findViewById(R.id.button_photo_select_add_p);
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //i.setType("image*//*");
                startActivityForResult(i, 1);
            }
        });



        //Add new item
        editText_name = (EditText) v.findViewById(R.id.EditText_name_add_p);
        editText_desc = (EditText) v.findViewById(R.id.EditText_desc_add_p);
        spnr_category = (Spinner)v.findViewById(R.id.spinner_category_add_p);
        btn_add = (Button)v.findViewById(R.id.button_add_add_p);
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
                PortraitFragment_main.dbh.Insert(editText_name.getText().toString(),editText_desc.getText().toString(),date,spnr_category.getSelectedItem().toString(),"0","0","false");
                PortraitFragment_main.ListViewAdapter.RefreshList(PortraitFragment_main.dbh.List(PortraitFragment_main.selectedCategory));
                PortraitFragment_main.ListViewAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(),"Item added succesfully!",Toast.LENGTH_SHORT).show();
            }
            catch(SQLiteException ex)
            {
                Toast.makeText(getActivity(),"Item already added!",Toast.LENGTH_SHORT).show();
            }
        }
            }
        });
        return v;
    }
    private boolean FieldControl()
    {
        if(editText_name.getText().toString().equals("") || editText_desc.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Field cannot be empty!",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(_year == 0)
        {
            Toast.makeText(getActivity(),"Select a date!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, 2000, 1, 1);
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
            btn_datePicker.setText(_year+"/"+_month+"/"+_day);
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
            }
        }
        else
        {
            //Album
            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }
}
