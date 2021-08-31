package com.rkesta.richiesta.adapters.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.model.cart.M_Cart;

import java.util.ArrayList;
import java.util.List;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;


public class AdapterCart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<M_Cart> items = new ArrayList<>();

    private boolean loading;
    //private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, M_Cart obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterCart(Context context, RecyclerView view, ArrayList<M_Cart> items) {
        this.items = items;
        ctx = context;
//        lastItemViewDetector(view);
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TV_ProName , TV_ProDetail , TV_Total;
        public ImageView IV_propic;
        public Button close_button;

        public OriginalViewHolder(View v) {
            super(v);
            TV_ProName = (TextView) v.findViewById(R.id.item_cart_details_TV_ProName);
            TV_ProDetail = (TextView) v.findViewById(R.id.item_cart_details_TV_ProDetail);
            TV_Total = (TextView) v.findViewById(R.id.item_cart_details_TV_Total);

            IV_propic = (ImageView) v.findViewById(R.id.item_cart_details_IV_propic);

            close_button = (Button) v.findViewById(R.id.item_cart_close_button);

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
//        if (viewType == VIEW_ITEM) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_details, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final M_Cart item_cart = items.get(position);
            final OriginalViewHolder vItem = (OriginalViewHolder) holder;
            Glide.with(ctx).load(item_cart.getProduct_IMG())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vItem.IV_propic);

            vItem.TV_ProName.setText( "(x"+item_cart.getQty()+") "+ EN_OR_AR(ctx,item_cart.getProduct_nameEN(),item_cart.getProduct_nameAR()));

            vItem.TV_ProDetail.setText( getDetails(item_cart) );

//            vItem.TV_Total.setText( item_cart.getQty()+"x"+item_cart.getProduct_Price()+" , "+ctx.getResources().getString(R.string.Total)+" : "+item_cart.getTotalPrice()+" "+ constant.getcurrency(ctx) );
            vItem.TV_Total.setText( ctx.getResources().getString(R.string.Total)+" : "+item_cart.getTotalPrice()+" "+ constant.getcurrency(ctx) );


            vItem.close_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, item_cart , position);
                    }
                }
            });

        }
    }

    private String getDetails(M_Cart item_cart) {
        String Details = "";

        if(!item_cart.getSize_name().isEmpty()){
            Details = "  "+item_cart.getSize_name()+"  ";
        }

        if(!item_cart.getAdditional_name().isEmpty()){
            if(Details.isEmpty()){
                Details = "  "+item_cart.getAdditional_name()+"  ";
            }else{
                Details += "\n"+"  "+item_cart.getAdditional_name()+"  ";
            }
        }

        if(!item_cart.getColor_name().isEmpty()){
            if(Details.isEmpty()){
                Details = "  "+item_cart.getColor_name()+"  ";
            }else{
                Details += "\n"+"  "+item_cart.getColor_name()+"  ";
            }
        }
        return Details;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public void insertData(List<M_Cart> items) {
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

}
