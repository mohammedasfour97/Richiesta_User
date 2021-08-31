package com.rkesta.richiesta.custom_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.rkesta.richiesta.R;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;


public class AlertOrderCompleteDialog extends Dialog {

    public Activity c;
    public Dialog d;
    public String SONumber;
    public Button yes, no;


    public AlertOrderCompleteDialog(Activity activity, String genSONumber) {
        super(activity);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SONumber = genSONumber;
        this.c = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_order_complete_dialog);

        String EN_SONumber = "Your Order #("+SONumber+") has been placed successfully, a confirmation letter is sent to your e-mail address to view Order receipt and you can also check Order Details by checking \"My Orders\" in the \"Settings\" Section\n" +
                "            \n" +
                "Note: if you don't see the email in your inbox please check your spam folder.";


        String AR_SONumber = "تم تقديم طلبك رقم #("+SONumber+") بنجاح ، ويتم إرسال خطاب تأكيد إلى عنوان بريدك الإلكتروني لعرض إيصال الطلب ويمكنك أيضًا التحقق من تفاصيل الطلب عن طريق التحقق من \"طلباتي\" في قسم \"الإعدادات\"\n" +
                "\n" +
                "ملاحظة: إذا كنت لا ترى البريد الإلكتروني في علبة الوارد الخاصة بك ، يرجى التحقق من مجلد البريد المزعج.\n";


        String Show_text = EN_OR_AR(c,EN_SONumber,AR_SONumber);
                // text
        ((TextView) findViewById(R.id.alert_complete_TV_txt)).setText(Show_text);

        //yes
        ((Button) findViewById(R.id.alert_complete_btn_yes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}