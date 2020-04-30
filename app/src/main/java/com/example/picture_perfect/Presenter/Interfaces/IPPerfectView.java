package com.example.picture_perfect.Presenter.Interfaces;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Map;

//Interface to implement methods in the view.
public interface IPPerfectView {
    void getPixelColors(Bitmap img);
    void onTaskComplete(Map<String, Integer> result);
}
