package hu.uniobuda.nik.guideme;

import java.util.Date;

/**
 * Created by tothb on 2017. 03. 21..
 */

public class Monument
{
    String name;
    Coordinate coordinate;
    String description;
    //Picture
    int rate;
    int numberOfVisitors;
    String date;
    String category;

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Coordinate getCoordinate()
    {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate)
    {
        this.coordinate = coordinate;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public int getRate()
    {
        return rate;
    }
    public void setRate(int rate)
    {
        this.rate = rate;
    }
    public int getNumberOfVisitors()
    {
        return numberOfVisitors;
    }
    public void setNumberOfVisitors(int numberOfVisitors)
    {
        this.numberOfVisitors = numberOfVisitors;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getCategory()
    {
        return category;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public Monument(String name, Coordinate coordinate, String description, int rate, int numberOfVisitors, String date, String category)
    {
        this.name = name;
        this.coordinate = coordinate;
        this.description = description;
        this.rate = rate;
        this.numberOfVisitors = numberOfVisitors;
        this.date = date;
        this.category = category;
    }

    public Monument(String name, String description, String date, String category)
    {
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
    }
}

