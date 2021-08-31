package com.rkesta.richiesta.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.rkesta.richiesta.R;

public class ImageFullScreen extends AppCompatActivity {

    String Imageurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Imageurl= null;
            } else {
                Imageurl= extras.getString("Imageurl");
            }
        } else {
            Imageurl= (String) savedInstanceState.getSerializable("Imageurl");
        }
        AppCompatImageView IV = findViewById(R.id.ImageFullScreen_IV_Image);


        Glide
                .with(this)
                .load(Imageurl).fitCenter()
                .into(IV);


        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}