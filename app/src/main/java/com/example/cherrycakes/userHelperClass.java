package com.example.cherrycakes;

public class userHelperClass {

    String name11, email11, mobile11, address11, pincode11,image;

    public userHelperClass() {

    }

    public userHelperClass(String name, String email, String mobile, String address, String pincode) {
        this.name11 = name;
        this.email11 = email;
        this.mobile11 = mobile;
        this.address11 = address;
        this.pincode11 = pincode;
    }

    public String getName() {
        return name11;
    }

    public void setName(String name) {
        this.name11 = name;
    }

    public String getEmail() {
        return email11;
    }

    public void setEmail(String email) {
        this.email11 = email;
    }

    public String getMobile() {
        return mobile11;
    }

    public void setMobile(String mobile) {
        this.mobile11 = mobile;
    }

    public String getAddress() {
        return address11;
    }

    public void setAddress(String address) {
        this.address11 = address;
    }

    public String getPincode() {
        return pincode11;
    }

    public void setPincode(String pincode) {
        this.pincode11 = pincode;
    }
}