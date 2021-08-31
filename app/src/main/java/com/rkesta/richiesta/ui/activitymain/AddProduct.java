package com.rkesta.richiesta.ui.activitymain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.app.DBHelper;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.model.cart.M_Cart;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Additional;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Color;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Size;
import com.rkesta.richiesta.util.ImageFullScreen;
import com.rkesta.richiesta.util.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.rkesta.richiesta.app.sharedObjects.SharedSelectedStore_ID;
import static com.rkesta.richiesta.app.sharedObjects.SharedSelectedStore_IMG;
import static com.rkesta.richiesta.app.sharedObjects.SharedSelectedStore_name;
import static com.rkesta.richiesta.app.sharedObjects.addonAdditional_List;
import static com.rkesta.richiesta.app.sharedObjects.addonColor_List;
import static com.rkesta.richiesta.app.sharedObjects.addonSize_List;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class AddProduct extends AppCompatActivity {
    AppCompatImageView IV_close , IV_ProductImage ;
    TextView TV_ProductName ;
    TextView TV_ProductPrice ;
    TextView ProductDetails ;
    AppCompatEditText ET_Note ;
    ImageButton IB_positive ;
    ImageButton IB_minus ;
    TextView TV_Quanitity ;
    Button BTN_AddToBasket ;


    String Product_ID = "";
    String NameAR = "";
    String NameEN = "";
    String Desc = "";
    String Price = "";
    String Imageurl = "";
    String Branch_ID = "";

    DBHelper DB ;

    private void Init() {
        TV_ProductName = findViewById(R.id.Add_Product_TV_ProductName);
        TV_ProductPrice = findViewById(R.id.Add_Product_TV_ProductPrice);
        ProductDetails = findViewById(R.id.Add_Product_TV_ProductDetails);
        IV_ProductImage = findViewById(R.id.Add_Product_IV_ProductImage);
        IV_close = findViewById(R.id.Add_Product_IV_close);

        IB_positive = findViewById(R.id.Add_Product_IB_positive);
        IB_minus = findViewById(R.id.Add_Product_IB_minus);
        TV_Quanitity = findViewById(R.id.Add_Product_TV_Quanitity);

        ET_Note = findViewById(R.id.Add_Product_ET_Note);
        BTN_AddToBasket = findViewById(R.id.Add_Product_BTN_AddToBasket);
        
        BTN_AddToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddToCart();
            }
        });

        IV_ProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddProduct.this, ImageFullScreen.class);
                i.putExtra("Imageurl", Imageurl);
                startActivity(i);
            }
        });

    }

    private void AddToCart() {

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        String Datenow = df.format(new Date());

        ArrayList<M_Cart> cart_item = DB.getCart();
//        EN_OR_AR(this,NameEN,NameAR)
        if(cart_item.isEmpty()){
            DB.setCart(SharedSelectedStore_ID , SharedSelectedStore_name , SharedSelectedStore_IMG , Branch_ID ,Product_ID ,NameEN,NameAR ,Price ,Imageurl ,currentColor_ID ,currentColor_name ,lastColor+"" ,currentAdditional_ID ,currentAdditional_name ,lastAdditional+"" ,currentSize_ID ,currentSize_name ,lastSize+"" ,TV_Quanitity.getText().toString() ,totalprice+"" ,ET_Note.getText().toString() ,Datenow);

            Toast.makeText(this, EN_OR_AR(this,"Added","تمت الإضافة"), Toast.LENGTH_SHORT).show();

            finish();
        }else{
                if(cart_item.get(0).getBranch_ID().equals(Branch_ID)){
                    DB.setCart(SharedSelectedStore_ID , SharedSelectedStore_name , SharedSelectedStore_IMG , Branch_ID ,Product_ID ,NameEN,NameAR ,Price ,Imageurl ,currentColor_ID ,currentColor_name ,lastColor+"" ,currentAdditional_ID ,currentAdditional_name ,lastAdditional+"" ,currentSize_ID ,currentSize_name ,lastSize+"" ,TV_Quanitity.getText().toString() ,totalprice+"" ,ET_Note.getText().toString() ,Datenow);

                    Toast.makeText(this, EN_OR_AR(this,"Added","تمت الإضافة"), Toast.LENGTH_SHORT).show();

                    finish();
            }else{
                    Toast.makeText(this, EN_OR_AR(this,"It is not possible to order from another store. Please finish your first destination or empty your list of requests.",
                            "لا يمكن طلب من محل اخر برجاء الانتهاء من وجهتك الاولى او افرغ قائمه الطلبات لديك"), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Product_ID = null;
                NameAR = null;
                NameEN = null;
                Desc  = null;
                Price  = null;
                Imageurl  = null;
                Branch_ID  = null;
            } else {
                Product_ID     = extras.getString("Product_ID");
                NameAR  = extras.getString("NameAR");
                NameEN  = extras.getString("NameEN");
                Desc  = extras.getString("Desc");
                Price  = extras.getString("Price");
                Imageurl  = extras.getString("Imageurl");
                Branch_ID  = extras.getString("Branch_ID");
            }
        } else {
            Product_ID     = (String) savedInstanceState.getSerializable("Product_ID");
            NameAR  = (String) savedInstanceState.getSerializable("NameAR");
            NameEN  = (String) savedInstanceState.getSerializable("NameEN");
            Desc  = (String) savedInstanceState.getSerializable("Desc");
            Price  = (String) savedInstanceState.getSerializable("Price");
            Imageurl  = (String) savedInstanceState.getSerializable("Imageurl");
            Branch_ID  = (String) savedInstanceState.getSerializable("Branch_ID");
        }
        DB = new DBHelper(this);
        Init();
        FillData();

        CreateViews();
        RelativeLayout RL_AnimeContainer = (RelativeLayout) findViewById(R.id.Add_Product_RL_ImageDescContainer);

//        scaleView(RL_AnimeContainer,0f,6f);
        animateViewToMatchParent(RL_AnimeContainer);


    }
    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        v.startAnimation(anim);
    }
    private void animateViewToMatchParent(final View target) {
        int fromValue = 0;
//        int toValue = ((View) target.getParent()).getHeight();
        int toValue = (int) getResources().getDimension(R.dimen.dialog_header_height);
//        int toValue = (int) (getResources().getDimension(R.dimen.dialog_header_height) / getResources().getDisplayMetrics().density);

        ValueAnimator animator = ValueAnimator.ofInt(fromValue, toValue);

        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                target.getLayoutParams().height = (int) animation.getAnimatedValue();
                target.requestLayout();
            }
        });

        animator.start();
    }
    /****** CREATE VIEWWWWS HASSAN BADAWI ******/
    /****** yel3an abo omek veiws 3ala abo om el select ******/
    /****** CREATE VIEWWWWS HASSAN BADAWI ******/

    LinearLayout LL_AddOnSize , LL_AddOnAdditional , LL_AddOnColor ;
    private void CreateViews() {

        /**
         Add_Product_LL_AddOnSize
         * */
        LL_AddOnSize = (LinearLayout) findViewById(R.id.Add_Product_LL_AddOnSize);

        ArrayList<M_CSA_Size> addonSize_ListDump = new ArrayList<>();
        //addonSize_List
        for (M_CSA_Size Size:addonSize_List) {
            if(Size.getProductID().equals(Product_ID)){
                addonSize_ListDump.add(Size);
            }
        }

        if(addonSize_ListDump.isEmpty()){
            LL_AddOnSize.setVisibility(View.GONE);
        }else{
            LL_AddOnSize.setVisibility(View.VISIBLE);
            addAddonsSize(LL_AddOnSize , addonSize_ListDump,"");
        }



        /**
         Add_Product_LL_AddOnAdditional
         * */
        LL_AddOnAdditional = (LinearLayout) findViewById(R.id.Add_Product_LL_AddOnAdditional);

        ArrayList<M_CSA_Additional> addonAdditional_ListDump = new ArrayList<>();
        //addonAdditional_List
        for (M_CSA_Additional Additional : addonAdditional_List) {
            if(Additional.getProductID().equals(Product_ID)){
                addonAdditional_ListDump.add(Additional);
            }
        }

        if(addonAdditional_ListDump.isEmpty()){
            LL_AddOnAdditional.setVisibility(View.GONE);
        }else{
            LL_AddOnAdditional.setVisibility(View.VISIBLE);
            addAddonsAdditional(LL_AddOnAdditional , addonAdditional_ListDump,"");
        }


        /**
         Add_Product_LL_AddOnColor
         * */

        LL_AddOnColor = (LinearLayout) findViewById(R.id.Add_Product_LL_AddOnColor);

        ArrayList<M_CSA_Color> addonColor_ListDump = new ArrayList<>();
        //addonColor_List
        for (M_CSA_Color Color : addonColor_List) {
            if(Color.getProductID().equals(Product_ID)){
                addonColor_ListDump.add(Color);
            }
        }

        if(addonColor_ListDump.isEmpty()){
            LL_AddOnColor.setVisibility(View.GONE);
        }else{
            LL_AddOnColor.setVisibility(View.VISIBLE);
            addAddonsColor(LL_AddOnColor , addonColor_ListDump,"");
        }




    }
    String currentSize_ID = "" ;
    String currentSize_name = "" ;
    private void addAddonsSize(LinearLayout ll_addOnSize, final ArrayList<M_CSA_Size> addon_listDumpSize , String SelectedID) {
        ll_addOnSize.removeAllViews();
        addheaderTVExist(LL_AddOnSize,getResources().getText(R.string.Sizes).toString());

        for (final M_CSA_Size Size : addon_listDumpSize) {
            LayoutInflater inflater = LayoutInflater.from(this);
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.item_addprod_csa, null, false);

            final AppCompatImageView IV_Selected = layout.findViewById(R.id.item_addprod_csa_IV_Selected);

            ((TextView) layout.findViewById(R.id.item_addprod_csa_TV_AddonName)).setText(EN_OR_AR(this,Size.getNameEN(),Size.getNameAR()));
            ((TextView) layout.findViewById(R.id.item_addprod_csa_TV_AddonPrice)).setText("+"+Size.getPrice() + " " + constant.getcurrency(this));

            if(Size.getID().equals(SelectedID) && !currentSize_ID.equals(SelectedID)){
                IV_Selected.setColorFilter(ContextCompat.getColor(AddProduct.this, R.color.colorPrimary));
                lastSize = Double.valueOf(Size.getPrice());
                currentSize_ID = SelectedID;
                currentSize_name = EN_OR_AR(this,Size.getNameEN(),Size.getNameAR());
            }else if (Size.getID().equals(SelectedID) && currentSize_ID.equals(SelectedID)){
                currentSize_ID = "";
                currentSize_name = "";
                lastSize = 0.0;
            }

            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) layout.findViewById(R.id.item_addprod_csa_lyt_parent);


            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAddonsSize(LL_AddOnSize , addon_listDumpSize, Size.getID());
//                    IV_Selected.setColorFilter(ContextCompat.getColor(AddProduct.this, R.color.colorPrimary));
                }
            });
            ll_addOnSize.addView(layout);
        }
        calctotal_top();
    }
    String currentAdditional_ID = "" ;
    String currentAdditional_name = "" ;
    private void addAddonsAdditional(LinearLayout ll_addOnAdditional, final ArrayList<M_CSA_Additional> addon_listDumpAdditional , String SelectedID) {
        ll_addOnAdditional.removeAllViews();
        addheaderTVExist(LL_AddOnAdditional,getResources().getText(R.string.Additionals).toString());

        for (final M_CSA_Additional Additional : addon_listDumpAdditional) {
            LayoutInflater inflater = LayoutInflater.from(this);
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.item_addprod_csa, null, false);

            final AppCompatImageView IV_Selected = layout.findViewById(R.id.item_addprod_csa_IV_Selected);

            ((TextView) layout.findViewById(R.id.item_addprod_csa_TV_AddonName)).setText(EN_OR_AR(this,Additional.getNameEN(),Additional.getNameAR()));
            ((TextView) layout.findViewById(R.id.item_addprod_csa_TV_AddonPrice)).setText("+"+Additional.getPrice() + " " + constant.getcurrency(this));

            if(Additional.getID().equals(SelectedID) && !currentAdditional_ID.equals(SelectedID)){
                IV_Selected.setColorFilter(ContextCompat.getColor(AddProduct.this, R.color.colorPrimary));
                lastAdditional = Double.valueOf(Additional.getPrice());
                currentAdditional_ID = SelectedID;
                currentAdditional_name = EN_OR_AR(this,Additional.getNameEN(),Additional.getNameAR());
            }else if (Additional.getID().equals(SelectedID) && currentAdditional_ID.equals(SelectedID)){
                currentAdditional_ID = "";
                currentAdditional_name = "";
                lastAdditional = 0.0;
            }

            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) layout.findViewById(R.id.item_addprod_csa_lyt_parent);


            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAddonsAdditional(LL_AddOnAdditional , addon_listDumpAdditional, Additional.getID());
//                    IV_Selected.setColorFilter(ContextCompat.getColor(AddProduct.this, R.color.colorPrimary));
                }
            });
            ll_addOnAdditional.addView(layout);
        }
        calctotal_top();
    }
    String currentColor_ID = "" ;
    String currentColor_name = "" ;
    private void addAddonsColor(LinearLayout ll_addOnColor, final ArrayList<M_CSA_Color> addon_listDumpColor , String SelectedID) {
        ll_addOnColor.removeAllViews();
        addheaderTVExist(LL_AddOnColor,getResources().getText(R.string.Colors).toString());

        for (final M_CSA_Color Color : addon_listDumpColor) {
            LayoutInflater inflater = LayoutInflater.from(this);
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.item_addprod_csa, null, false);

            final AppCompatImageView IV_Selected = layout.findViewById(R.id.item_addprod_csa_IV_Selected);

            ((TextView) layout.findViewById(R.id.item_addprod_csa_TV_AddonName)).setText(EN_OR_AR(this,Color.getNameEN(),Color.getNameAR()));
            ((TextView) layout.findViewById(R.id.item_addprod_csa_TV_AddonPrice)).setText("+"+Color.getPrice() + " " + constant.getcurrency(this));

            if(Color.getID().equals(SelectedID) && !currentColor_ID.equals(SelectedID)){
                IV_Selected.setColorFilter(ContextCompat.getColor(AddProduct.this, R.color.colorPrimary));
                lastColor = Double.valueOf(Color.getPrice());
                currentColor_ID = SelectedID;
                currentColor_name = EN_OR_AR(this,Color.getNameEN(),Color.getNameAR());
            }else if (Color.getID().equals(SelectedID) && currentColor_ID.equals(SelectedID)){
                currentColor_ID = "";
                currentColor_name = "";
                lastColor = 0.0;
            }

            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) layout.findViewById(R.id.item_addprod_csa_lyt_parent);


            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAddonsColor(LL_AddOnColor , addon_listDumpColor, Color.getID());
//                    IV_Selected.setColorFilter(ContextCompat.getColor(AddProduct.this, R.color.colorPrimary));
                }
            });
            ll_addOnColor.addView(layout);
        }
        calctotal_top();
    }
    private void addheaderTVExist(LinearLayout ll_addOn, String headerName) {
        TextView tvID = new TextView(this);
        tvID.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        tvID.setPadding(5, 10, 5, 10);
        tvID.setGravity(Gravity.CENTER);
        Typeface TF = ResourcesCompat.getFont( this,R.font.cairo);
        tvID.setTypeface(TF);
        tvID.setTextColor(ContextCompat.getColor(this, R.color.White));
        tvID.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        tvID.setText(headerName);
        ll_addOn.addView(tvID);

    }

    /****** END VIEWWWWS HASSAN BADAWI ******/
    /****** yel3an abo omek veiws 3ala abo om el select ******/
    /****** END VIEWWWWS HASSAN BADAWI ******/






    private void FillData() {

        lastprice_selected = Double.valueOf(Price);

        TV_ProductName.setText(EN_OR_AR(this,NameEN,NameAR)); //name
        ProductDetails.setText(Desc); //desc
        ProductDetails.setMovementMethod(new ScrollingMovementMethod());   //desc scroll
        calctotal_top(); //price TV_ProductPrice
        utility.displayImageOriginal(this,IV_ProductImage, Imageurl);//image
        IV_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });// close event

        IB_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int CurrentQuantity = Integer.parseInt(TV_Quanitity.getText().toString());
                CurrentQuantity += 1 ;
                TV_Quanitity.setText(CurrentQuantity+"");
                calctotal_top();
            }
        });// +++++Quantity event
        IB_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int CurrentQuantity = Integer.parseInt(TV_Quanitity.getText().toString());
                 if(CurrentQuantity == 1){
                    return;
                 }
                CurrentQuantity -= 1 ;
                TV_Quanitity.setText(CurrentQuantity+"");
                calctotal_top();
            }
        });// -----Quantity event

    }



    Double lastprice_selected = 0.0 ;
    Double totalprice = 0.0 ;
    Double lastColor = 0.0 ;
    Double lastSize = 0.0 ;
    Double lastAdditional = 0.0 ;
    private void calctotal_top(){
        Double quant = Double.valueOf(TV_Quanitity.getText().toString());
                totalprice = quant * ( lastprice_selected + lastColor + lastSize + lastAdditional );
        String totalsrt = totalprice+"";
        Log.d("Total_Price",quant * lastprice_selected + " = " + totalsrt);

        TV_ProductPrice.setText(totalsrt + " " + constant.getcurrency(this));


    }









}

















