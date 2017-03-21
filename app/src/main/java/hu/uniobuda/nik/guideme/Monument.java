package hu.uniobuda.nik.guideme;

import java.util.Date;

/**
 * Created by tothb on 2017. 03. 21..
 */

public class Monument
{
    string name;
    Coordinate coordinate;
    string description;
    //Picture
    int rate;
    int numberOfVisitors;
    Date yearOfBuilt;
    Type type;

    public Monument(string name, Coordinate coordinate, string description, int rate, int numberOfVisitors, Date yearOfBuilt, Type type) {
       this.name = name;
        this.coordinate = coordinate;
        this.description = description;
        this.rate = rate;
        this.numberOfVisitors = numberOfVisitors;
        this.yearOfBuilt = yearOfBuilt;
        this.type = type;
    }

    public string getName() {
        return name;
    }
    public void setName(string name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public string getDescription() {
        return description;
    }
    public void setDescription(string description) {
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

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
}
