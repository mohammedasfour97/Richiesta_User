package com.rkesta.richiesta.ui.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.api.WebService;

import static com.rkesta.richiesta.app.sharedObjects.userAr;
import static com.rkesta.richiesta.app.sharedObjects.userEN;
import static com.rkesta.richiesta.app.sharedObjects.userEmail;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class Help_Center extends AppCompatActivity {
    AppCompatImageView IV_DELIVERTO ;
    AppCompatEditText etNote;
    Button BTN_Send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        IV_DELIVERTO = findViewById(R.id.Help_Center_IV_DELIVERTO);
        etNote = findViewById(R.id.Help_Center_ET_etNote);
        BTN_Send = findViewById(R.id.Help_Center_BTN_Send);

        IV_DELIVERTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        BTN_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etNote.getText().toString().isEmpty()){
                    new Async_Help_Center(etNote.getText().toString()).execute();
                }else{
                    Toast.makeText(Help_Center.this, getResources().getString(R.string.Blank_field), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    private ProgressDialog pDialog;
    class Async_Help_Center extends AsyncTask<String, String, String> {

        String etNote = "";
        public Async_Help_Center(String etNote) {
            this.etNote = etNote;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Help_Center.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        String result = "";
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.ContactUS(EN_OR_AR(Help_Center.this,userAr,userEN) , userEmail , etNote );

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(result.equals("ERROR")){
                Toast.makeText(Help_Center.this, "ERROR", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Help_Center.this, "Done", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
//            if(StoreCategories.size() == 0){
////                repeate call
////                Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
//
//                Reload_Data();
//                //something went wrong stay
//            }else{
//                StoreCategories_exec(StoreCategories);
////                prepareoder(PendingOrders);
//            }
            pDialog.dismiss();
        }
    }




}