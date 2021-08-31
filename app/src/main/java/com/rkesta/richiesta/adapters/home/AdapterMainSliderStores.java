package com.rkesta.richiesta.adapters.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.model.home.M_Slider;
import com.rkesta.richiesta.model.home.M_Store6Pro;
import com.rkesta.richiesta.ui.activitymain.SelectedStore;
import com.rkesta.richiesta.util.utility;

import java.util.ArrayList;
import java.util.List;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class AdapterMainSliderStores extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<M_Slider> items = new ArrayList<>();

    private boolean loading;
    //private OnLoadMoreListener onLoadMoreListener;

    private Activity activity;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, M_Slider obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterMainSliderStores(Activity activity, RecyclerView view, ArrayList<M_Slider> items) {
        this.items = items;
        this.activity = activity;
//        lastItemViewDetector(view);
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView StoreName ;
        private ViewPager viewPager;
        private Handler handler = new Handler();
        private Runnable runnableCode = null;
        private AdapterDetailsSliderStore adapter;
        private TextView Tags;
        private View lyt_main_content;
        private ImageButton bt_previous, bt_next;
        private LinearLayout layout_dots;

        private ImageView IV_Logo;


        public OriginalViewHolder(View v) {
            super(v);


            IV_Logo = (ImageView) v.findViewById(R.id.MasterSlider_IV_Logo);

            StoreName = (TextView) v.findViewById(R.id.MasterSlider_TV_StoreName);
            lyt_main_content = (CardView) v.findViewById(R.id.MasterSlider_lyt_cart);
            Tags = (TextView) v.findViewById(R.id.MasterSlider_Tags);
            layout_dots = (LinearLayout) v.findViewById(R.id.MasterSlider_layout_dots);
            viewPager = (ViewPager) v.findViewById(R.id.MasterSlider_pager);
            bt_previous = (ImageButton) v.findViewById(R.id.MasterSlider_bt_previous);
            bt_next = (ImageButton) v.findViewById(R.id.MasterSlider_bt_next);
//            lyt_main_content.setVisibility(View.GONE);

            adapter = new AdapterDetailsSliderStore(activity, new ArrayList<M_Store6Pro>());

            bt_previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prevAction();
                }
            });

            bt_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextAction();
                }
            });
        }

        private void prevAction() {
            int pos = viewPager.getCurrentItem();
            pos = pos - 1;
            if (pos < 0) pos = adapter.getCount();
            viewPager.setCurrentItem(pos);
        }

        private void nextAction() {
            int pos = viewPager.getCurrentItem();
            pos = pos + 1;
            if (pos >= adapter.getCount()) pos = 0;
            viewPager.setCurrentItem(pos);
        }

        private void startAutoSlider(final int count) {
            runnableCode = new Runnable() {
                @Override
                public void run() {
                    int pos = viewPager.getCurrentItem();
                    pos = pos + 1;
                    if (pos >= count) pos = 0;
                    viewPager.setCurrentItem(pos);
                    handler.postDelayed(runnableCode, 3000);
                }
            };
            handler.postDelayed(runnableCode, 3000);
        }

        private void addBottomDots(LinearLayout layout_dots, int size, int current) {
            ImageView[] dots = new ImageView[size];

            layout_dots.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new ImageView(activity);
                int width_height = 15; // 7agm el daira
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
                params.setMargins(10, 10, 10, 10);
                dots[i].setLayoutParams(params);
                dots[i].setImageResource(R.drawable.shape_circle);
                dots[i].setColorFilter(ContextCompat.getColor(activity, R.color.darkOverlaySoft));
                layout_dots.addView(dots[i]);
            }

            if (dots.length > 0) {
                dots[current].setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimaryLight));
            }
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
//        if (viewType == VIEW_ITEM) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_master_slider, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final M_Slider Slider = items.get(position);
            final OriginalViewHolder vItem = (OriginalViewHolder) holder;


            utility.displayImageOriginal(activity, vItem.IV_Logo, Slider.getStorePic());
            vItem.StoreName.setText(EN_OR_AR(activity,Slider.getStoreNameEN(),Slider.getStoreNameAR()));
            vItem.Tags.setText(EN_OR_AR(activity,Slider.getStoreTagsEN(),Slider.getStoreTagsAR()));

            vItem.adapter.setItems(Slider.getStore6Products());
            vItem.viewPager.setAdapter(vItem.adapter);

            ViewGroup.LayoutParams params = vItem.viewPager.getLayoutParams();
            params.height = utility.getFeaturedNewsImageHeight(activity);
            vItem.viewPager.setLayoutParams(params);

//             displaying selected image first
            vItem.viewPager.setCurrentItem(0);
            vItem.addBottomDots(vItem.layout_dots, vItem.adapter.getCount(), 0);

            vItem.adapter.setOnItemClickListener(new AdapterDetailsSliderStore.OnItemClickListener() {
                @Override
                public void onItemClick(View view, M_Store6Pro obj) {
                    Intent i = new Intent(activity, SelectedStore.class);
                    i.putExtra("Store_ID",Slider.getID_STORE());
                    i.putExtra("Store_nameAR",Slider.getStoreNameAR());
                    i.putExtra("Store_nameEN",Slider.getStoreNameEN());
                    i.putExtra("Store_tagAR",Slider.getStoreTagsAR());
                    i.putExtra("Store_tagEN",Slider.getStoreTagsEN());
                    i.putExtra("Store_Image",Slider.getStorePic());
                    activity.startActivity(i);
                }
            });

            vItem.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int pos) {
//                    M_Store6Pro cur = vItem.adapter.getItem(pos);
//                    features_news_title.setText(cur.title);
                    vItem.addBottomDots(vItem.layout_dots, vItem.adapter.getCount(), pos);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

        }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }


    public void insertData(List<M_Slider> items) {
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

}
