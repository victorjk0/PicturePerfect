package com.example.picture_perfect.Model;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PixelHandler {

    //Gets array of pixels from a bitmap image
    public int[] getPixels(Bitmap image) {

        //creates a new array with the length of total pixels
        int[] pixels = new int[image.getWidth() * image.getHeight()];

        //gets all pixels from the image.
        image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

        return pixels;
    }

    //Creates PixelColor objects with the RGB value of each pixel (1 object for each pixel)
    public ArrayList<PixelColor> getColorsFromPixels(int[] pixels) {

        ArrayList<PixelColor> pCAL = new ArrayList<>();

        for (int pixel : pixels) {

            PixelColor pc = new PixelColor();

            pc.setRed(Color.red(pixel));
            pc.setGreen(Color.green(pixel));
            pc.setBlue(Color.blue(pixel));

            pCAL.add(pc);
        }

        return pCAL;
    }

    //Adds the counted pixel colors to a list of strings.
    public ArrayList<String> displayTopColors(Map<String, Integer> colorMap) {

        ArrayList<String> strings = new ArrayList<>();

        for (Map.Entry<String, Integer> color : colorMap.entrySet()) {

            //gets the hex value of each entry
            strings.add(color.getKey());
        }

        return strings;
    }

    //sorts the Map with the hex color as key and count as value
    //and places it so that the hex string with most counts is first and the one with the least is at the end.
    public Map<String, Integer> sortByOccurrences(Map<String, Integer> map) {

        List<Map.Entry<String, Integer>> tmpList = new ArrayList<>(map.entrySet());

        //sorts the list in reverse order so highest value is first.
        tmpList.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Map<String, Integer> resultMap = new LinkedHashMap<>(map.size());

        for (Map.Entry<String, Integer> entry : tmpList) {

            resultMap.put(entry.getKey(), entry.getValue());
        }

        return resultMap;
    }

}
