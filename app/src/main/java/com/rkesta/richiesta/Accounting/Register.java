package com.rkesta.richiesta.Accounting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.rkesta.richiesta.R;

public class Register extends AppCompatActivity {

    public FrameLayout FM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FM  = (FrameLayout) findViewById(R.id.contentFragment);
        if ( FM != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of Fragment1
            RegisterFirstFragment firstFragment = new RegisterFirstFragment(this);
            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(FM.getId(), firstFragment).commit();

            //check the fkin upload image
//            RegisterLastFragment LastFragment = new RegisterLastFragment(this,"12345678+");
//            LastFragment.setArguments(getIntent().getExtras());
//            getSupportFragmentManager().beginTransaction().add(FM.getId(), LastFragment).commit();





        }

    }
}