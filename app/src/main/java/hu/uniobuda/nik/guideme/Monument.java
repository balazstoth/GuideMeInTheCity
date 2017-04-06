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
    Date yearOfBuilt;
    Category type;

    public Monument(String name, Coordinate coordinate, String description, int rate, int numberOfVisitors, Date yearOfBuilt, Category type) {
       this.name = name;
        this.coordinate = coordinate;
        this.description = description;
        this.rate = rate;
        this.numberOfVisitors = numberOfVisitors;
        this.yearOfBuilt = yearOfBuilt;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getNumberOfVisitors() {
        return numberOfVisitors;
    }
    public void setNumberOfVisitors(int numberOfVisitors) {
        this.numberOfVisitors = numberOfVisitors;
    }

    public Date getYearOfBuilt() {
        return yearOfBuilt;
    }
    public void setYearOfBuilt(Date yearOfBuilt) {
        this.yearOfBuilt = yearOfBuilt;
    }

    public Category getType() {
        return type;
    }
    public void setType(Category type) {
        this.type = type;
    }
}
