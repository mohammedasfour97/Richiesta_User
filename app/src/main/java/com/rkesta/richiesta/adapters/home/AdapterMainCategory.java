package com.rkesta.richiesta.adapters.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.model.home.M_maincat;

import java.util.ArrayList;
import java.util.List;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class AdapterMainCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<M_maincat> items = new ArrayList<>();

    private boolean loading;
    //private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, M_maincat obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterMainCategory(Context context, RecyclerView view, ArrayList<M_maincat> items) {
        this.items = items;
        ctx = context;
//        lastItemViewDetector(view);
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title , itemcat_selectedBG;
        public ImageView thumbnail;

        public OriginalViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            itemcat_selectedBG = (TextView) v.findViewById(R.id.itemcat_selected);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
//        if (viewType == VIEW_ITEM) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_category, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final M_maincat maincat = items.get(position);
            final OriginalViewHolder vItem = (OriginalViewHolder) holder;
            Glide.with(ctx).load(maincat.getStoreCategoryPic())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vItem.thumbnail);
//                    .bitmapTransform(new CircleTransform(ctx))
            vItem.title.setText(EN_OR_AR(ctx , maincat.getStoreCategoryNameEn() , maincat.getStoreCategoryNameAr()));

            if(maincat.getIS_Selected()){
                vItem.itemcat_selectedBG.setBackgroundResource(R.color.colorPrimaryLight);
            }else{
                vItem.itemcat_selectedBG.setBackgroundResource(R.color.colorPrimaryBlack);
            }

            vItem.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, maincat , position);
                        resetBackGround();
                        items.get(position).setIS_Selected(true);
//                        vItem.selected_categoryBG.setBackgroundResource(R.color.colorAccentWhite);
                        notifyDataSetChanged();
                    }
                }
            });
//            // loading album cover using Glide library
//            vItem.overflow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(ctx,"hi",Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

    public void resetBackGround(){
        for (M_maincat item : items) {
            item.setIS_Selected(false);
        }
    }

//    public void resetBackGround(){
//        for (category item : items) {
//            item.setCat_selected(false);
//        }
//    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void insertData(List<M_maincat> items) {
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

}
