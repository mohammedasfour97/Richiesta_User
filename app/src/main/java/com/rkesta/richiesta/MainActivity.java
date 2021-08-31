package com.rkesta.richiesta;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rkesta.richiesta.Helper.LocalHelper;
import com.rkesta.richiesta.ui.cart.ShoppingBasketFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static com.rkesta.richiesta.app.sharedObjects.GO_TO_position;
import static com.rkesta.richiesta.app.sharedObjects.TO_position;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase));
    }
    BottomNavigationView navView;
    AppBarConfiguration appBarConfiguration;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_shoppingbasket, R.id.navigation_settings)
                .build();


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(GO_TO_position){
            switch (TO_position){
                case 1:
                    navController.navigate(R.id.navigation_shoppingbasket);
                    break;
                case 2:
                    navController.navigate(R.id.navigation_settings);
                    break;
                default:
                    navController.navigate(R.id.navigation_home);
            }
        }

        GO_TO_position = false ;
        TO_position = 0 ;
    }

    //    boolean doubleBackToExitPressedOnce = false;
//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, EN_OR_AR(this,"Tap again to exit","انقر مرة أخرى للخروج"), Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }

}