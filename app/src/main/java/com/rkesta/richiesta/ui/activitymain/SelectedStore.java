package com.rkesta.richiesta.ui.activitymain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rkesta.richiesta.BuildConfig;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.adapters.home.StorCateProdBaseAdapter;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Additional;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Color;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Size;
import com.rkesta.richiesta.model.home.M_Expandablecategory;
import com.rkesta.richiesta.model.home.M_Product;
import com.rkesta.richiesta.util.ImageFullScreen;
import com.rkesta.richiesta.util.utility;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiesta.app.sharedObjects.GO_TO_position;
import static com.rkesta.richiesta.app.sharedObjects.TO_position;
import static com.rkesta.richiesta.app.sharedObjects.addonAdditional_List;
import static com.rkesta.richiesta.app.sharedObjects.addonColor_List;
import static com.rkesta.richiesta.app.sharedObjects.addonSize_List;
import static com.rkesta.richiesta.app.sharedObjects.SharedSelectedStore_ID;
import static com.rkesta.richiesta.app.sharedObjects.SharedSelectedStore_name;
import static com.rkesta.richiesta.app.sharedObjects.SharedSelectedStore_IMG;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class SelectedStore extends AppCompatActivity {

    AppCompatImageView IV_close , IV_basket , IV_share , IV_nearme_location , IV_call , IV_Refresh ;
    ImageView IV_pic ;
    TextView StoreName , StoreTags ;
    RecyclerView RV_StoreCategoryProd ;

    StorCateProdBaseAdapter CategoryProd_Adapter ;

    String Store_ID = "";
    String Store_nameAR = "";
    String Store_nameEN = "";
    String Store_tagAR = "";
    String Store_tagEN = "";
    String Store_Image = "";

    ArrayList<M_Expandablecategory> ExpandableCategory_list = new ArrayList<M_Expandablecategory>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_store);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                 Store_ID = null;
                 Store_nameAR = null;
                 Store_nameEN = null;
                 Store_tagAR = null;
                 Store_tagEN = null;
                 Store_Image = null;
            } else {
                 Store_ID     = extras.getString("Store_ID");
                 Store_nameAR = extras.getString("Store_nameAR");
                 Store_nameEN = extras.getString("Store_nameEN");
                 Store_tagAR  = extras.getString("Store_tagAR");
                 Store_tagEN  = extras.getString("Store_tagEN");
                 Store_Image  = extras.getString("Store_Image");
            }
        } else {
            Store_ID     = (String) savedInstanceState.getSerializable("Store_ID");
            Store_nameAR = (String) savedInstanceState.getSerializable("Store_nameAR");
            Store_nameEN = (String) savedInstanceState.getSerializable("Store_nameEN");
            Store_tagAR  = (String) savedInstanceState.getSerializable("Store_tagAR");
            Store_tagEN  = (String) savedInstanceState.getSerializable("Store_tagEN");
            Store_Image  = (String) savedInstanceState.getSerializable("Store_Image");

        }
        Init();
        initRecyclerView();

        filldata();

        SharedSelectedStore_ID   = Store_ID;
        SharedSelectedStore_name = EN_OR_AR(SelectedStore.this,Store_nameEN,Store_nameAR);
        SharedSelectedStore_IMG  = Store_Image;

        new Async_ProductDetailsByStoreId(Store_ID).execute();

    }

    private void Init() {
        IV_close  = findViewById(R.id.Selected_Store_IV_close);
        IV_basket = findViewById(R.id.Selected_Store_IV_basket);
        IV_share  = findViewById(R.id.Selected_Store_IV_share);
        IV_nearme_location  = findViewById(R.id.Selected_Store_IV_nearme_location);
        IV_call  = findViewById(R.id.Selected_Store_IV_call);
        IV_Refresh  = findViewById(R.id.Selected_Store_IV_Refresh);
        IV_pic  = findViewById(R.id.Selected_Store_IV_pic);

        IV_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectedStore.this, ImageFullScreen.class);
                i.putExtra("Imageurl", Store_Image);
                startActivity(i);
            }
        });

        IV_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        IV_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GO_TO_position = true;
                TO_position = 1 ;
                onBackPressed();
            }
        });


        IV_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Richiesta");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        IV_nearme_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri =  "http://maps.google.com/maps?q=loc:"+ BranchLatitude+","+ BranchLongitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        IV_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                phonecall();
                onCall();
            }

        });
        IV_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Async_ProductDetailsByStoreId(Store_ID).execute();
            }
        });





    }

    public void onCall() {

        String storephone = BranchPhone;
        if(BranchPhone.isEmpty()){
            storephone = BranchCell;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+storephone)));
        }
    }

    private void initRecyclerView() {
        RV_StoreCategoryProd  = findViewById(R.id.Selected_Store_RV_StoreCategoryProd);
        CategoryProd_Adapter = new StorCateProdBaseAdapter(this,ExpandableCategory_list);
        RV_StoreCategoryProd.setLayoutManager(new LinearLayoutManager(this));
        RV_StoreCategoryProd.setAdapter(CategoryProd_Adapter);

    }

    private void filldata() {
        StoreName  = findViewById(R.id.Selected_Store_TV_StoreName);
        StoreTags  = findViewById(R.id.Selected_Store_TV_StoreTags);


        utility.displayImageOriginal(SelectedStore.this , IV_pic , Store_Image);


        StoreName.setText(EN_OR_AR(SelectedStore.this,Store_nameEN,Store_nameAR));
        StoreTags.setText(EN_OR_AR(SelectedStore.this,Store_tagEN,Store_tagAR));
    }

    String BranchPhone = "";
    String BranchCell = "";
    String BranchLatitude = "";
    String BranchLongitude = "";
    private ProgressDialog pDialog;
    class Async_ProductDetailsByStoreId extends AsyncTask<String, String, String> {

        String store_id = "";

        public Async_ProductDetailsByStoreId(String store_ID) {
            this.store_id = store_ID;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SelectedStore.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        private ArrayList<HashMap<String, String>> ProductDetails_list = new ArrayList<HashMap<String, String>>();
        private ArrayList<HashMap<String, String>> CategoriesByStore_list = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            ProductDetails_list = WS.Selectsprk_vw_MayProductDetailsByStoreId(store_id);
            CategoriesByStore_list = WS.Selectsprk_SelectRK_CategoriesByStoreId(store_id);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            if(ProductDetails_list.size() > 0) {
                BranchPhone = ProductDetails_list.get(0).get("BranchPhone");
                BranchCell = ProductDetails_list.get(0).get("BranchCell");
                BranchLatitude = ProductDetails_list.get(0).get("BranchLatitude");
                BranchLongitude = ProductDetails_list.get(0).get("BranchLongitude");
            }
//            "BranchLatitude" -> "31.208300"
//            "BranchLongitude" -> "29.913715"
//            "BranchCell" -> "01111155259"
//            "BranchPhone" -> "01111155259"

//            "ProdDetBranchId" -> "28"
//            "BranchAddress" -> "Louisville, Kentucky"
//            "ProductSalesUnitPrice" -> "186.99"
//            "ProdDetHexCode" -> ""
//            "ProdDetProdID" -> "1285"
//            "ProdDetNameEN" -> ""
//            "ProdDetStoreId" -> "43"
//            "MeasurmentUnitAr" -> ""
//            "ProdDetNameAR" -> ""
//            "ProdDetCategoryId" -> "70"
//            "ProdDetDetId" -> ""
//            "ProdNameEN" -> "FESTIVE BUCKET 9 PCS "
//            "ProductDescEn" -> "9 pcs Chicken + 1 Family Fries + 1 L Coleslaw + 4 buns + 1 L Pepsi\n"
//            "ProductDetUnitPrice" -> ""
//            "ProdDetType" -> "ProdAdditional"
//            "ProdNameAR" -> "باكت الاحتفال 9 قطع "
//            "ProductDescAr" -> "9 قطع دجاج + 1 بطاطس عائلي + 1 كول سلو كبير + 4 خبز + 1 لتر بيبسي (مشروب)\n"
//            "ProductPic" -> "http://cp.rkesta.com/prdPic/~/prdPic/20200513034731Store22.png"
//            "ProdDetUnitType" -> ""
//            "ProdDetID" -> ""
//            "MeasurmentUnit" -> ""
//            "BranchDescription" -> "كنتاكي فرايد تشيكن هي سلسلة مطاعم أمريكية للوجبات"

//            ID : elem["ID"].element?.text ?? ""
//                    ,NameEN:  elem["CategoryNameEN"].element?.text ?? ""
//                    ,NameAr:  elem["CategoryNameAr"].element?.text ?? ""
//                    ,Notes:  elem["CategoryNotes"].element?.text ?? ""

            if (!CategoriesByStore_list.isEmpty()) { //if category not empty
                ArrayList<M_Product> DummyProduct_list = new ArrayList<M_Product>() ;

                for (HashMap<String, String> Category : CategoriesByStore_list) {

                    DummyProduct_list = new ArrayList<M_Product>() ;

                    for (HashMap<String, String> product : ProductDetails_list) {

                        if(Category.get("ID").equals(product.get("ProdDetCategoryId"))){
                            if(DummyProduct_list.isEmpty()){
                            DummyProduct_list.add(new M_Product(product.get("ProdDetProdID")
                                    ,product.get("ProdNameEN")
                                    ,product.get("ProdNameAR")
                                    ,product.get("ProductDescEn")
                                    ,product.get("ProductDescAr")
                                    ,product.get("ProductSalesUnitPrice")
                                    ,product.get("ProductPic")
                                    ,product.get("MeasurmentUnit")
                                    ,product.get("MeasurmentUnitAr")
                                    ,product.get("ProdDetCategoryId")
                                    ,product.get("ProdDetBranchId")));
                            }else{
                                boolean ID_IS_Repeated = false;
                                for (int i = 0; i < DummyProduct_list.size(); i++) {
                                    if(DummyProduct_list.get(i).getProduct_ID().equals(product.get("ProdDetProdID"))){
                                        ID_IS_Repeated = true ;
                                    }
                                }

                                if(ID_IS_Repeated){
                                    //do nothing for now
                                }else{
                                    DummyProduct_list.add(new M_Product(product.get("ProdDetProdID")
                                            ,product.get("ProdNameEN")
                                            ,product.get("ProdNameAR")
                                            ,product.get("ProductDescEn")
                                            ,product.get("ProductDescAr")
                                            ,product.get("ProductSalesUnitPrice")
                                            ,product.get("ProductPic")
                                            ,product.get("MeasurmentUnit")
                                            ,product.get("MeasurmentUnitAr")
                                            ,product.get("ProdDetCategoryId")
                                            ,product.get("ProdDetBranchId")));
                                }
                            }

                        }

//                        if (ExpandableCategory_list.isEmpty()) {//if expandable list empty add first item direct
//                        }else{
//                        }

                    } // end for product
                    ExpandableCategory_list.add(new M_Expandablecategory(EN_OR_AR(SelectedStore.this,Category.get("CategoryNameEN"),Category.get("CategoryNameAr")), DummyProduct_list, true));
                }// end for cat



                //fillCSA()
                addonColor_List.clear();
                addonSize_List.clear();
                addonAdditional_List.clear();
                for (HashMap<String, String> product : ProductDetails_list) {
                    if ( product.get("ProdDetType").equals("ProdColor") && !(product.get("ProdDetID").equals(""))){

                        addonColor_List.add(
                                new M_CSA_Color(
                                        product.get("ProdDetProdID"),
                                        product.get("ProdDetID"),
                                        product.get("ProdDetHexCode"),
                                        product.get("ProdDetNameEN"),
                                        product.get("ProdDetNameAR"),
                                        product.get("ProductDetUnitPrice")
                                )
                        );

                    }else if ( product.get("ProdDetType").equals("ProdSize") && !(product.get("ProdDetID").equals(""))){

                        addonSize_List.add(
                                new M_CSA_Size(
                                        product.get("ProdDetProdID"),
                                        product.get("ProdDetID"),
                                        product.get("ProdDetHexCode"),
                                        product.get("ProdDetNameEN"),
                                        product.get("ProdDetNameAR"),
                                        product.get("ProductDetUnitPrice")
                                )
                        );

                    }else if ( product.get("ProdDetType").equals("ProdAdditional") && !(product.get("ProdDetID").equals(""))){

                        addonAdditional_List.add(
                                new M_CSA_Additional(
                                        product.get("ProdDetProdID"),
                                        product.get("ProdDetID"),
                                        product.get("ProdDetHexCode"),
                                        product.get("ProdDetNameEN"),
                                        product.get("ProdDetNameAR"),
                                        product.get("ProductDetUnitPrice")
                                )
                        );

                    }
                }




            }
            CategoryProd_Adapter.notifyDataSetChanged();

            pDialog.dismiss();
        }
    }








    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

}