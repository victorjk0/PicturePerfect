package com.example.picture_perfect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.picture_perfect.Presenter.Interfaces.IPPerfectView;
import com.example.picture_perfect.Presenter.Interfaces.PPerfectPresenter;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity implements IPPerfectView{

    private static int REQUEST_IMAGE_CAPTURE = 1;
    private PPerfectPresenter presenter;

    private ProgressBar _loading;
    private ImageView imageView;

    private View _firstColor;
    private View _secondColor;
    private View _thirdColor;
    private View _fourthColor;
    private View _fifthColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new PPerfectPresenter(this);

        imageView = findViewById(R.id.photo);

        _loading = findViewById(R.id.Loading);
        _loading.setVisibility(View.INVISIBLE);

        _firstColor = findViewById(R.id.fistColor);
        _secondColor = findViewById(R.id.secondColor);
        _thirdColor = findViewById(R.id.thirdColor);
        _fourthColor = findViewById(R.id.fourthColor);
        _fifthColor = findViewById(R.id.fifthColor);

    }

    //starts the camera intent.
    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    //When the camera intent is finished with its task this is run.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //gets thumbnail on the photo
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //starts our loading circle
            _loading.setVisibility(View.VISIBLE);

            //Calls our getPixelColor function in the presenter
            //With the picture we took with the camera intent
            getPixelColors(imageBitmap);

            //show image
            imageView.setImageBitmap(imageBitmap);

        }
    }


    //Calls the getPixColor in the Presenter via our IPPerfectView
    @Override
    public void getPixelColors(Bitmap img) {
        presenter.getPixelColors(img);
    }

    //This is run when our listener gets an updated from our.
    @Override
    public void onTaskComplete(Map<String, Integer> result) {
        //gets the sorted Map from sortByOccurrences in the presenter
        Map<String, Integer> topOccurrences = presenter.sortByOccurrences(result);

        //returns the results as a ArrayList of strings for the view to handle
        ArrayList<String> results = presenter.displayTopColors(topOccurrences);

        //updates the Views we have created for showing top 5 colors.
        updateColors(results);

        //Removes the loading circle
        _loading.setVisibility(View.GONE);

    }

    //Sets the color for each view to the matching color for top 5
    public void updateColors(ArrayList<String> topResults){

        ChangeBackgroundColor(_firstColor, topResults.get(0));
        ChangeBackgroundColor(_secondColor, topResults.get(1));
        ChangeBackgroundColor(_thirdColor, topResults.get(2));
        ChangeBackgroundColor(_fourthColor, topResults.get(3));
        ChangeBackgroundColor(_fifthColor, topResults.get(4));
    }

    //sets the background of the View
    public void ChangeBackgroundColor(View original, String hexStr) {

        //gets the original view background
        Drawable originalBackground = original.getBackground();

        //gets the changed background
        Drawable changedBackground = ChangeDrawableBackGround(originalBackground, hexStr);

        //sets the background to the new changed background
        original.setBackground(changedBackground);
    }


    //Change the background color of the drawable
    public Drawable ChangeDrawableBackGround(Drawable drawable, String hexString) {

        //wraps the drawable so that we can set the Tint (color) of it.
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);

        //sets the drawables background color to a hex color.
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(hexString));

        return wrappedDrawable;
    }
}