package hu.uniobuda.nik.guideme;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tothb on 2017. 04. 25..
 */

public class LandscapeListAdapter extends BaseAdapter
{
    List<Monument> items;

    public LandscapeListAdapter(List<Monument> items)
    {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Monument c = items.get(i);
        String nameString = "Name: " + c.getName();
        String dateString = "Built in: " + c.getDate();
        String descString = "Description: " + c.getDescription();
        Bitmap picture = c.getPicture();
        View listItemView = view;

        if(listItemView == null)
            listItemView = View.inflate(parent.getContext(), R.layout.list_monument_l, null);

        TextView textView_monument_name = (TextView) listItemView.findViewById(R.id.textView_landscape_monument_name);
        TextView textView_monument_date = (TextView) listItemView.findViewById(R.id.textView_landscape_monument_date);
        TextView textView_monument_desc = (TextView) listItemView.findViewById(R.id.textView_landscape_monument_desc);
        ImageView imageView_monument_picture = (ImageView) listItemView.findViewById(R.id.imageView_landscape_monument_picture);

        textView_monument_name.setText(nameString);
        textView_monument_date.setText(dateString);
        textView_monument_desc.setText(descString);
        imageView_monument_picture.setImageBitmap(picture);
        return listItemView;
    }

    public void RefreshList(List<Monument> list) {
        this.items = list;
    }
}
