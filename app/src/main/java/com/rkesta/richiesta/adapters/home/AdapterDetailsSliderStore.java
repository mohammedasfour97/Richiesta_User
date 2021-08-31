package com.rkesta.richiesta.adapters.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.model.home.M_Store6Pro;
import com.rkesta.richiesta.model.home.M_maincat;
import com.rkesta.richiesta.ui.settings.MyOrders;
import com.rkesta.richiesta.util.CoustomTextView;
import com.rkesta.richiesta.util.utility;

import java.util.ArrayList;
import java.util.List;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class AdapterDetailsSliderStore extends PagerAdapter {

    private Activity act;
    private List<M_Store6Pro> items;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, M_Store6Pro obj);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // constructor
    public AdapterDetailsSliderStore(Activity activity, ArrayList<M_Store6Pro> items) {
        this.act = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    public M_Store6Pro getItem(int pos) {
        return items.get(pos);
    }

    public void setItems(ArrayList<M_Store6Pro> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final M_Store6Pro product = items.get(position);
        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_details_slider, container, false);
        ImageView image = (ImageView) v.findViewById(R.id.DetailsSlider_image);
        CoustomTextView ProdName = (CoustomTextView) v.findViewById(R.id.DetailsSlider_TV_ProdName);
        CoustomTextView ProdPrice = (CoustomTextView) v.findViewById(R.id.DetailsSlider_TV_ProdPrice);

        ProdName.setText(EN_OR_AR(act , product.getProNameEN() , product.getProNameAR()));
        ProdPrice.setText(product.getProprice()+" "+ constant.getcurrency(act));

        MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.DetailsSlider_lyt_parent);
        utility.displayImageOriginal(act, image, product.getProimage());
        lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, product);
//                    Toast.makeText(act, "hi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((ViewPager) container).addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
