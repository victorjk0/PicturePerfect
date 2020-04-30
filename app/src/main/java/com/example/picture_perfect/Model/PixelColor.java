package com.example.picture_perfect.Model;

import android.graphics.Color;

public class PixelColor {

    private int _red;
    private int _green;
    private int _blue;

    public void setRed(int value) {
        _red = value;
    }

    public int getRed() {
        return _red;
    }

    public void setGreen(int value) {
        _green = value;
    }

    public int getGreen() {
        return _green;
    }

    public void setBlue(int value) {
        _blue = value;
    }

    public int getBlue() {
        return _blue;
    }

    //Converts the _red, _green, _blue fields to a hex string.
    public String toHex() {
        return String.format("#%02x%02x%02x", _red, _green, _blue);
    }




}
