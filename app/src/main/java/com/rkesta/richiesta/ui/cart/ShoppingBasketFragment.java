package com.rkesta.richiesta.ui.cart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.SplashScreen;
import com.rkesta.richiesta.adapters.cart.AdapterCart;
import com.rkesta.richiesta.adapters.spinner_idname_adapter;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.app.DBHelper;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.custom_dialog.AlertOrderCompleteDialog;
import com.rkesta.richiesta.model.cart.M_Cart;
import com.rkesta.richiesta.ui.settings.MyAddress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.rkesta.richiesta.app.sharedObjects.IS_Guest;
import static com.rkesta.richiesta.app.sharedObjects.MaxDeliveryAmount;
import static com.rkesta.richiesta.app.sharedObjects.userEN;
import static com.rkesta.richiesta.app.sharedObjects.userEmail;
import static com.rkesta.richiesta.app.sharedObjects.userPhone;
import static com.rkesta.richiesta.app.sharedObjects.user_ID;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.get_pref;
import static com.rkesta.richiesta.util.utility.roundup;

public class ShoppingBasketFragment extends Fragment {

    AppCompatEditText ET_Note;
    TextView TV_OrderTotal, TV_promoStatus;
    TextView TV_DeliveryCharges;
    TextView TV_Total;
    Button BTN_PlaceOrder, BT_checkPromoCode;
    AppCompatImageView IV_delete;
    EditText ET_PromoCode;

    AdapterCart AdapterCartItems;
    RecyclerView RV_CartItems;
    DBHelper DB;
    ArrayList<M_Cart> ArrayList_Cart = new ArrayList<M_Cart>();

    AppCompatSpinner spin_Pickaddress;
    spinner_idname_adapter Adapter_PickAddress;
    String GenSONumber = "";
    String promCode = "";

    Double totalorder = 0.0;
    Double ShippingRate = 0.0;
    Double orderPrice = 0.0;
    double discount = 0.0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        Init(root);
        //Refresh_cart();
        if(!IS_Guest){
            new Async_getUser_DetailsByUserID().execute();
            if (!ArrayList_Cart.isEmpty()) {
                new Async_GenSONumber(ArrayList_Cart.get(0).getProducts_ID() , ArrayList_Cart.get(0).getStore_ID()).execute();
            }
            // cart_item[0].Products_ID // cart_item[0].Store_ID
        }

        return root;
    }




    private void Init(View root) {

        DB = new DBHelper(getActivity());


        spin_Pickaddress = (AppCompatSpinner) root.findViewById(R.id.cart_spin_Pickaddress);


        ET_Note = root.findViewById(R.id.cart_ET_Note);
        TV_OrderTotal = root.findViewById(R.id.cart_TV_OrderTotal);
        TV_DeliveryCharges = root.findViewById(R.id.cart_TV_DeliveryCharges);
        TV_Total = root.findViewById(R.id.cart_TV_Total);
        BTN_PlaceOrder = root.findViewById(R.id.cart_BTN_PlaceOrder);
        TV_promoStatus = root.findViewById(R.id.promo_status);
        BT_checkPromoCode = root.findViewById(R.id.cart_BTN_check_promo_code);
        ET_PromoCode = root.findViewById(R.id.cart_ET_promocode);

        IV_delete = root.findViewById(R.id.cart_IV_delete);

        RV_CartItems = (RecyclerView) root.findViewById(R.id.cart_RV_CartItems);

        //RV_category.setLayoutManager(new GridLayoutManager(act, Tools.getGridSpanCount(act)));
        RV_CartItems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RV_CartItems.setHasFixedSize(true);

        IV_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCart();
            }
        });


        BTN_PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlaceOrderFire();
            }
        });

        ET_PromoCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (TextUtils.isEmpty(s))
                    BT_checkPromoCode.setEnabled(false);

                else BT_checkPromoCode.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(s))
                    BT_checkPromoCode.setEnabled(false);

                else BT_checkPromoCode.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        BT_checkPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BT_checkPromoCode.getText().equals(getResources().getString(R.string.change))){

                    Refresh_cart();

                    BT_checkPromoCode.setText(getResources().getString(R.string.check));
                    BT_checkPromoCode.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                    ET_PromoCode.setEnabled(true);
                }

                else
                    new Async_CheckPromoCode(ET_PromoCode.getText().toString()).execute();
            }
        });

    }


    public void Refresh_cart() {

        ArrayList_Cart = DB.getCart();

        //set data and list adapter
        AdapterCartItems = new AdapterCart(getActivity(), RV_CartItems, ArrayList_Cart);

        RV_CartItems.setAdapter(AdapterCartItems);

        initPromoCodeLayout();

        AdapterCartItems.setOnItemClickListener(new AdapterCart.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final M_Cart obj, int position) {

                String prodName = EN_OR_AR(getActivity(), obj.getProduct_nameEN(), obj.getProduct_nameAR());
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                DB.deleteRow(DB.Cart_Table_Name, DB.Cart_ID, obj.getCart_ID());
                                Refresh_cart();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(EN_OR_AR(getActivity(), "are you sure you wan't to delete " + prodName + " ?", "هل أنت متأكد أنك تريد حذف" + prodName + "؟")).setPositiveButton(EN_OR_AR(getActivity(), "Yes", "نعم"), dialogClickListener)
                        .setNegativeButton(EN_OR_AR(getActivity(), "No", "لا"), dialogClickListener).show();

            }
        });

        if (ArrayList_Cart.isEmpty()) {
            TV_OrderTotal.setText(0.0 + " " + constant.getcurrency(getActivity()));
            TV_DeliveryCharges.setText("");
            TV_Total.setText(0.0 + " " + constant.getcurrency(getActivity()));
        }else{
            // new Async_getRK_BranchDetailID(ArrayList_Cart.get(0).getBranch_ID()).execute();
            new Async_GenSONumber(ArrayList_Cart.get(0).getProducts_ID() , ArrayList_Cart.get(0).getStore_ID()).execute();


        }


    }

    private void initPromoCodeLayout(){

        ET_PromoCode.setText("");
        TV_promoStatus.setText("");
        BT_checkPromoCode.setText(getResources().getString(R.string.check));
        BT_checkPromoCode.setBackgroundColor(getResources().getColor(R.color.valid_promo_status_txt));

    }

    private void clearCart() {

        if (ArrayList_Cart.isEmpty()) {
//            self.showToast(message: AppConfig.En_OR_Ar(EN: "there aren't any product in the basket", AR: "لا يوجد أي منتج في السلة"))
            Toast.makeText(getActivity(), EN_OR_AR(getActivity(), "there aren't any product in the basket"
                    , "لا يوجد أي منتج في السلة"), Toast.LENGTH_SHORT).show();
            return;
        }

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        DB.deleteallfrom(DB.Cart_Table_Name);

                        Refresh_cart();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(EN_OR_AR(getActivity(), "are you sure you want to clear the Basket?"
                , "هل أنت متأكد أنك تريد مسح السلة؟")).setPositiveButton(EN_OR_AR(getActivity(), "Yes", "نعم"), dialogClickListener)
                .setNegativeButton(EN_OR_AR(getActivity(), "No", "لا"), dialogClickListener).show();

    }


    private void FillSpinner() {
        HashMap<String, String> map ;
        Addresses_List.clear();
        for (HashMap<String, String> Address : User_Details_List) {
            map = new HashMap<>();
            map = Address;
            map.put("id", Address.get("ID"));
            map.put("name", Address.get("RKAddress"));
            Addresses_List.add(map);
        }

        //Creating the ArrayAdapter instance having the country list
        Adapter_PickAddress = new spinner_idname_adapter(getActivity(), Addresses_List);
        spin_Pickaddress.setAdapter(Adapter_PickAddress);

        spin_Pickaddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                selected_Address = Addresses_List.get(position);

                //new Async_CalcDeliveryRate(ArrayList_Cart.get(0).getBranch_ID()).execute();
                Refresh_cart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void setPromoStatusTxtView(boolean isValid, String message){

        if (isValid){

            TV_promoStatus.setText(message);
            TV_promoStatus.setTextColor(getResources().getColor(R.color.valid_promo_status_txt));

            BT_checkPromoCode.setText(getResources().getString(R.string.change));
            BT_checkPromoCode.setBackgroundColor(getResources().getColor(R.color.invalid_promo_status_txt));

            ET_PromoCode.setEnabled(false);

        }

        else {

            TV_promoStatus.setText(message);
            TV_promoStatus.setTextColor(getResources().getColor(R.color.invalid_promo_status_txt));
        }
    }


    private void PlaceOrderFire() {
        if (ArrayList_Cart.isEmpty()) {
            Toast.makeText(getActivity(), EN_OR_AR(getActivity(),"there aren't any product in the basket",
                    "لا يوجد أي منتج في السلة"), Toast.LENGTH_SHORT).show();
            return;
        }

        if(IS_Guest){
            Toast.makeText(getActivity(), EN_OR_AR(getActivity(),"Please register with us to complete your order", "برجاء التسجيل معنا لاكمال طلبك"), Toast.LENGTH_LONG).show();
            Intent i = new Intent(getContext(), SplashScreen.class);
// set the new task and clear flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return;
        }
//        selected_ID_Address = Addresses_List.get(position).get("ID");

        if(Addresses_List.size() == 0){
            Toast.makeText(getActivity(),EN_OR_AR(getActivity(),"Please add an address","يرجى إضافة عنوان" ), Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), MyAddress.class);
            startActivity(i);
            return;
        }

        if(selected_Address == null){
            Toast.makeText(getActivity(),EN_OR_AR(getActivity(),"please choose your address","يرجى اختيار عنوانك" ), Toast.LENGTH_LONG).show();
            return;
        }

        new Async_CheckValidateAdress(selected_Address.get("Longitude"), selected_Address.get("Latitude")).execute();



    }


    private void configPromoCode(HashMap<String,String> promoMap){

        //// Is Gift ////

        if (Boolean.parseBoolean(promoMap.get("IsGift"))){

            if (Integer.parseInt(promoMap.get("QtyLimit")) == 0){

                setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
            }
            else if (Integer.parseInt(promoMap.get("QtyLimit")) <= totalorder){

                setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
            }
            else {

                setPromoStatusTxtView(true, promoMap.get("GiftDesc"));
            }
        }

        //// Is Free Order /////

        else if (Boolean.parseBoolean(promoMap.get("FreeOrder"))){

            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));

            ShippingRate = 0.0;

        }

        else{

            /////////////////// Discount on order  ///////////////////////////

            if (promoMap.get("AppliesTo").equals("Ord")){

                ///////// Is Prize ////////////

                if (!promoMap.get("Prize").equals("0")){

                    /// Percent Prize ///

                    if (Boolean.parseBoolean(promoMap.get("PrizeType"))){

                        if (Double.parseDouble(promoMap.get("QtyLimit")) <= totalorder){

                            discount = ((Double.parseDouble(promoMap.get("Amount"))) / 100) * orderPrice;

                            if (!promoMap.get("MaxPercentAmountLimit").equals("0") &&
                                    discount > Double.parseDouble(promoMap.get("MaxPercentAmountLimit")))
                                discount = Double.parseDouble(promoMap.get("MaxPercentAmountLimit"));

                            orderPrice -= discount;

                            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
                        }

                        //// above the qnt limit ////

                        else setPromoStatusTxtView(true, EN_OR_AR(getContext(), "Add " + (Double.parseDouble(
                                promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency(getActivity()),
                                "قم بإضافة " + (Double.parseDouble(promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency
                                        (getActivity())));
                    }

                    ////  Fixed Prize ///

                    else {

                        if (Double.parseDouble(promoMap.get("QtyLimit")) <= totalorder){

                            discount = (Double.parseDouble(promoMap.get("Prize")));

                            orderPrice -= discount;

                            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
                        }

                        //// above the qnt limit ////

                        else setPromoStatusTxtView(true, "add more");
                    }
                }


                ///////// Is Amount ////////////

                else {

                    /// Percent Amount ///

                    if (Boolean.parseBoolean(promoMap.get("AmountType"))){

                        if (Double.parseDouble(promoMap.get("QtyLimit")) <= totalorder){

                            discount = ((Double.parseDouble(promoMap.get("Amount"))) / 100) * orderPrice;

                            if (!promoMap.get("MaxPercentAmountLimit").equals("0") &&
                                    discount > Double.parseDouble(promoMap.get("MaxPercentAmountLimit")))
                                discount = Double.parseDouble(promoMap.get("MaxPercentAmountLimit"));

                            orderPrice -= discount;

                            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
                        }

                        //// above the qnt limit ////

                        else setPromoStatusTxtView(true, EN_OR_AR(getContext(), "Add " + (Double.parseDouble(
                                promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency(getActivity()),
                                "قم بإضافة " + (Double.parseDouble(promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency
                                        (getActivity())));
                    }

                    ////  Fixed Amount ///

                    else {

                        if (Double.parseDouble(promoMap.get("QtyLimit")) <= totalorder){

                            discount = (Double.parseDouble(promoMap.get("Amount")));

                            orderPrice -= discount;

                            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
                        }

                        //// above the qnt limit ////

                        else setPromoStatusTxtView(true, EN_OR_AR(getContext(), "Add " + (Double.parseDouble(
                                promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency(getActivity()),
                                "قم بإضافة " + (Double.parseDouble(promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency
                                        (getActivity())));
                    }
                }
            }



            /////////////////// Discount on Del  ///////////////////////////


            else {

                ///////// Is Prize ////////////

                if (!promoMap.get("Prize").equals("0")){

                    /// Percent Prize ///

                    if (Boolean.parseBoolean(promoMap.get("PrizeType"))){

                        if (Double.parseDouble(promoMap.get("QtyLimit")) <= totalorder){

                            discount = (Double.parseDouble(promoMap.get("Prize")) / 100) * orderPrice;

                            if (!promoMap.get("MaxPercentAmountLimit").equals("0") &&
                                    discount > Double.parseDouble(promoMap.get("MaxPercentAmountLimit")))
                                discount = Double.parseDouble(promoMap.get("MaxPercentAmountLimit"));

                            ShippingRate -= discount;

                            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
                        }

                        //// above the qnt limit ////

                        else setPromoStatusTxtView(true, EN_OR_AR(getContext(), "Add " + (Double.parseDouble(
                                promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency(getActivity()),
                                "قم بإضافة " + (Double.parseDouble(promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency
                                        (getActivity())));
                    }

                    ////  Fixed Prize ///

                    else {

                        if (Double.parseDouble(promoMap.get("QtyLimit")) <= totalorder){

                            discount = (Double.parseDouble(promoMap.get("Prize")));

                            ShippingRate -= discount;

                            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
                        }

                        //// above the qnt limit ////

                        else setPromoStatusTxtView(true, EN_OR_AR(getContext(), "Add " + (Double.parseDouble(
                                promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency(getActivity()),
                                "قم بإضافة " + (Double.parseDouble(promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency
                                        (getActivity())));
                    }
                }


                ///////// Is Amount ////////////

                else {

                    /// Percent Amount ///

                    if (Boolean.parseBoolean(promoMap.get("AmountType"))){

                        if (Double.parseDouble(promoMap.get("QtyLimit")) <= totalorder){

                            discount = (Double.parseDouble(promoMap.get("Amount")) / 100) * orderPrice;

                            //discount = 100.0
                            // MaxPercentAmountLimit = 0
                            if (!promoMap.get("MaxPercentAmountLimit").equals("0") &&
                                    discount > Double.parseDouble(promoMap.get("MaxPercentAmountLimit"))){

                                discount = Double.parseDouble(promoMap.get("MaxPercentAmountLimit"));

                            }


                            ShippingRate -= discount;

                            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
                        }

                        //// above the qnt limit ////

                        else setPromoStatusTxtView(true, EN_OR_AR(getContext(), "Add " + (Double.parseDouble(
                                promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency(getActivity()),
                                "قم بإضافة " + (Double.parseDouble(promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency
                                        (getActivity())));
                    }

                    ////  Fixed Amount ///

                    else {

                        if (Double.parseDouble(promoMap.get("QtyLimit")) <= totalorder){

                            discount = (Double.parseDouble(promoMap.get("Amount")));

                            ShippingRate -= discount;

                            setPromoStatusTxtView(true, promoMap.get("PromoDesc"));
                        }

                        //// above the qnt limit ////

                        else setPromoStatusTxtView(true, EN_OR_AR(getContext(), "Add " + (Double.parseDouble(
                                promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency(getActivity()),
                                "قم بإضافة " + (Double.parseDouble(promoMap.get("QtyLimit")) - totalorder) + constant.getcurrency
                                        (getActivity())));
                    }
                }
            }
        }

        refreshTotalOrderAmount();
    }


    private void refreshTotalOrderAmount(){

        TV_OrderTotal.setText(orderPrice + " " + constant.getcurrency(getActivity()));

        TV_DeliveryCharges.setText(roundup(ShippingRate) + " " + constant.getcurrency(getActivity()));

        totalorder = roundup(0.0 + orderPrice + ShippingRate);

        TV_Total.setText(totalorder + constant.getcurrency(getActivity()));
    }


    class Async_CheckPromoCode extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {

        String promo_code;

        public Async_CheckPromoCode(String prom) {

            this.promo_code = prom;
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected ArrayList<HashMap<String, String>> doInBackground(String... args) {
            WebService WS = new WebService();

            return WS.getCheckPromoCode(promo_code , get_pref(getContext(), constant.KEY_user_ID));

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(ArrayList<HashMap<String, String>> file_url) {

            switch (file_url.get(0).get("Column1")){

                case "" :
                {

                    promo_code = ET_PromoCode.getText().toString();

                    configPromoCode(file_url.get(0));

                    break;
                }

                case "PromoCodeLimitExceeded" :

                    promo_code = "";

                    setPromoStatusTxtView(false, EN_OR_AR(getContext(), "You had exceed the limit of usage this promocode",
                            "لقد تخطيت العدد المسموح به لاستخدام هذه القسيمة"));

                    break;

                case "PromoCodeNotActive" :

                    promo_code = "";

                    setPromoStatusTxtView(false, EN_OR_AR(getContext(), "The promocode isn't active",
                            "قسيمة الخصم غير مفعلة"));
                    break;

                case "PromoCodeExpired" :

                    promo_code = "";

                    setPromoStatusTxtView(false, EN_OR_AR(getContext(), "The promocode is expired",
                            "قسيمة الخصم منتهية الصلاحية"));

                    break;

                case "PromoCodeNotFound" :

                    promo_code = "";

                    setPromoStatusTxtView(false, EN_OR_AR(getContext(), "The promocode is not found",
                            "قسيمة الخصم غير موجودة"));

                    break;

            }


        }

    }


    class Async_CheckValidateAdress extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {

        String longit, latit;
        boolean validate = false;

        public Async_CheckValidateAdress(String longit, String latit) {
            this.longit = longit;
            this.latit = latit;
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected ArrayList<HashMap<String, String>> doInBackground(String... args) {
            WebService WS = new WebService();

            return WS.getCheckValidateAddress(longit , latit, ArrayList_Cart.get(0).getBranch_ID());

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(ArrayList<HashMap<String, String>> file_url) {

            if (file_url.isEmpty()){

                Toast.makeText(getContext(), EN_OR_AR(getActivity(),"Can't deliver to this location",
                        "لا يمكن التوصيل لهذا العنوان" ), Toast.LENGTH_SHORT).show();
            }
            else {

                new Async_InsertRK_SalesOrder(ET_Note.getText().toString()).execute();
            }

        }

    }


    class Async_InsertRK_SalesOrder extends AsyncTask<String, String, String> {

        String Notes = "" ;
        public Async_InsertRK_SalesOrder(String notes) {
            Notes = notes ;
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            for (M_Cart product :ArrayList_Cart ) {

                String idcolor = "0";
                String idadditional = "0";
                String idsize = "0";
                String pricecolor = "0.0";
                String priceadditional = "0.0";
                String pricesize = "0.0";
//                Double priceNOqty = 0.0;

//                priceNOqty += Double.valueOf(product.getProduct_Price());  // unit price
                if(!product.getColor_ID().isEmpty()){
                    idcolor = product.getColor_ID();
//                    priceNOqty += Double(product.Color_price) ?? 0.0 // unit price + Color_price
                    pricecolor = product.getColor_price();
                }
                if(!product.getAdditional_ID().isEmpty()){
                    idadditional = product.getAdditional_ID();
//                    priceNOqty += Double(product.Additional_price) ?? 0.0 // unit price + Additional_price
                    priceadditional = product.getAdditional_price();
                }
                if(!product.getSize_ID().isEmpty()){
                    idsize = product.getSize_ID();
//                    priceNOqty += Double(product.Size_price) ?? 0.0 // unit price + Size_price
                    pricesize = product.getSize_price();
                }

                WS.InsertRK_SalesOrder(GenSONumber ,product.getStore_ID() , product.getBranch_ID() , product.getProducts_ID()  ,
                        idcolor  , idadditional , idsize , pricecolor , priceadditional , pricesize  , product.getProduct_Price() ,
                        product.getQty() , product.getOrderNotes() , user_ID , selected_Address.get("id") , Notes ,
                        "UserID_"+(user_ID)+ "_FromAndroid" , ShippingRate+"");

                WS.InsertOrderSummary(GenSONumber, ShippingRate+"", product.getBranch_ID(), promCode, String.valueOf(discount),
                        Notes);

//                Log.d("GenSONumber : ",GenSONumber +product.getStore_ID() + product.getBranch_ID() + product.getProducts_ID()  + idcolor  + idadditional + idsize + pricecolor + priceadditional + pricesize  + product.getProduct_Price() + product.getQty() + product.getOrderNotes() + user_ID + selected_ID_Address + Notes + "UserID_"+(user_ID)+"_FromAndroid" + ShippingRate+"");
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

            DB.deleteallfrom(DB.Cart_Table_Name);
            Refresh_cart();

            Toast.makeText(getActivity(), EN_OR_AR(getActivity(),"Check My Orders",  "تحقق من طلباتي"), Toast.LENGTH_SHORT).show();

            new Async_SendConfirmationEmail().execute();

        }

    }

    class Async_SendConfirmationEmail extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            Date d = new Date();
            String formattedDate = new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(d);
            WebService WS = new WebService();
            WS.SendConfirmationEmail(GenSONumber,  formattedDate, userEN, userEmail, userPhone,totalorder+"");

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

            AlertOrderCompleteDialog cdd = new AlertOrderCompleteDialog(getActivity(),GenSONumber);
            cdd.show();
            pDialog.dismiss();

        }
    }



    class Async_GenSONumber extends AsyncTask<String, String, String> {

        String Productid="";
        String Storeid="";
        public Async_GenSONumber(String productid, String storeid) {

            this.Productid=productid;
            this.Storeid=storeid;
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            GenSONumber = WS.GenSONumber(Productid,Storeid);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            if (GenSONumber.isEmpty() || GenSONumber.equals("ERROR")) {
                new Async_GenSONumber(ArrayList_Cart.get(0).getProducts_ID() , ArrayList_Cart.get(0).getStore_ID()).execute();
            }

            new Async_CalcDeliveryAmount(ArrayList_Cart.get(0).getBranch_ID(), selected_Address.get("id")).execute();

            Log.d("GenSONumber : ",GenSONumber);
        }
    }



    private ArrayList<HashMap<String, String>> User_Details_List;
    private ArrayList<HashMap<String, String>> Addresses_List = new ArrayList<>();
    HashMap<String, String> selected_Address ;
    private ProgressDialog pDialog;

    class Async_getUser_DetailsByUserID extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            User_Details_List = WS.SelectRK_User_DetailsByUserID(user_ID);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

            if (User_Details_List.size() == 0) {
            } else {
//                StoreCategories_exec(StoreCategories);

                FillSpinner();

            }

            pDialog.dismiss();
        }
    }




//    class Async_getRK_BranchDetailID extends AsyncTask<String, String, String> {
//
//
//        private ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        String ID = "";
//        public Async_getRK_BranchDetailID(String id) {
//            ID = id;
//        }
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        protected String doInBackground(String... args) {
//            WebService WS = new WebService();
//            result = WS.SelectRK_Branch_DetailsByBranchID(ID);
//
//            return null;
//        }
//
//        /**
//         * After completing background task Dismiss the progress dialog
//         **/
//        protected void onPostExecute(String file_url) {
//            if (result.isEmpty()) {
//                RK_BranchDetailID = "0";
//            } else {
//                RK_BranchDetailID = result.get(0).get("ID");
//            }
//
//            new Async_CalcDeliveryRate(ArrayList_Cart.get(0).getBranch_ID()).execute();
//        }
//
//    }


    class Async_CalcDeliveryAmount extends AsyncTask<String, String, String> {


        private String result = new String();
        String ID = "";
        String idAddress;
        public Async_CalcDeliveryAmount(String id, String idAddress) {
            ID = id;
            this.idAddress = idAddress;
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.sprk_CalcDeliveryAmount(ID,idAddress);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            double DeliveryAmount = 0.0;
            if (result.equals("ERROR")) {
                DeliveryAmount = 0.0;
            } else {
                try {
                    DeliveryAmount = Double.parseDouble(result);
                }catch (Exception e){
                    DeliveryAmount = 0.0;
                }
            }
            double sum = 0.0;

            for (M_Cart cart : ArrayList_Cart) {
                sum += Double.parseDouble(cart.getTotalPrice());
            }

            //var DeliveryAmount = AppConfig.WS.sprk_CalcDeliveryRate(RK_BranchID: cart_item[0].Branch_ID , RK_BranchDetailID: RK_BranchDetailID , RK_UserDetailID: "\(self.selected_ID_Address)")

            double DeliveryAmount_Double = 0.0;

            if (DeliveryAmount > MaxDeliveryAmount) {
                DeliveryAmount = MaxDeliveryAmount;
            }
            if (DeliveryAmount == -1) {
                DeliveryAmount_Double = 0.0;
            } else {
                DeliveryAmount_Double = DeliveryAmount;
            }

            orderPrice = sum;
            ShippingRate = DeliveryAmount_Double;

            refreshTotalOrderAmount();


        }

    }

}