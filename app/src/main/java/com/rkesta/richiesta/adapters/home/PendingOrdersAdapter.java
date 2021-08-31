package com.rkesta.richiesta.adapters.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.databinding.ItemPendingOrderBinding;
import com.rkesta.richiesta.ui.activitymain.TrackOrderActivity;
import com.rkesta.richiesta.util.utility;

import java.util.HashMap;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.getFormattedDate;

public class PendingOrdersAdapter extends RecyclerView.Adapter<PendingOrdersAdapter.PendingOrdersViewHolder> {

    private List<HashMap<String,String>> filteredPendingOrderList, allPendingOrderList;
    private Activity activity;
    Context context;


    public static class PendingOrdersViewHolder extends RecyclerView.ViewHolder {

        private ItemPendingOrderBinding itemPendingOrderBinding;


        public PendingOrdersViewHolder(ItemPendingOrderBinding itemPendingOrderBinding) {
            super(itemPendingOrderBinding.getRoot());
            this.itemPendingOrderBinding = itemPendingOrderBinding;
        }

    }

    public PendingOrdersAdapter(List<HashMap<String,String>> allPendingOrderList,List<HashMap<String,String>> filteredPendingOrderList,
                                Context context, Activity activity) {
        this.allPendingOrderList = allPendingOrderList;
        this.filteredPendingOrderList = filteredPendingOrderList;
        this.context = context;
        this.activity= activity;

    }

    @Override
    public void onBindViewHolder(PendingOrdersViewHolder holder, int position) {

        holder.itemPendingOrderBinding.itemPendingOrderSo.setText(filteredPendingOrderList.get(position).get("SONumber"));
        holder.itemPendingOrderBinding.itemPendingOrderStoreName.setText(EN_OR_AR(context,
                filteredPendingOrderList.get(position).get("RK_Stores_StoreNameEnglish"), filteredPendingOrderList.get(position)
                        .get("RK_Stores_StoreNameArabic")));
        holder.itemPendingOrderBinding.itemPendingOrderTime.setText(utility.formatDate(filteredPendingOrderList.get(position)
                .get("CreatedDate")));
        if (filteredPendingOrderList.get(position).get("DeliveryProgess_DeliveryContact").equals("")) {
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.item_order_preparation_background));
            holder.itemPendingOrderBinding.itemPendingOrderStatus.setText(context.getResources().getString(R.string.in_prep));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!filteredPendingOrderList.get(position).get("DeliveryProgess_DeliveryContact").equals("")) {
                    for (HashMap<String, String> map : allPendingOrderList) {

                        if (map.get("SONumber").equals(filteredPendingOrderList.get(position).get("SONumber")))
                            constant.pendingOrderDet.add(map);
                    }

                    Intent intent = new Intent(activity, TrackOrderActivity.class);
                    intent.putExtra("del_id", filteredPendingOrderList.get(position).get("DeliveryProgess_DeliveryContact"));
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public PendingOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPendingOrderBinding itemPendingOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_pending_order, parent, false);
        return new PendingOrdersViewHolder(itemPendingOrderBinding);

    }


    @Override
    public int getItemCount() {
        return filteredPendingOrderList.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}