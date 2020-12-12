package com.example.base_sql;

public class Contact {
    private String name;
    private int phone;
    public static int  nombredescontacts = 0;
    private  byte[] image;



    private int id;
public Contact(){}
    public Contact(String name, int phone) {
        this.name = name;
        this.phone = phone;
        this.id = nombredescontacts;
        nombredescontacts++;
    }
    public Contact(String name, int phone,byte[] images) {
        this.name = name;
        this.phone = phone;
        this.image = images;

    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
