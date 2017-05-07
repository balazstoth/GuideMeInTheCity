package hu.uniobuda.nik.guideme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity
{
    TextView name;
    TextView desc;
    TextView year;
    ImageView image;
    Monument selected;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_details);

        name = (TextView)findViewById(R.id.textView_details_name_content);
        year = (TextView)findViewById(R.id.textView_details_year_content);
        desc = (TextView)findViewById(R.id.textView_details_desc_content);
        image = (ImageView)findViewById(R.id.imageView_details_image_content);
        rating = (RatingBar)findViewById(R.id.ratingBar_details);
        selected = PortraitFragment_main.selectedMonument;

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                selected.setPoints(selected.getPoints() + rating);
                selected.setVotes(selected.getVotes() + 1);
                LoginActivity.dbh.RefreshRating(selected.getName(),selected.getPoints(),selected.getVotes());
                PortraitFragment_main.RefreshList();
            }
        });

        name.setText(selected.getName());
        year.setText(selected.getDate());
        desc.setText(selected.getDescription());
        image.setImageBitmap(selected.getPicture());
    }
}
