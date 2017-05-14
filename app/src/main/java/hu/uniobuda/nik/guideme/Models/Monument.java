package hu.uniobuda.nik.guideme.Models;

import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

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

    public float getRate()
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

    public int getVotes()
    {
        return votes;
    }

    public void setVotes(int votes)
    {
        this.votes = votes;
    }

    public float getPoints()
    {
        return points;
    }
    public double getLatitude(){return latitude;}

    public double getLongitude(){return longitude;}

    public void setPoints(float points)
    {
        this.points = points;
    }

    String name;
    Coordinate coordinate;
    String description;
    String numberOfVisitors;
    float points;
    int votes;
    String date;
    String category;
    String isEnabled;
    double latitude, longitude;

    Bitmap picture;

    public Monument(String name, String description, String date, String category, String points, String votes, String isEnabled, Bitmap picture, double latitude, double longitude)
    {
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
        this.isEnabled = isEnabled;
        this.points = Float.parseFloat(points);
        this.votes = Integer.parseInt(votes);
        this.isEnabled = isEnabled;
        this.picture = picture;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public  Monument(){}

    @Override
    public int compareTo(@NonNull Monument o)
    {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString(){
        return "Name: " + this.getName() + "\n" + "Built in: " + this.getDate() + ", Description: " + this.getDescription();
    }
}

