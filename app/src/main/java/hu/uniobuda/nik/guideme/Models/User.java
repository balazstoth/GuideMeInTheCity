package hu.uniobuda.nik.guideme.Models;

/**
 * Created by Tamás on 2017. 04. 22..
 */

public class User {
    int id;
    String name, email, username, password;

    public void  setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return this.id;
    }
    public void setName(String name){ this.name = name;}
    public  String getName() {return  this.name;}
    public void setEmail(String email)
    {
        this.email = email;
    }
    public  String getEmail()
    {
        return  this.email;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    public  String getUsername()
    {
        return  this.username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public  String getPassword()
    {
        return  this.password;
    }
}
