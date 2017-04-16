package hu.uniobuda.nik.guideme;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
    EditText etxt_name;
    List<Category> categories_enum = Arrays.asList(Category.values());
    List<String> categories_string = new ArrayList<String>();
    ImageView iv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.add_portrait,container,false);
        iv = (ImageView)v.findViewById(R.id.imageView4);
        CopyCategories();
        Spinner spinnerCategory = (Spinner) v.findViewById(R.id.spinner_category_add_p);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories_string);
        spinnerCategory.setAdapter(spinnerAdapter);

        btn_datePicker = (Button)v.findViewById(R.id.button_date_add_p);
        btn_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


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
                startActivityForResult(i, 1);

               /* Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);*/
            }
        });


        etxt_name = (EditText) v.findViewById(R.id.EditText_name_add_p);
        btn_add = (Button)v.findViewById(R.id.button_add_add_p);
        btn_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(etxt_name.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"Name field cannot be empty!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        Toast.makeText(getActivity(),"Count: " + PortraitFragment_main.dbh.Count(),Toast.LENGTH_SHORT).show();
                        PortraitFragment_main.dbh.Insert(etxt_name.getText().toString());

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


    public void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, _year, _month, _day);
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
        /*if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri,projection,null,null,null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(projection[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                Drawable d = new BitmapDrawable(yourSelectedImage);
                iv.setBackground(d);
            }
        }*/


        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(picturePath);
            Drawable d = new BitmapDrawable(yourSelectedImage);
            iv.setBackground(d);
            cursor.close();

        }
    }
}
