package hu.uniobuda.nik.guideme.Models;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
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

    String name;
    Coordinate coordinate;
    String description;
    String numberOfVisitors;
    int points, votes;
    String date;
    String category;
    String isEnabled;
    double latitude, longitude;

    public Monument(String name, String description, String date, String category, int points, int votes, String isEnabled, double latitude, double longitude)
    {
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
        this.points = points;
        this.votes = votes;
        this.isEnabled = isEnabled;
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
        return "Name: " + this.getName() + "\n" + "Built in: " + this.getDate() + ", Rate: " + this.getRate();
    }
}

