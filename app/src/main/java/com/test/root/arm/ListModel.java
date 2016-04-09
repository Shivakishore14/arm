package com.test.root.arm;

/**
 * Created by root on 10/3/16.
 */public class ListModel {

    private  String Name = "";
    private  String Image = "";
    private  String pra = "";


    public void setName(String Name)
    {
        this.Name = Name;
    }

    public void setImage(String Image)
    {
        this.Image = Image;
    }

    public void setPra(String pra)
    {
        this.pra = pra;
    }


    public String getName()
    {
        return this.Name;
    }

    public String getImage()
    {
        return this.Image;
    }

    public String getPra()
    {
        return this.pra;
    }
}