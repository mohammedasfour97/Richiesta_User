package com.rkesta.richiesta.adapters.settings;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.model.home.M_Product;
import com.rkesta.richiesta.model.settings.M_ExpandableOrders;
import com.rkesta.richiesta.model.settings.M_OrdersDetails;
import com.rkesta.richiesta.ui.activitymain.AddProduct;
import com.rkesta.richiesta.util.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.roundup;

public class MyOrdersBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<M_ExpandableOrders> ExpandableOrders_list = new ArrayList<M_ExpandableOrders>() ;

    public MyOrdersBaseAdapter(Context context , ArrayList<M_ExpandableOrders> ExpandableCat_list) {
        this.context = context;
        this.ExpandableOrders_list = ExpandableCat_list;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        TextView  TV_SOnumber,TV_Order_Total,TV_Delivery_Charges,TV_total,TV_Date , TV_Order_Status, TV_Discount ;
        ImageButton IB_expand_colisap;
        LinearLayout RL_ShowDetails;
        public OriginalViewHolder(View v) {
            super(v);
            TV_SOnumber = (TextView) v.findViewById(R.id.MyOrder_item_TV_SOnumber);



            TV_Order_Total = (TextView) v.findViewById(R.id.MyOrder_item_TV_Order_Total);
            TV_Delivery_Charges = (TextView) v.findViewById(R.id.MyOrder_item_TV_Delivery_Charges);
            TV_total = (TextView) v.findViewById(R.id.MyOrder_item_TV_total);
            TV_Date = (TextView) v.findViewById(R.id.MyOrder_item_TV_Date);

            TV_Order_Status = (TextView) v.findViewById(R.id.MyOrder_item_TV_Order_Status);

            TV_Discount = (TextView) v.findViewById(R.id.MyOrder_item_TV_discount);



            RL_ShowDetails = (LinearLayout) v.findViewById(R.id.MyOrder_item_RL_ShowDetails);

            IB_expand_colisap = (ImageButton) v.findViewById(R.id.MyOrder_item_IB_expand_colisap);
        }

    }


//    //interface
//    public interface OnItemPickedUP {
//        void onItemClick(View view, M_Expandablecategory selectedExpandableCat, int position);
//    }
//    //object from interface
//    private OnItemPickedUP ObjPickedUPListener;
//
//    //call from mainactivity
//    public void setOnItemPickedUPListener(OnItemPickedUP onItemClickListener) {
//        this.ObjPickedUPListener = onItemClickListener;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        //todo des
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_order, parent, false);
        vh = new MyOrdersBaseAdapter.OriginalViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {

            final OriginalViewHolder Item = (OriginalViewHolder) holder;
            final M_ExpandableOrders ExpandableOrders = ExpandableOrders_list.get(position);

            double current_total = 0.0;
            double shipp = 0.0;
            try {
                current_total = roundup(Double.parseDouble(ExpandableOrders.getAllTotalPrice()));
            }catch (Exception e){
                current_total = 0.0;
            }
            try {
                shipp = roundup(Double.parseDouble(ExpandableOrders.getShippingRate()));
            }catch (Exception e){
                shipp = 0.0;
            }
            Item.TV_SOnumber.setText("#"+ExpandableOrders.getSONumber());
            Item.TV_Order_Total.setText(current_total+"");
            Item.TV_Delivery_Charges.setText(shipp+"");
            if (!ExpandableOrders.getDiscount().equals(""))
                Item.TV_Discount.setText(""+roundup(Double.valueOf(ExpandableOrders.getDiscount())));

            Item.TV_total.setText(roundup(current_total+shipp)+"");

            if(ExpandableOrders.isComplete()){
                Item.TV_Order_Status.setText(EN_OR_AR(context," Delivered ",  " تم التوصيل "));
                Item.TV_Order_Status.setBackgroundResource(R.color.PearlAqua);
            }else{
                Item.TV_Order_Status.setText(EN_OR_AR(context, " Pending... ",  " قيد الانتظار "));
                Item.TV_Order_Status.setBackgroundResource(R.color.Hue);
            }



            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");
            String startDateStr = ExpandableOrders.getOrderDate();
            Date date = null;
            try {
                date = inputFormat.parse(startDateStr);
                startDateStr = outputFormat.format(date);
            } catch (Exception e) {

            }
            Item.TV_Date.setText(startDateStr);

            /**
             * Des here the rows
             * */
            Item.RL_ShowDetails.removeAllViews();
            for (final M_OrdersDetails prod :ExpandableOrders.getOrdersDetails_list() ) {
                LayoutInflater inflater = LayoutInflater.from(context);
                //todo des
                RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.item_my_order_details, null, false);

                //image
                utility.displayImageOriginal(context , ((ImageView) layout.findViewById(R.id.item_my_order_details_IV_propic)), prod.getProductPic());
                //name + Qnty
                ((TextView) layout.findViewById(R.id.item_my_order_details_TV_ProName)).setText( "("+prod.getSalesUnitPrice()+"x"+prod.getQty()+") "+ EN_OR_AR(context,prod.getRKPrdNameEN(),prod.getRKPrdNameAR()));
                //price + currency
                ((TextView) layout.findViewById(R.id.item_my_order_details_TV_ProPrice)).setText(prod.getTotalPrice() + " " + constant.getcurrency(context));

                String desc = "";
//                prod.getNotes()
                if(!prod.getProductColor().isEmpty()){
                    desc = prod.getProductColor();
                }
                if(!prod.getProductAdditionals().isEmpty()){
                    if(desc.isEmpty()) {
                        desc = prod.getProductAdditionals();
                    }else{
                        desc += " , "+prod.getProductAdditionals();
                    }
                }
                if(!prod.getProductSize().isEmpty()){
                    if(desc.isEmpty()) {
                        desc = prod.getProductSize();
                    }else{
                        desc += " , "+prod.getProductSize();
                    }
                }
                if(!prod.getNotes().isEmpty()){
                    if(desc.isEmpty()) {
                        desc = prod.getNotes();
                    }else{
                        desc += "\n"+prod.getNotes();
                    }
                }
                ((TextView) layout.findViewById(R.id.item_my_order_details_TV_Note)).setText(desc);

//                layout.findViewById(R.id.item_prod_details_IV_Add).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //Toast.makeText(context, prod.getProduct_ID() , Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(context, AddProduct.class);
//
//                        i.putExtra("Product_ID",prod.getProduct_ID());
//                        i.putExtra("NameEN",prod.getNameEN());
//                        i.putExtra("NameAR",prod.getNameAR());
////                        i.putExtra("Name",EN_OR_AR(context,prod.getNameEN(),prod.getNameAR()));
//                        i.putExtra("Desc",EN_OR_AR(context,prod.getDescEn(),prod.getDescAr()));
//                        i.putExtra("Price",prod.getPrice());
//                        i.putExtra("Imageurl",prod.getImageurl());
//                        i.putExtra("Branch_ID",prod.getBranch_ID());
//                        context.startActivity(i);
//                    }
//                });
                Item.RL_ShowDetails.addView(layout);

            }


            if(ExpandableOrders.isExpanded()){
                Item.RL_ShowDetails.setVisibility(View.VISIBLE);
                Item.IB_expand_colisap.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
            }else {
                Item.RL_ShowDetails.setVisibility(View.GONE);
                Item.IB_expand_colisap.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
            }

            Item.IB_expand_colisap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ExpandableOrders.isExpanded()){
                        ExpandableOrders_list.get(position).setExpanded(false);
                    }else {
                        ExpandableOrders_list.get(position).setExpanded(true);
                    }
                    notifyItemChanged(position);
                }
            });


        }
    }



    @Override
    public int getItemCount() {
        return ExpandableOrders_list.size();
    }

    public ArrayList<M_ExpandableOrders> getBean() {
        return ExpandableOrders_list;
    }


}

