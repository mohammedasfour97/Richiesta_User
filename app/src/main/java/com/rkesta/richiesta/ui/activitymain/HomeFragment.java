package com.rkesta.richiesta.ui.activitymain;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.adapters.home.AdapterMainCategory;
import com.rkesta.richiesta.adapters.home.AdapterMainSliderStores;
import com.rkesta.richiesta.adapters.home.PendingOrdersAdapter;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.model.home.M_Slider;
import com.rkesta.richiesta.model.home.M_Store6Pro;
import com.rkesta.richiesta.model.home.M_maincat;
import com.rkesta.richiesta.ui.cart.ShoppingBasketViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.rkesta.richiesta.app.constant.KEY_user_ID;
import static com.rkesta.richiesta.app.sharedObjects.ID_City;
import static com.rkesta.richiesta.app.sharedObjects.ID_Country;
import static com.rkesta.richiesta.app.sharedObjects.name_CityAR;
import static com.rkesta.richiesta.app.sharedObjects.name_CityEN;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.get_pref;

public class HomeFragment extends Fragment {

    ArrayList<M_maincat> ArrayList_maincat = new ArrayList<M_maincat>();
    ArrayList<M_Slider> ArrayList_Sider = new ArrayList<M_Slider>();
    ArrayList<M_Slider> ArrayList_SiderSelected = new ArrayList<M_Slider>();
    ArrayList<HashMap<String,String>> ArrayList_pending_orders = new ArrayList<HashMap<String,String>>();
    ArrayList<HashMap<String,String>> ArrayList_filtered_pending_orders = new ArrayList<HashMap<String,String>>();

    AdapterMainCategory  AdapterMC ;
    RecyclerView RV_MainCategory ;
    RecyclerView pending_orders ;
    TextView TV_selected_category;
    TextView TV_selected_categorycount, pending_orders_count;


    AdapterMainSliderStores AdapterSlider ;
    PendingOrdersAdapter pendingOrdersAdapter;
    RecyclerView RV_Slider ;
    private void init(View root) {
        ((TextView) root.findViewById(R.id.home_TV_selected_city)).setText(EN_OR_AR(getActivity(),name_CityEN,name_CityAR));

        RV_MainCategory = (RecyclerView) root.findViewById(R.id.home_RV_MainCategory);
        //RV_category.setLayoutManager(new GridLayoutManager(act, Tools.getGridSpanCount(act)));
        RV_MainCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RV_MainCategory.setHasFixedSize(true);
        //set data and list adapter
        AdapterMC = new AdapterMainCategory(getActivity(), RV_MainCategory, ArrayList_maincat);
        RV_MainCategory.setAdapter(AdapterMC);

        RV_Slider = (RecyclerView) root.findViewById(R.id.home_RV_Slider);
        RV_Slider.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RV_Slider.setHasFixedSize(true);
        //set data and list adapter
        AdapterSlider = new AdapterMainSliderStores(getActivity(), RV_Slider, ArrayList_SiderSelected);
        RV_Slider.setAdapter(AdapterSlider);

        TV_selected_category = root.findViewById(R.id.home_TV_selected_category);
        TV_selected_categorycount = root.findViewById(R.id.home_TV_selected_categorycount);
        pending_orders_count = root.findViewById(R.id.pending_orders_count);
//        TextView textView = root.findViewById(R.id.text_home);
//        textView.setText(s);

        pending_orders = (RecyclerView) root.findViewById(R.id.fragment_home_pending_orders);
        pending_orders.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RV_Slider.setHasFixedSize(true);
        //set data and list adapter
        pendingOrdersAdapter = new PendingOrdersAdapter(ArrayList_pending_orders,ArrayList_filtered_pending_orders, getActivity(), getActivity());
        pending_orders.setAdapter(pendingOrdersAdapter);
    }

//    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init(root);

        new Async_getProdsPerStore().execute();
        new Async_getPendingOrders().execute();
// //must to learn it
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                TV_selected_category.setText(s);
//                TV_selected_categorycount.setText(s);
//            }
//        });
        AdapterMC.setOnItemClickListener(new AdapterMainCategory.OnItemClickListener() {
            @Override
            public void onItemClick(View view, M_maincat obj, int position) {

//                SetSelectedCategorySTR(EN_OR_AR(getActivity(),obj.getStoreCategoryNameEn(),obj.getStoreCategoryNameAr()));
//                Toast.makeText(getActivity(),obj.getStoreCategoryPic(),Toast.LENGTH_LONG).show();
                selectedCategoryNotifyData(position);
            }
        });

        return root;
    }





    private void Home_exec(ArrayList<HashMap<String, String>> Top6Prods) {

        for(int i = 0 ; i < Top6Prods.size() ; i++) {


            //Sider

            if (ArrayList_Sider.isEmpty()) {
                M_Slider Slider_item;
                Slider_item = new M_Slider(Top6Prods.get(i).get("StoreID"),
                        Top6Prods.get(i).get("RKStoreCategoryID"),
                        "",
                        "",
                        Top6Prods.get(i).get("StoreNameEN"),
                        Top6Prods.get(i).get("StoreNameAr"),
                        Top6Prods.get(i).get("StoreLogo"));

                ArrayList_Sider.add(Slider_item);
                M_Store6Pro MSP = new M_Store6Pro(Top6Prods.get(i).get("NameArabic"), Top6Prods.get(i).get("NameEnglish"), Top6Prods.get(i).get("SalesUnitPrice"), Top6Prods.get(i).get("ProductImage"));
                ArrayList_Sider.get(0).append_Store6Products(MSP);

            } else {
                boolean is_contain_id = false;
                int Store_ID_contain = 0;
                for (int j = 0; j < ArrayList_Sider.size(); j++) {
                    if (ArrayList_Sider.get(j).getID_STORE().equals(Top6Prods.get(i).get("StoreID"))) {
                        is_contain_id = true;
                        Store_ID_contain = j;
                        // if (is_contain_id)
                        //      add product to ± Store_ID_contain ±
                        // else
                        //      add new Store
                    }
                }

                M_Slider Slider_item;
                if (is_contain_id) { // add product
                    M_Store6Pro MSP = new M_Store6Pro(Top6Prods.get(i).get("NameArabic"), Top6Prods.get(i).get("NameEnglish"), Top6Prods.get(i).get("SalesUnitPrice"), Top6Prods.get(i).get("ProductImage"));
                    ArrayList_Sider.get(Store_ID_contain).append_Store6Products(MSP);
                } else {  //add new Store
                    Slider_item = new M_Slider(Top6Prods.get(i).get("StoreID"),
                            Top6Prods.get(i).get("RKStoreCategoryID"),
                            "",
                            "",
                            Top6Prods.get(i).get("StoreNameEN"),
                            Top6Prods.get(i).get("StoreNameAr"),
                            Top6Prods.get(i).get("StoreLogo"));

                    ArrayList_Sider.add(Slider_item);
                    M_Store6Pro MSP = new M_Store6Pro(Top6Prods.get(i).get("NameArabic"), Top6Prods.get(i).get("NameEnglish"), Top6Prods.get(i).get("SalesUnitPrice"), Top6Prods.get(i).get("ProductImage"));
                    ArrayList_Sider.get(ArrayList_Sider.size() - 1).append_Store6Products(MSP);
                }
            }
            //Sider end


            //Maincategory
            M_maincat StoreCategories_item;
            if (ArrayList_maincat.isEmpty()) {
                StoreCategories_item = new M_maincat(
                        Top6Prods.get(i).get("RKStoreCategoryID"),
                        Top6Prods.get(i).get("StoreCategoryPic"),
                        Top6Prods.get(i).get("StoreCategoryNameAr"),
                        Top6Prods.get(i).get("StoreCategoryNameEn"),
                        true);

//                SetSelectedCategorySTR(EN_OR_AR(getActivity(),Top6Prods.get(i).get("StoreCategoryNameEn"),Top6Prods.get(i).get("StoreCategoryNameAr")));
                ArrayList_maincat.add(StoreCategories_item);
            } else {
                boolean is_contain_id = false;
                for (M_maincat maincat_single : ArrayList_maincat) {
                    if (maincat_single.getRKStoreCategoryID().equals(Top6Prods.get(i).get("RKStoreCategoryID"))) {
                        is_contain_id = true;
                    }
                }
                if (!is_contain_id) {
                    StoreCategories_item = new M_maincat(
                            Top6Prods.get(i).get("RKStoreCategoryID"),
                            Top6Prods.get(i).get("StoreCategoryPic"),
                            Top6Prods.get(i).get("StoreCategoryNameAr"),
                            Top6Prods.get(i).get("StoreCategoryNameEn"),
                            false);
                    ArrayList_maincat.add(StoreCategories_item);
                }
            }
            //end Maincategory

        }

        AdapterMC.notifyDataSetChanged();
        selectedCategoryNotifyData(0);


    }

    private void selectedCategoryNotifyData(int selectedPosition) {
        ArrayList_SiderSelected.clear();
        String IDCat = ArrayList_maincat.get(selectedPosition).getRKStoreCategoryID();
        TV_selected_category.setText(EN_OR_AR(getActivity(),ArrayList_maincat.get(selectedPosition).getStoreCategoryNameEn(),ArrayList_maincat.get(selectedPosition).getStoreCategoryNameAr()));
        for (M_Slider item : ArrayList_Sider) {
            if(item.getID_MainCategory().equals(IDCat)){
                ArrayList_SiderSelected.add(item);
            }
        }

        TV_selected_categorycount.setText(ArrayList_SiderSelected.size()+"");
        AdapterSlider.notifyDataSetChanged();
    }

    private ProgressDialog pDialog;
    class Async_getProdsPerStore extends AsyncTask<String, String, String> {

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

        ArrayList<HashMap<String, String>> ProdsPerStore = new ArrayList<>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            ProdsPerStore = WS.Top6Stores(ID_City,ID_Country, getActivity().getIntent()
                    .getStringExtra("lat"), getActivity().getIntent().getStringExtra("long"));
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            if(ProdsPerStore.isEmpty()){
                // repeate call
                // Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
                // something went wrong stay
            }else{
                Home_exec(ProdsPerStore);
                // prepareoder(PendingOrders);
            }
            pDialog.dismiss();
        }

    }


    class Async_getPendingOrders extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        ArrayList<HashMap<String,String>> pendingOrders = new ArrayList<>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            pendingOrders = WS.getPendingOrders(get_pref(getContext(),KEY_user_ID));
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/


        protected void onPostExecute(String file_url) {
            if(pendingOrders.size() == 0){
                // repeate call
                // Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
                // something went wrong stay
            }else{

                List<HashMap<String,String>> filtered = new ArrayList<>();

                int n;
                for (HashMap<String,String> map : pendingOrders){

                    n=0;

                    for (HashMap<String,String> map1 : pendingOrders){

                        if (map.get("SONumber").equals(map1.get("SONumber"))){

                            n++;
                            if (!filtered.contains(map1) && n<2){
                                filtered.add(map);
                            }

                            //before_filtered.remove(map1);
                        }

                    }
                }
                ArrayList_pending_orders.clear();
                ArrayList_filtered_pending_orders.clear();
                ArrayList_pending_orders.addAll(pendingOrders);
                ArrayList_filtered_pending_orders.addAll(filtered);
                pendingOrdersAdapter.notifyDataSetChanged();
                pending_orders_count.setText(String.valueOf(ArrayList_filtered_pending_orders.size()));
            }

        }

    }

}