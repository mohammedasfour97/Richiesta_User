package com.rkesta.richiesta.adapters.home;

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
import com.rkesta.richiesta.model.home.M_Expandablecategory;
import com.rkesta.richiesta.model.home.M_Product;
import com.rkesta.richiesta.ui.activitymain.AddProduct;
import com.rkesta.richiesta.util.ImageFullScreen;
import com.rkesta.richiesta.util.utility;

import java.util.ArrayList;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;


public class StorCateProdBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<M_Expandablecategory> ExpandableCat_list = new ArrayList<M_Expandablecategory>() ;

    public StorCateProdBaseAdapter(Context context , ArrayList<M_Expandablecategory> ExpandableCat_list) {
        this.context = context;
        this.ExpandableCat_list = ExpandableCat_list;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

    // each data item is just a string in this case
    TextView  TV_CategoryName ;
        ImageButton IB_expand_colisap;
        LinearLayout RL_ShowDetails;
    public OriginalViewHolder(View v) {
        super(v);
        TV_CategoryName = (TextView) v.findViewById(R.id.SCP_item_TV_categoryName);
        RL_ShowDetails = (LinearLayout) v.findViewById(R.id.SCP_item_RL_ShowDetails);

        IB_expand_colisap = (ImageButton) v.findViewById(R.id.SCP_item_IB_expand_colisap);
    }

}


    //interface
    public interface OnItemPickedUP {
        void onItemClick(View view, M_Expandablecategory selectedExpandableCat, int position);
    }
    //object from interface
    private OnItemPickedUP ObjPickedUPListener;

    //call from mainactivity
    public void setOnItemPickedUPListener(OnItemPickedUP onItemClickListener) {
        this.ObjPickedUPListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_cate_prod, parent, false);
        vh = new OriginalViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {

            final OriginalViewHolder Item = (OriginalViewHolder) holder;
            final M_Expandablecategory ExpandableCat = ExpandableCat_list.get(position);

            Item.TV_CategoryName.setText(ExpandableCat.getName());

            /**
             * Des here the rows
             * */
            Item.RL_ShowDetails.removeAllViews();
            for (final M_Product prod :ExpandableCat.getProduct_list() ) {
                LayoutInflater inflater = LayoutInflater.from(context);
                RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.item_prod_details, null, false);

                utility.displayImageOriginal(context , ((ImageView) layout.findViewById(R.id.item_prod_details_IV_propic)), prod.getImageurl());

                ((ImageView) layout.findViewById(R.id.item_prod_details_IV_propic)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, ImageFullScreen.class);
                        i.putExtra("Imageurl", prod.getImageurl());
                        context.startActivity(i);
                    }
                });


                //ProName
                String Unit = "" ;
                if(!EN_OR_AR(context,prod.getUnitType(),prod.getUnitTypeAr()).isEmpty()){
                    Unit = " ("+EN_OR_AR(context,prod.getUnitType(),prod.getUnitTypeAr())+")";
                }
                ((TextView) layout.findViewById(R.id.item_prod_details_TV_ProName)).setText(
                        EN_OR_AR(context,prod.getNameEN(),prod.getNameAR())+Unit
                        );
                //ProPrice
                ((TextView) layout.findViewById(R.id.item_prod_details_TV_ProPrice)).setText(
                        prod.getPrice() + " " + constant.getcurrency(context)
                );

                layout.findViewById(R.id.item_prod_details_IV_Add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(context, prod.getProduct_ID() , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, AddProduct.class);

                        i.putExtra("Product_ID",prod.getProduct_ID());
                        i.putExtra("NameEN",prod.getNameEN());
                        i.putExtra("NameAR",prod.getNameAR());
//                        i.putExtra("Name",EN_OR_AR(context,prod.getNameEN(),prod.getNameAR()));
                        i.putExtra("Desc",EN_OR_AR(context,prod.getDescEn(),prod.getDescAr()));
                        i.putExtra("Price",prod.getPrice());
                        i.putExtra("Imageurl",prod.getImageurl());
                        i.putExtra("Branch_ID",prod.getBranch_ID());
                        context.startActivity(i);
                    }
                });
                Item.RL_ShowDetails.addView(layout);

            }


            if(ExpandableCat.isExpanded()){
                Item.RL_ShowDetails.setVisibility(View.VISIBLE);
                Item.IB_expand_colisap.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
            }else {
                Item.RL_ShowDetails.setVisibility(View.GONE);
                Item.IB_expand_colisap.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
            }

            Item.IB_expand_colisap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ExpandableCat.isExpanded()){
                        ExpandableCat_list.get(position).setExpanded(false);
                    }else {
                        ExpandableCat_list.get(position).setExpanded(true);
                    }
                    notifyItemChanged(position);
                }
            });


        }
    }



    @Override
    public int getItemCount() {
        return ExpandableCat_list.size();
    }

    public ArrayList<M_Expandablecategory> getBean() {
        return ExpandableCat_list;
    }


}


