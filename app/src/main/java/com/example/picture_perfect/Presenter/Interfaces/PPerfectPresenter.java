package com.example.picture_perfect.Presenter.Interfaces;

import android.graphics.Bitmap;

import com.example.picture_perfect.Model.ColorCounterAsync;
import com.example.picture_perfect.Model.PixelColor;
import com.example.picture_perfect.Model.PixelColor;
import com.example.picture_perfect.Model.PixelHandler;
import com.example.picture_perfect.onTaskCompleted;

import java.util.ArrayList;
import java.util.Map;

public class PPerfectPresenter implements onTaskCompleted {

    private final PixelHandler _pixHandler;
    private final ColorCounterAsync _ccAsync;
    private final IPPerfectView _view;

    //Constructor
    public PPerfectPresenter(IPPerfectView view) {
        _view = view;
        _pixHandler = new PixelHandler();
        _ccAsync = new ColorCounterAsync();
    }

    //Starts the colorGrouping Async task. in the ColorCounterAsync
    public void colorCounter(ArrayList<PixelColor> pictureColorArrayList) {
        //inits a listener
        _ccAsync.addListener(this);

        //runs thread
        _ccAsync.execute(pictureColorArrayList);
    }

    //Calls the sortByOccurrences method in the PixelHandler
    public Map<String, Integer> sortByOccurrences(Map<String, Integer> map) {
        return _pixHandler.sortByOccurrences(map);
    }

    //Calls the displayTopColors method in the PixelHandler
    public ArrayList<String> displayTopColors(Map<String, Integer> colorCount) {
        return _pixHandler.displayTopColors(colorCount);
    }

    //This method gets all the Pixel Colors
    public void getPixelColors(Bitmap image) {

        //Calls the getPixel method in the PixelHandler
        int[] pixels = _pixHandler.getPixels(image);

        //Calls the getColorsFromPixels method in the PixelHandler
        ArrayList<PixelColor> pixelColors = _pixHandler.getColorsFromPixels(pixels);

        //Calls the method that starts the thread.
        colorCounter(pixelColors);
    }

    @Override
    public void onTaskCompleted(Map<String, Integer> result) {
        _view.onTaskComplete(result);
    }
}


