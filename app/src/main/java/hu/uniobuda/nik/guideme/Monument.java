package hu.uniobuda.nik.guideme;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by tothb on 2017. 03. 21..
 */

public class Monument implements Comparable<Monument>
{
    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public double getRate()
    {
        return votes == 0 ? 0 : points / votes;
    }

    public String getDate()
    {
        return date;
    }

    public String getCategory()
    {
        return category;
    }

    public Bitmap getPicture()
    {
        return picture;
    }

    String name;
    Coordinate coordinate;
    String description;
    String numberOfVisitors;
    int points, votes;
    String date;
    String category;
    String isEnabled;
    Bitmap picture;

    public Monument(String name, String description, String date, String category, String points, String votes, String isEnabled, Bitmap picture)
    {
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
        this.points = Integer.parseInt(points);
        this.votes = Integer.parseInt(votes);
        this.isEnabled = isEnabled;
        this.picture = picture;
    }

    @Override
    public int compareTo(@NonNull Monument o)
    {
        return this.name.compareTo(o.name);
    }
}

