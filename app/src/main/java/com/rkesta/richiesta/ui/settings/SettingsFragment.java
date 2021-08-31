package com.rkesta.richiesta.ui.settings;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rkesta.richiesta.Accounting.Login;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.SplashScreen;
import com.rkesta.richiesta.app.DBHelper;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.custom_dialog.ChangeEmail_Dialog;
import com.rkesta.richiesta.custom_dialog.ChangePassword_Dialog;
import com.rkesta.richiesta.util.utility;

import static com.rkesta.richiesta.Helper.LocalHelper.SELECTED_LANGUAGE;
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
import static com.rkesta.richiesta.util.utility.OPEN_URL;
import static com.rkesta.richiesta.util.utility.get_pref;
import static com.rkesta.richiesta.util.utility.rateAction;
import static com.rkesta.richiesta.util.utility.set_pref;

public class SettingsFragment extends Fragment  {

    Button  Edit_Profile,
            My_Orders,
            Change_Password,
            Change_Email,
            My_address_book,
            Change_Area,
            Change_to,
            Help_Center,
            Terms_and_Condition,
            Privacy_Policy,
            Rate_US,
            Logout;
    TextView ClientName ;
    ImageView pic;
    ScrollView SV_buttons;

    private void init(View root) {
        ClientName = root.findViewById(R.id.setting_TV_ClientName);
        pic = root.findViewById(R.id.setting_IV_pic);

        Edit_Profile = root.findViewById(R.id.setting_btn_Edit_Profile);
        My_Orders = root.findViewById(R.id.setting_btn_My_Orders);
        Change_Password = root.findViewById(R.id.setting_btn_Change_Password);
        Change_Email = root.findViewById(R.id.setting_btn_Change_Email);
        My_address_book = root.findViewById(R.id.setting_btn_My_address_book);
        Change_Area = root.findViewById(R.id.setting_btn_Change_Area);
        Change_to = root.findViewById(R.id.setting_btn_Change_to);
        Help_Center = root.findViewById(R.id.setting_btn_Help_Center);
        Terms_and_Condition = root.findViewById(R.id.setting_btn_Terms_and_Condition);
        Privacy_Policy = root.findViewById(R.id.setting_btn_Privacy_Policy);
        Rate_US = root.findViewById(R.id.setting_btn_Rate_US);
        Logout = root.findViewById(R.id.setting_btn_Logout);
        SV_buttons = root.findViewById(R.id.setting_SV_buttons);

        if(IS_Guest){
            SV_buttons.setVisibility(View.GONE);
            ClientName.setText(EN_OR_AR(getActivity(),"Welcome Guest","مرحبا بالضيف"));
            Edit_Profile.setText(getResources().getText(R.string.Login));
            Drawable img = getResources().getDrawable( R.mipmap.ic_login );
            Edit_Profile.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);

//            Edit_Profile.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.ic_login),null);
        }else{
            SV_buttons.setVisibility(View.VISIBLE);
            ClientName.setText(userEN);
            Edit_Profile.setText(getResources().getText(R.string.Edit_Profile));
            Edit_Profile.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.ic_perm_identity),null);
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        init(root);
        initClicks();

        utility.displayImageOriginal(getActivity(),pic, userProPic);

        return root;
    }

    private void initClicks() {

        Edit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_Guest){

                    Intent i = new Intent(getContext(), SplashScreen.class);
// set the new task and clear flags
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                }else {
                    F_Edit_Profile();
                }
            }
        });
        My_Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_My_Orders();
            }
        });
        Change_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Change_Password();
            }
        });
        Change_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Change_Email();
            }
        });
        My_address_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_My_address_book();
            }
        });
        Change_Area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Change_Area();
            }
        });
        Change_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Change_to();
            }
        });
        Help_Center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Help_Center();
            }
        });
        Terms_and_Condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Terms_and_Condition();
            }
        });
        Privacy_Policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Privacy_Policy();
            }
        });
        Rate_US.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Rate_US();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_Logout();
            }
        });

    }



    private void F_Edit_Profile() {
        Intent i = new Intent(getActivity(),EditProfile.class);
        startActivity(i);
    }
    private void F_My_Orders() {
        Intent i = new Intent(getActivity(),MyOrders.class);
        startActivity(i);
    }
    private void F_Change_Password() {
        ChangePassword_Dialog customDialogFragment = new ChangePassword_Dialog();
        customDialogFragment.show(getFragmentManager(), "");
    }
    private void F_Change_Email() {
        ChangeEmail_Dialog customDialogFragment = new ChangeEmail_Dialog();
        customDialogFragment.show(getFragmentManager(), "");
    }
    private void F_My_address_book() {
        Intent i = new Intent(getActivity(),MyAddress.class);
        i.putExtra("order", "yes");
        startActivity(i);
    }
    private void F_Change_Area() {
        Intent i = new Intent(getActivity(),ChangeArea.class);
        startActivity(i);
    }
    private void F_Change_to() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked

                        if(get_pref(getActivity(),SELECTED_LANGUAGE).equals("") || get_pref(getActivity(),SELECTED_LANGUAGE).equals("en")){
                            set_pref(getActivity(),SELECTED_LANGUAGE,"ar");
                        }else{
                            set_pref(getActivity(),SELECTED_LANGUAGE,"en");
                        }
                        Intent i = new Intent(getContext(), SplashScreen.class);
// set the new task and clear flags
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(EN_OR_AR(getActivity(),"Are you sure to change the language?","هل أنت متأكد من تغيير اللغة؟")).setPositiveButton(EN_OR_AR(getActivity(),"Yes","نعم"), dialogClickListener)
                .setNegativeButton(EN_OR_AR(getActivity(),"No","لا"), dialogClickListener).show();


    }
    private void F_Help_Center() {
        Intent i = new Intent(getActivity(),Help_Center.class);
        startActivity(i);
    }
    private void F_Terms_and_Condition() {
        OPEN_URL(getActivity(),getResources().getString(R.string.TermsConditionLINK));
    }
    private void F_Privacy_Policy() {
        OPEN_URL(getActivity(),getResources().getString(R.string.PrivacyPolicyLINK));
    }
    private void F_Rate_US() {
        rateAction(getActivity());
//        try{
//         startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getActivity().getPackageName())));
//     }
//     catch (ActivityNotFoundException e){
//         startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getActivity().getPackageName())));
//     }
    }


    private void F_Logout() {

        DBHelper DB = new DBHelper(getActivity());
        DB.deleteallfrom(DBHelper.Cart_Table_Name);
        set_pref(getActivity(),KEY_user_ID,"");
        set_pref(getActivity(),KEY_userAr,"");
        set_pref(getActivity(),KEY_userEN,"");
        set_pref(getActivity(),KEY_userEmail,"");
        set_pref(getActivity(),KEY_userPhone,"");
        set_pref(getActivity(),KEY_userProPic,"");
        set_pref(getActivity(),KEY_userProPicName,"");
        set_pref(getActivity(),KEY_userFirstName,"");
        set_pref(getActivity(),KEY_userLastName,"");
        set_pref(getActivity(),KEY_userFirstNameAR,"");
        set_pref(getActivity(),KEY_userLastNameAR,"");


        Intent i = new Intent(getContext(), SplashScreen.class);
// set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);


    }

    @Override
    public void onResume() {
        super.onResume();

        if(IS_Guest){
            SV_buttons.setVisibility(View.GONE);
            ClientName.setText(EN_OR_AR(getActivity(),"Welcome Guest","مرحبا بالضيف"));
            Edit_Profile.setText(getResources().getText(R.string.Login));
            Drawable img = getResources().getDrawable( R.mipmap.ic_login );
            Edit_Profile.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);

//            Edit_Profile.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.ic_login),null);
        }else{
            SV_buttons.setVisibility(View.VISIBLE);
            ClientName.setText(userEN);
            Edit_Profile.setText(getResources().getText(R.string.Edit_Profile));
            Edit_Profile.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.ic_perm_identity),null);
        }
    }
}