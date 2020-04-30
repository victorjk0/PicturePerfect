package com.example.picture_perfect.Model;

import android.os.AsyncTask;

import com.example.picture_perfect.onTaskCompleted;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ColorCounterAsync extends AsyncTask<ArrayList<PixelColor>, Void, Map<String, Integer>> {


    private List<onTaskCompleted> listeners = new ArrayList<>();

    //Adds listener to the Listener List
    public void addListener(onTaskCompleted listener) {
        listeners.add(listener);
    }

    //Removes listener from the Listener List
    public void removeListener(onTaskCompleted listener) {
        listeners.remove(listener);
    }


    //Async task runs in background
    //This function is running the color grouping logic.
    //So that we can group colors by the distance from each other using the euclidean algorithm
    @Override
    protected Map<String, Integer> doInBackground(ArrayList<PixelColor>... params) {

        Map<String, Integer> countColors = new LinkedHashMap<String, Integer>();
        Map<String, Double> testMap = new LinkedHashMap<>();
        Map<String, Integer> resultOfCountedColors = new LinkedHashMap<>();

        //Gets the first parameter from the parameter
        ArrayList<PixelColor> pCAL = params[0];

        PixelColor tmpPC = null;
        double tmpDouble = 0;



        for (PixelColor pc : pCAL) {
            //checks if color is in the countColors Map
            if (countColors.containsKey(pc.toHex())) {
                //if yes add one to the value of the key.
                countColors.put(pc.toHex(), countColors.get(pc.toHex()) + 1);
            } else {
                //if no Map a new hex string with a value of 1
                countColors.put(pc.toHex(), 1);

                if (tmpPC == null) {

                    //if yes create new obj with current obj values.
                    tmpPC = new PixelColor();
                    tmpPC.setRed(pc.getRed());
                    tmpPC.setGreen(pc.getGreen());
                    tmpPC.setBlue(pc.getBlue());

                } else {
                    //if no calculate the RGB distance from pc obj and tmpPC obj
                    double value = colorGroupingAlogrithm(pc, tmpPC);

                    if (testMap.isEmpty()) {

                        //if yes. Maps hex string with a value of the RGB distance.
                        testMap.put(pc.toHex(), value);
                        tmpDouble = value;

                        //checks if last value subtracted with current value is over 20
                        // or last value subtracted with current value is under -20
                    } else if ((tmpDouble - value) > 20 || (tmpDouble - value) < -20) {
                        //else if yes Maps hex string with a value of the RGB distance.
                        testMap.put(pc.toHex(), value);
                        tmpDouble = value;
                    }
                    //sets the tmpPC obj with current objs values for further checking.
                    tmpPC.setRed(pc.getRed());
                    tmpPC.setGreen(pc.getGreen());
                    tmpPC.setBlue(pc.getBlue());
                }
            }
        }


        for (Map.Entry<String, Double> row : testMap.entrySet()) {

            resultOfCountedColors.put(row.getKey(), countColors.get(row.getKey()));
        }

        return resultOfCountedColors;
    }

    //When task is done
    //This functions updates a listerner with the result Map from the thread.
    @Override
    protected void onPostExecute(Map<String, Integer> result) {
        for (onTaskCompleted l : listeners) {
            l.onTaskCompleted(result);
        }
    }

    //This is the euclidean algorithm that calculates the RGB distance between two colors
    private double colorGroupingAlogrithm(PixelColor color, PixelColor nextColor) {

        //subtracts the next colors values with our current colors values
        double red = nextColor.getRed() - color.getRed();
        double green = nextColor.getGreen() - color.getGreen();
        double blue = nextColor.getBlue() - color.getBlue();

        //finds the power of two value for each color result from our subtraction above
        red = Math.pow(red, 2);
        green = Math.pow(green, 2);
        blue = Math.pow(blue, 2);

        //Finds the square root from our power of two values that are added together.
        return Math.sqrt(red + green + blue);

    }

}
