package com.rkesta.richiesta.Accounting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.hbb20.CountryCodePicker;
import com.rkesta.richiesta.ChoiceCountry;
import com.rkesta.richiesta.MainActivity;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.SplashScreen;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.ui.settings.MyAddress;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiesta.app.constant.KEY_CountryID;
import static com.rkesta.richiesta.app.constant.KEY_userAr;
import static com.rkesta.richiesta.app.constant.KEY_userEN;
import static com.rkesta.richiesta.app.constant.KEY_userEmail;
import static com.rkesta.richiesta.app.constant.KEY_userFirstName;
import static com.rkesta.richiesta.app.constant.KEY_userFirstNameAR;
import static com.rkesta.richiesta.app.constant.KEY_userLastName;
import static com.rkesta.richiesta.app.constant.KEY_userLastNameAR;
import static com.rkesta.richiesta.app.constant.KEY_userPhone;
import static com.rkesta.richiesta.app.constant.KEY_userProPic;
import static com.rkesta.richiesta.app.constant.KEY_userProPicName;
import static com.rkesta.richiesta.app.constant.KEY_user_ID;
import static com.rkesta.richiesta.app.sharedObjects.*;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.OPEN_URL;
import static com.rkesta.richiesta.util.utility.set_pref;
import static com.rkesta.richiesta.util.utility.substringAfter;

public class Login extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText editTextCarrierNumber;
    LinearLayout ll_phoneContainer;

    EditText ET_password;
    ImageView IV_show_pass;
    boolean is_validNum = false;
    String Phone = "";

    Button BTN_login;
    Button BTN_Register;
    Button BTN_continueasguest;

    TextView TV_Privacy_Policy;
    TextView TV_Terms_and_Condition;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Initbuttons();
        Init_phone();
        Init_Pass();

    }

    private void loginFire() {

        // phone :01146364751  password : 123
        if(is_validNum){

            if(ET_password.getText().toString().isEmpty() || Phone.isEmpty()){
                Toast.makeText(this, EN_OR_AR(this,"Please do not leave the field blank.","من فضلك لا تترك الحقل فارغًا."), Toast.LENGTH_SHORT).show();
                return;
            }else{

                new Async_UserLogin(Phone, ET_password.getText().toString()).execute();


            }


        }else{
            Toast.makeText(this, EN_OR_AR(this,"Please set a valid phone number","يرجى تعيين رقم هاتف صالح"), Toast.LENGTH_SHORT).show();
        }


    }

    private void Initbuttons() {
        BTN_Register = (Button) findViewById(R.id.Login_BTN_Register);
        BTN_login = (Button) findViewById(R.id.Login_BTN_login);
        TV_Privacy_Policy = (TextView) findViewById(R.id.Login_TV_Privacy_Policy);
        TV_Terms_and_Condition = (TextView) findViewById(R.id.Login_TV_Terms_and_Condition);
        BTN_continueasguest = (Button) findViewById(R.id.Login_BTN_continueasguest);

        BTN_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFire();
            }
        });

        TV_Privacy_Policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OPEN_URL(Login.this,getResources().getString(R.string.PrivacyPolicyLINK));
            }
        });

        TV_Terms_and_Condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OPEN_URL(Login.this,getResources().getString(R.string.TermsConditionLINK));
            }
        });

        BTN_continueasguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IS_Guest = true ;

                fetchCurrentLocation();
            }
        });



    }
    private void Init_phone() {

        ccp = (CountryCodePicker) findViewById(R.id.Login_ccp);
        editTextCarrierNumber = (EditText) findViewById(R.id.Login_ET_carrierNumber);
        ll_phoneContainer = findViewById(R.id.login_ll_phoneContainer);

        ccp.registerCarrierNumberEditText(editTextCarrierNumber);

        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                //                //get formatted number i.e "+1 469-664-1766"
                //                ccp.getFormattedFullNumber();
                //
                //              //get unformatted number i.e. "14696641766"
                //                ccp.getFullNumber();
                //
                //              //get unformatted number with prefix "+" i.e "+14696641766"
                //                ccp.getFullNumberWithPlus();
//                Log.d("ValidityChanged",
//                        "valid="+isValidNumber
//                                +",,,Format="+ccp.getFormattedFullNumber()
//                                +",,,none="+ccp.getFullNumber()
//                                +",,,plus="+ccp.getFullNumberWithPlus()
//                );

                is_validNum = isValidNumber;
                if(isValidNumber){
                    ll_phoneContainer.setBackground(getResources().getDrawable(R.drawable.btn_flat_accepted));
                    Phone = ccp.getFullNumberWithPlus();
                }else{
                    ll_phoneContainer.setBackground(getResources().getDrawable(R.drawable.btn_flat_normal));
                    Phone = "";
                }

            }
        });
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Log.d("ValidityChanged",
                        "Updated="+ccp.getSelectedCountryName()
                );
                Phone = "";
                is_validNum = false;
                editTextCarrierNumber.setText("");

            }
        });
    }
    private void Init_Pass() {
        ET_password = (EditText) findViewById(R.id.Login_ET_password);
        IV_show_pass = (ImageView) findViewById(R.id.Login_IV_show_pass);

        IV_show_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ET_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    IV_show_pass.setImageResource(R.mipmap.ic_hidepass);
                    //Show Password
                    ET_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    IV_show_pass.setImageResource(R.mipmap.ic_showpass);
                    //Hide Password
                    ET_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }


    private ProgressDialog pDialog;
    class Async_UserLogin extends AsyncTask<String, String, String> {
        String Phone;
        String Password;
        public Async_UserLogin(String phone, String password) {
            Phone = phone;
            Password = password;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.RK_UserLogin(Phone , Password);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(result.isEmpty()){
                Toast.makeText(Login.this, EN_OR_AR(Login.this,"The phone or Password is wrong",
                        "الهاتف أو كلمة المرور خاطئة"), Toast.LENGTH_SHORT).show();
            }else{

                user_ID = result.get(0).get("ID");
                userAr = result.get(0).get("FullNameArabic");
                userEN = result.get(0).get("FullNameEnglish");
                userEmail = result.get(0).get("Email");
                userPhone = result.get(0).get("Phone");
                userProPic = result.get(0).get("ProfPic");
                userProPicName = substringAfter(result.get(0).get("ProfPic"), "/prdPic/");
                userFirstName = result.get(0).get("FirstName");
                userLastName = result.get(0).get("LastName");
                userFirstNameAR = result.get(0).get("FirstNameAR");
                userLastNameAR = result.get(0).get("LastNameAR");
 
                set_pref(Login.this,KEY_user_ID,user_ID);
                set_pref(Login.this,KEY_userAr,userAr);
                set_pref(Login.this,KEY_userEN,userEN);
                set_pref(Login.this,KEY_userEmail,userEmail);
                set_pref(Login.this,KEY_userPhone,userPhone);
                set_pref(Login.this,KEY_userProPic,userProPic);
                set_pref(Login.this,KEY_userProPicName,userProPicName);
                set_pref(Login.this,KEY_userFirstName,userFirstName);
                set_pref(Login.this,KEY_userLastName,userLastName);
                set_pref(Login.this,KEY_userFirstNameAR,userFirstNameAR);
                set_pref(Login.this,KEY_userLastNameAR,userLastNameAR);
                IS_Guest = false;



                Toast.makeText(Login.this, EN_OR_AR(Login.this,"welcome  "+userFirstName+" "+userLastName
                        ," مرحبا بك "+userFirstName+" "+userLastName), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Login.this, MyAddress.class);
                i.putExtra("order", "no");
                startActivity(i);

                pDialog.dismiss();
                // close this activity
                finish();

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

    private void fetchCurrentLocation() {


        intent = new Intent(Login.this, MainActivity.class);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Login.this);

        if (!checkPermission()) {return;}
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,
                new CancellationToken() {
                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }

                    @Override
                    public CancellationToken onCanceledRequested(OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    intent.putExtra("lat", String.valueOf(currentLocation.getLatitude()));
                    intent.putExtra("long", String.valueOf(currentLocation.getLongitude()));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean checkPermission() {

        if (!constant.checkLocationPermission(Login.this)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            return false;
        } else return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    fetchCurrentLocation();
                }
                else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){

                    intent.putExtra("lat", "0");
                    intent.putExtra("long", "0");
                    startActivity(intent);
                    finish();
                    break;
                }


        }
    }

}