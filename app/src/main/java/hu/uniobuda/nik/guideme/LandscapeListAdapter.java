package hu.uniobuda.nik.guideme;

import android.graphics.Bitmap;
import android.media.Rating;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import hu.uniobuda.nik.guideme.Models.Monument;
import static java.security.AccessController.getContext;

/**
 * Created by tothb on 2017. 04. 25..
 */

public class LandscapeListAdapter extends BaseAdapter
{
    List<Monument> items;
    int j;

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
        final Monument c = items.get(i);
        j = i;
        String nameString = "Name: " + c.getName();
        String dateString = "Built in: " + c.getDate();
        String descString = "Description: " + c.getDescription();
        String rating = "Rating: " + c.getRate();
        Bitmap picture = c.getPicture();
        View listItemView = view;

        if(listItemView == null)
            listItemView = View.inflate(parent.getContext(), R.layout.list_monument_l, null);

        TextView textView_monument_name = (TextView) listItemView.findViewById(R.id.textView_landscape_monument_name);
        final TextView textView_monument_date = (TextView) listItemView.findViewById(R.id.textView_landscape_monument_date);
        TextView textView_monument_desc = (TextView) listItemView.findViewById(R.id.textView_landscape_monument_desc);
        TextView textView_monument_rating = (TextView) listItemView.findViewById(R.id.textView_landscape_monument_rate);
        ImageView imageView_monument_picture = (ImageView) listItemView.findViewById(R.id.imageView_landscape_monument_picture);
        RatingBar ratingBar = (RatingBar) listItemView.findViewById(R.id.ratingBar_landscape_monument_rating);

        textView_monument_name.setText(nameString);
        textView_monument_date.setText(dateString);
        textView_monument_desc.setText(descString);
        textView_monument_rating.setText(rating);
        imageView_monument_picture.setImageBitmap(picture);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                c.setPoints(c.getPoints() + rating);
                c.setVotes(c.getVotes() + 1);
                LoginActivity.dbh.RefreshRating(c.getName(),c.getPoints(),c.getVotes());
                LandscapeFragment_main.RefreshList();
            }
        });


        return listItemView;
    }

    public void RefreshList(List<Monument> list) {
        this.items = list;
    }
}
