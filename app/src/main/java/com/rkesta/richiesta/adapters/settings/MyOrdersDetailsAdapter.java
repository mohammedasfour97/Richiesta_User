package com.rkesta.richiesta.adapters.settings;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.databinding.ItemMyOrderDetailsBinding;
import com.rkesta.richiesta.model.settings.M_OrdersDetails;
import com.rkesta.richiesta.util.utility;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class MyOrdersDetailsAdapter extends RecyclerView.Adapter<MyOrdersDetailsAdapter.MyOrderDetailsViewHolder> {

    private List<M_OrdersDetails> ordersDetails;
    Context context;


    public static class MyOrderDetailsViewHolder extends RecyclerView.ViewHolder {

        private ItemMyOrderDetailsBinding itemMyOrderDetailsBinding;


        public MyOrderDetailsViewHolder(ItemMyOrderDetailsBinding itemMyOrderDetailsBinding) {
            super(itemMyOrderDetailsBinding.getRoot());
            this.itemMyOrderDetailsBinding = itemMyOrderDetailsBinding;
        }

    }

    public MyOrdersDetailsAdapter(List<M_OrdersDetails> ordersDetails, Context context) {
        this.ordersDetails = ordersDetails;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(MyOrderDetailsViewHolder holder, int position) {

        M_OrdersDetails m_ordersDetails = ordersDetails.get(position);

        holder.itemMyOrderDetailsBinding.itemMyOrderDetailsTVProName.setText(EN_OR_AR(context,m_ordersDetails.getRKPrdNameEN(),
                m_ordersDetails.getRKPrdNameAR()));
        holder.itemMyOrderDetailsBinding.itemMyOrderDetailsTVProPrice.setText(m_ordersDetails.getTotalPrice() + " " +
                context.getResources().getString(R.string.currency));
        utility.displayImageOriginal(context, holder.itemMyOrderDetailsBinding.itemMyOrderDetailsIVPropic, m_ordersDetails.getProductPic());

    }

    @Override
    public MyOrderDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMyOrderDetailsBinding itemMyOrderDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_my_order_details, parent, false);
        return new MyOrderDetailsViewHolder(itemMyOrderDetailsBinding);

    }


    @Override
    public int getItemCount() {
        return ordersDetails.size();
    }
}