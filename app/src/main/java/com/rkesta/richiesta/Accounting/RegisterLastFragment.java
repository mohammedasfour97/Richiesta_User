package com.rkesta.richiesta.Accounting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkesta.richiesta.MainActivity;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.ui.settings.Help_Center;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
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
import static com.rkesta.richiesta.app.sharedObjects.IS_Guest;
import static com.rkesta.richiesta.app.sharedObjects.userAr;
import static com.rkesta.richiesta.app.sharedObjects.userEN;
import static com.rkesta.richiesta.app.sharedObjects.userEmail;
import static com.rkesta.richiesta.app.sharedObjects.userFirstName;
import static com.rkesta.richiesta.app.sharedObjects.userFirstNameAR;
import static com.rkesta.richiesta.app.sharedObjects.userLastName;
import static com.rkesta.richiesta.app.sharedObjects.userLastNameAR;
import static com.rkesta.richiesta.app.sharedObjects.userPhone;
import static com.rkesta.richiesta.app.sharedObjects.userProPic;
import static com.rkesta.richiesta.app.sharedObjects.userProPicName;
import static com.rkesta.richiesta.app.sharedObjects.user_ID;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.set_pref;
import static com.rkesta.richiesta.util.utility.substringAfter;

public class RegisterLastFragment extends Fragment {

    Register registerAct;
    String Phone = "";
    public RegisterLastFragment(Register register, String phone) {
        this.registerAct = register;
        this.Phone = phone;
    }


    Button BTN_SelectIMG;
    Button BTN_RegisterNow;
    ImageView IV_IMG;

    EditText ET_firstname , ET_lastname , ET_email , ET_Phone ,  ET_password , ET_repassword;
    ImageView IV_show_pass , IV_show_repass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_last, container, false);

        BTN_SelectIMG = (Button) root.findViewById(R.id.LastRegister_BTN_SelectIMG);
        BTN_RegisterNow = (Button) root.findViewById(R.id.LastRegister_BTN_RegisterNow);
        IV_IMG = (ImageView) root.findViewById(R.id.LastRegister_IV_IMG);

        ET_firstname = (EditText) root.findViewById(R.id.LastRegister_ET_firstname);
        ET_lastname = (EditText) root.findViewById(R.id.LastRegister_ET_lastname);
        ET_email = (EditText) root.findViewById(R.id.LastRegister_ET_email);
        ET_Phone = (EditText) root.findViewById(R.id.LastRegister_ET_phone);
        Init_Pass(root);

        ET_Phone.setText(Phone);



        BTN_SelectIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},100);
                        return;
                    }
                }
                showFileChooser();
            }
        });

        BTN_RegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ET_password.getText().equals(ET_repassword.getText())){

                    Toast.makeText(registerAct, EN_OR_AR(registerAct,"Password and Password Confirmation doesn't match.", "كلمة المرور وتأكيد كلمة المرور لا تتطابق."), Toast.LENGTH_SHORT).show();

                    return;

                }else {
                    if(ET_password.getText().toString().isEmpty() || ET_repassword.getText().toString().isEmpty() || ET_firstname.getText().toString().isEmpty() || ET_lastname.getText().toString().isEmpty()||  ET_email.getText().toString().isEmpty()|| ET_Phone.getText().toString().isEmpty()){

                        Toast.makeText(registerAct, EN_OR_AR(registerAct, "Please do not leave the field blank.", "من فضلك لا تترك الحقل فارغًا."), Toast.LENGTH_SHORT).show();

                        return;
                    }
//                    FirstName: TF_FirstName.text!, LastName: TF_LastName.text!, FirstNameAR: TF_FirstName.text!, LastNameAR: TF_LastName.text!, PassPhrase: TF_PassPhrase.text!, Email: TF_Email.text!, Phone: TF_Phone.text!, CellPhone: "", CellPhone2: "", ProfPic: "~/prdPic/\(picture)", isActive: true, isBanned: false, Createdby: "IOS_RegisterClass", Notes: ""
                    new Async_InsertRK_User(ET_firstname.getText().toString() , ET_lastname.getText().toString() , ET_firstname.getText().toString() , ET_lastname.getText().toString() , ET_password.getText().toString() , ET_email.getText().toString() , Phone , "" , "" , "~/prdPic/"+uploadFileName , true , false , "Android_RegisterLastFragment.class","").execute();
                }


            }
        });



        return root;
    }


    private void Init_Pass(View root) {
        ET_password = (EditText) root.findViewById(R.id.LastRegister_ET_password);
        IV_show_pass = (ImageView) root.findViewById(R.id.LastRegister_IV_show_pass);

        ET_repassword = (EditText) root.findViewById(R.id.LastRegister_ET_repassword);
        IV_show_repass = (ImageView) root.findViewById(R.id.LastRegister_IV_show_repass);

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

        IV_show_repass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ET_repassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    IV_show_repass.setImageResource(R.mipmap.ic_hidepass);
                    //Show Password
                    ET_repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    IV_show_repass.setImageResource(R.mipmap.ic_showpass);
                    //Hide Password
                    ET_repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);



        //Intent intent = new Intent();
        //intent.setType("*/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), 100 );

    }


    String URI_Image;
    String uploadFileName = "usrprofile.png";
    private byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        return byteArray;
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private String BitmapToBase64Str(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            URI_Image = targetUri.toString();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));

                Date d = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("MMddyyhhmm", Locale.US);
                uploadFileName  =  format1.format(d.getTime())+"UserProPic.png";

                Log.d("UploadedImage","FileName : "+uploadFileName);
                Log.d("UploadedImage","Base64Str : "+BitmapToBase64Str(bitmap));

                new Async_UploadFile(BitmapToBase64Str(bitmap),uploadFileName).execute();
                /*   IV_BG_image.setImageBitmap(bitmap);*/
                Glide.with(getActivity()).load(bitmapToByte(bitmap))
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(IV_IMG);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    class Async_UploadFile extends AsyncTask<String, String, String> {

        String bitmapToBase64Str = "";
        String uploadFileName = "";

        public Async_UploadFile(String BitmapToBase64Str, String UploadFileName) {
            bitmapToBase64Str = BitmapToBase64Str;
            uploadFileName = UploadFileName;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        String result = "";
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.UploadFile(bitmapToBase64Str , uploadFileName);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            Log.d("UploadedImage",result);
        }
    }

    class Async_InsertRK_User extends AsyncTask<String, String, String> {

        String FirstName ;
        String LastName ;
        String FirstNameAR ;
        String LastNameAR ;
        String  PassPhrase ;
        String Email ;
        String Phone ;
        String CellPhone ;
        String CellPhone2 ;
        String ProfPic ;
        boolean isActive;
        boolean  isBanned ;
        String Createdby;
        String Notes;

        public Async_InsertRK_User(String FirstName ,  String LastName ,  String FirstNameAR ,  String LastNameAR ,   String PassPhrase ,  String Email ,  String Phone , String CellPhone ,  String CellPhone2 ,  String ProfPic ,  boolean isActive,   boolean isBanned ,  String Createdby, String Notes) {
            this.FirstName = FirstName ;
            this.LastName = LastName ;
            this.FirstNameAR = FirstNameAR ;
            this.LastNameAR = LastNameAR ;
            this.PassPhrase =  PassPhrase ;
            this.Email = Email ;
            this.Phone = Phone ;
            this.CellPhone = CellPhone ;
            this.CellPhone2 = CellPhone2 ;
            this.ProfPic = ProfPic ;
            this.isActive =  isActive;
            this.isBanned =   isBanned ;
            this.Createdby = Createdby;
            this.Notes = Notes;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.InsertRK_User(FirstName ,  LastName ,  FirstNameAR ,  LastNameAR ,   PassPhrase ,  Email ,  Phone , CellPhone ,  CellPhone2 ,  ProfPic ,  isActive,   isBanned ,  Createdby, Notes);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if (!result.isEmpty()) {
                if (result.get(0).get("ID").isEmpty() || result.get(0).get("ID").equals("0")) {
                    //ERROR
                    Toast.makeText(registerAct, EN_OR_AR(registerAct, "Something went wrong, please try again later", "هناك شئ خاطئ، يرجى المحاولة فى وقت لاحق"), Toast.LENGTH_SHORT).show();

                } else {//1 Done
                    new Async_UserLogin(Phone, ET_password.getText().toString()).execute();
                }

            }
        }
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
            pDialog = new ProgressDialog(registerAct);
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
//                Toast.makeText(registerAct, EN_OR_AR(registerAct,"The phone or Password is wrong","الهاتف أو كلمة المرور خاطئة"), Toast.LENGTH_SHORT).show();
                Toast.makeText(registerAct, EN_OR_AR(registerAct, "Something went wrong, please try again later", "هناك شئ خاطئ، يرجى المحاولة فى وقت لاحق"), Toast.LENGTH_SHORT).show();
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

                set_pref(registerAct,KEY_user_ID,user_ID);
                set_pref(registerAct,KEY_userAr,userAr);
                set_pref(registerAct,KEY_userEN,userEN);
                set_pref(registerAct,KEY_userEmail,userEmail);
                set_pref(registerAct,KEY_userPhone,userPhone);
                set_pref(registerAct,KEY_userProPic,userProPic);
                set_pref(registerAct,KEY_userProPicName,userProPicName);
                set_pref(registerAct,KEY_userFirstName,userFirstName);
                set_pref(registerAct,KEY_userLastName,userLastName);
                set_pref(registerAct,KEY_userFirstNameAR,userFirstNameAR);
                set_pref(registerAct,KEY_userLastNameAR,userLastNameAR);
                IS_Guest = false;



                Toast.makeText(registerAct, EN_OR_AR(registerAct,"welcome  "+userFirstName+" "+userLastName
                        ," مرحبا بك "+userFirstName+" "+userLastName), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(registerAct, MainActivity.class);
// set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                pDialog.dismiss();
                // close this activity

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

