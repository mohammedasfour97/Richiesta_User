package com.rkesta.richiesta.ui.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkesta.richiesta.Accounting.RegisterLastFragment;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.api.WebService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.rkesta.richiesta.app.constant.ImageURl;
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

public class EditProfile extends AppCompatActivity {

    Button BTN_SelectIMG;
    Button BTN_Save;
    ImageView IV_IMG;

    String uploadFileName = "usrprofile.png";

    EditText ET_firstname , ET_lastname , ET_email , ET_Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ((androidx.appcompat.widget.AppCompatImageView) findViewById(R.id.EditProfile_IV_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        BTN_SelectIMG = (Button) findViewById(R.id.EditProfile_BTN_SelectIMG);
        BTN_Save = (Button) findViewById(R.id.EditProfile_BTN_Save);
        IV_IMG = (ImageView) findViewById(R.id.EditProfile_IV_IMG);


        ET_firstname = (EditText) findViewById(R.id.EditProfile_ET_firstname);
        ET_lastname = (EditText) findViewById(R.id.EditProfile_ET_lastname);
        ET_email = (EditText) findViewById(R.id.EditProfile_ET_email);
        ET_Phone = (EditText) findViewById(R.id.EditProfile_ET_phone);

        uploadFileName = userProPicName;
        Glide.with(EditProfile.this).load(userProPic)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(IV_IMG);
        ET_firstname.setText(userFirstName);
        ET_lastname.setText(userLastName);
        ET_email.setText(userEmail);
        ET_Phone.setText(userPhone);

        BTN_SelectIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(EditProfile.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},100);
                        return;
                    }
                }
                showFileChooser();
            }
        });


        BTN_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if( ET_firstname.getText().toString().isEmpty() || ET_lastname.getText().toString().isEmpty()||  ET_email.getText().toString().isEmpty()|| ET_Phone.getText().toString().isEmpty()){

                        Toast.makeText(EditProfile.this, EN_OR_AR(EditProfile.this, "Please do not leave the field blank.", "من فضلك لا تترك الحقل فارغًا."), Toast.LENGTH_SHORT).show();

                        return;
                    }
//                    FirstName: TF_FirstName.text!, LastName: TF_LastName.text!, FirstNameAR: TF_FirstName.text!, LastNameAR: TF_LastName.text!, PassPhrase: TF_PassPhrase.text!, Email: TF_Email.text!, Phone: TF_Phone.text!, CellPhone: "", CellPhone2: "", ProfPic: "~/prdPic/\(picture)", isActive: true, isBanned: false, Createdby: "IOS_RegisterClass", Notes: ""
                    new Async_ModifyRK_User(user_ID , ET_firstname.getText().toString() , ET_lastname.getText().toString() ,
                            ET_firstname.getText().toString() , ET_lastname.getText().toString() , "" ,
                            ET_email.getText().toString() , userPhone , "" , "" , uploadFileName , true ,
                            false , "Android_EditProfile.class","").execute();


            }
        });

    }




    class Async_ModifyRK_User extends AsyncTask<String, String, String> {

        String User_ID ;
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

        public Async_ModifyRK_User(String User_ID , String FirstName ,  String LastName ,  String FirstNameAR ,  String LastNameAR ,
                                   String PassPhrase ,  String Email ,  String Phone , String CellPhone ,  String CellPhone2 ,
                                   String ProfPic ,  boolean isActive,   boolean isBanned ,  String Createdby, String Notes) {
            this.User_ID = User_ID ;
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
        String result = "";
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.ModifyRK_User(User_ID , FirstName ,  LastName ,  FirstNameAR ,  LastNameAR ,   PassPhrase ,  Email ,  Phone ,
                    CellPhone ,  CellPhone2 ,  ProfPic ,  isActive,   isBanned ,  Createdby, Notes);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(result.isEmpty() || result.equals("0") || result.equals("ERROR")){
                //ERROR
                Toast.makeText(EditProfile.this, EN_OR_AR(EditProfile.this, "Something went wrong, please try again later", "هناك شئ خاطئ، يرجى المحاولة فى وقت لاحق"), Toast.LENGTH_SHORT).show();

            }else{//1 Done

                Toast.makeText(EditProfile.this, EN_OR_AR(EditProfile.this, "saved", "تم الحفظ"), Toast.LENGTH_SHORT).show();

                userAr          = FirstName + " " + LastName ;
                userEN          = FirstName + " " + LastName ;
                userEmail       = Email;
                userPhone       = Phone;
                userProPic      = ImageURl+ uploadFileName;
                userProPicName  = uploadFileName;
                userFirstName   = FirstName;
                userLastName    = LastName;
                userFirstNameAR = FirstName;
                userLastNameAR  = LastName;

                set_pref(EditProfile.this,KEY_user_ID,user_ID);
                set_pref(EditProfile.this,KEY_userAr,userAr);
                set_pref(EditProfile.this,KEY_userEN,userEN);
                set_pref(EditProfile.this,KEY_userEmail,userEmail);
                set_pref(EditProfile.this,KEY_userPhone,userPhone);
                set_pref(EditProfile.this,KEY_userProPic,userProPic);
                set_pref(EditProfile.this,KEY_userProPicName,userProPicName);
                set_pref(EditProfile.this,KEY_userFirstName,userFirstName);
                set_pref(EditProfile.this,KEY_userLastName,userLastName);
                set_pref(EditProfile.this,KEY_userFirstNameAR,userFirstNameAR);
                set_pref(EditProfile.this,KEY_userLastNameAR,userLastNameAR);

                finish();
            }

        }
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
                bitmap = BitmapFactory.decodeStream(EditProfile.this.getContentResolver().openInputStream(targetUri));

                Date d = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("MMddyyhhmm", Locale.US);
                uploadFileName  =  format1.format(d.getTime())+"UserProPic.png";

                Log.d("UploadedImage","FileName : "+uploadFileName);
                Log.d("UploadedImage","Base64Str : "+BitmapToBase64Str(bitmap));

                new Async_UploadFile(BitmapToBase64Str(bitmap),uploadFileName).execute();
                /*   IV_BG_image.setImageBitmap(bitmap);*/
                Glide.with(EditProfile.this).load(bitmapToByte(bitmap))
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


}