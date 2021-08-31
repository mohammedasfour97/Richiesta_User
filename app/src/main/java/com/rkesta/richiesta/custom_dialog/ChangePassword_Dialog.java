package com.rkesta.richiesta.custom_dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.ui.settings.Help_Center;

import static com.rkesta.richiesta.app.sharedObjects.user_ID;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;


/**
 * Custom Dialog Fragment that prompts the user to add items to his cart
 */
public class ChangePassword_Dialog extends DialogFragment {
    Button submit;

    Dialog dialog;
    EditText ET_oldpass , ET_newpass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * The system calls this only when creating the layout in a dialog.
     * 2,"LM Red","62211611","20191106034041.jpg"
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
//        replaceTitle = (String) bundle.get("Title");
//        replaceTitle = (String) bundle.get("Title");
//        productnumber = (String) bundle.get("BarcodeNumber");

        // The only reason you might override this method is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        dialog = new Dialog(getActivity());

/*

        // Make us non-modal, so that others can receive touch events.  HASSAN BADAWI TEST No EXIT ON touch out side
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        // ...but notify us that it happened.   HASSAN BADAWI TEST No EXIT ON touch out side
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
*/


        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        // set the layout for the dialog
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        ET_oldpass = (EditText) dialog.findViewById(R.id.dialog_change_password_ET_oldpass);
        ET_newpass = (EditText) dialog.findViewById(R.id.dialog_change_password_ET_newpass);

        submit = (Button) dialog.findViewById(R.id.BTN_D_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!ET_oldpass.getText().toString().isEmpty() && !ET_newpass.getText().toString().isEmpty()){
                    new Async_changepass(ET_oldpass.getText().toString(),ET_newpass.getText().toString()).execute();
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.Blank_field), Toast.LENGTH_SHORT).show();
                }
//                dismiss();
            }
        });


        // Close
        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // User cancelled the dialog
                dismiss();
            }

        });
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    private ProgressDialog pDialog;
    class Async_changepass extends AsyncTask<String, String, String> {

        String oldpass = "";
        String newpass = "";
        public Async_changepass(String oldpass, String newpass) {
            this.oldpass = oldpass ;
            this.newpass = newpass ;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        String result = "";
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.UptPass(user_ID , oldpass , newpass );

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(result.equals("ERROR")){
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                dismiss();
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
