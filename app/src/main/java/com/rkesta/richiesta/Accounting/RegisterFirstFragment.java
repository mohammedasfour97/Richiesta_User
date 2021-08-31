package com.rkesta.richiesta.Accounting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hbb20.CountryCodePicker;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.api.WebService;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class RegisterFirstFragment extends Fragment {

    EditText ET_Phone;

    CountryCodePicker ccp;
    LinearLayout ll_phoneContainer;

    boolean is_validNum = false;
    Register registerAct;
    String Phone = "";

    private void Init_phone(View root) {

        ccp = (CountryCodePicker) root.findViewById(R.id.firstRegister_ccp);
        ET_Phone = (EditText) root.findViewById(R.id.firstRegister_ET_carrierNumber);
        ll_phoneContainer = root.findViewById(R.id.firstRegister_ll_phoneContainer);

        ccp.registerCarrierNumberEditText(ET_Phone);

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
                ET_Phone.setText("");

            }
        });
    }



    public RegisterFirstFragment(Register register) {
        this.registerAct = register;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_first, container, false);
        Init_phone(root);



        ((Button) root.findViewById(R.id.firstRegister_BTN_continueasguest)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // below line is for checking weather the user
                // has entered his mobile number or not.
                if (is_validNum) {
                    new Async_VerifyPhoneNumber(Phone).execute();
                } else {
                    Toast.makeText(getActivity(), EN_OR_AR(getActivity(),"Please do not leave the field blank.","من فضلك لا تترك الحقل فارغًا."), Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });



        return root;
    }

    class Async_VerifyPhoneNumber extends AsyncTask<String, String, String> {

        String Phone = "";

        public Async_VerifyPhoneNumber(String phone) {
            Phone = phone;
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
            result = WS.VerifyPhoneNumber(Phone);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(result.equals("Y") || result.equals("ERROR")) {

                Toast.makeText(registerAct, EN_OR_AR(registerAct,
                "This number is already registered with us, did you forget the password?",
                "هذا الرقم مسجل لدينا من قبل ، هل نسيت كلمه المرور؟"), Toast.LENGTH_SHORT).show();


            }else{

            // Create an instance of Fragment1
            RegisterSecFragment SecFragment = new RegisterSecFragment(registerAct,Phone);
            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            SecFragment.setArguments(registerAct.getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            registerAct.getSupportFragmentManager().beginTransaction()
                    .add(registerAct.FM.getId(), SecFragment).commit();

            }
        }
    }

}
