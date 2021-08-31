package com.rkesta.richiesta.adapters.settings;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.rkesta.richiesta.MainActivity;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.model.settings.M_Address;
import com.rkesta.richiesta.model.settings.M_ExpandableOrders;

import java.util.ArrayList;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class MyAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<M_Address> address_list = new ArrayList<M_Address>() ;
    Activity activity;

    public MyAddressAdapter(Context context , Activity activity, ArrayList<M_Address> Address_list) {
        this.context = context;
        this.address_list = Address_list;
        this.activity = activity;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        TextView TV_address ;
        TextView TV_homedetails ;

        Button BTN_delete ;
        Button BTN_edit ;
        public OriginalViewHolder(View v) {
            super(v);
            TV_address = (TextView) v.findViewById(R.id.item_my_address_TV_address);
            TV_homedetails = (TextView) v.findViewById(R.id.item_my_address_TV_homedetails);

            BTN_delete = (Button) v.findViewById(R.id.item_my_address_BTN_delete);
            BTN_edit = (Button) v.findViewById(R.id.item_my_address_BTN_edit);
        }

    }


    //interface
    public interface OnItemDelete {
        void onItemClick(View view, M_Address selectedAddress, int position);
    }
    //object from interface
    private OnItemDelete DeleteListener;

    //call from mainactivity
    public void setOnItemDeleteListener(OnItemDelete onItemClickListener) {
        this.DeleteListener = onItemClickListener;
    }

    //interface
    public interface OnItemEdit {
        void onItemClick(View view, M_Address selectedAddress, int position);
    }
    //object from interface
    private OnItemEdit EditListener;

    //call from mainactivity
    public void setOnItemEditListener(OnItemEdit onItemClickListener) {
        this.EditListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_address, parent, false);
        vh = new MyAddressAdapter.OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {

            final OriginalViewHolder Item = (OriginalViewHolder) holder;
            final M_Address Address = address_list.get(position);

            Item.TV_address.setText(Address.getRKAddress()+" ,"+Address.getNotes());
            Item.TV_homedetails.setText(Address.getRKBldngNum()+" ,"+Address.getRKFloorNum()+" ,"+Address.getRKAptNum());

            Item.BTN_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    if(DeleteListener != null){
                                        DeleteListener.onItemClick(view,Address,position);
                                    }
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(EN_OR_AR(context, "are you sure you wan't to delete \n( " + Item.TV_address.getText() + " ) ?", "هل أنت متأكد أنك تريد حذف \n( " + Item.TV_address.getText() + " ) ؟")).setPositiveButton(EN_OR_AR(context, "Yes", "نعم"), dialogClickListener)
                            .setNegativeButton(EN_OR_AR(context, "No", "لا"), dialogClickListener).show();
                }
            });

            Item.BTN_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if(EditListener != null){
                        EditListener.onItemClick(view,Address,position);
                    }
                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (activity.getIntent().getStringExtra("order").equals("no")){

                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtra("long", Address.getLongitude());
                        intent.putExtra("lat", Address.getLatitude());
                        activity.startActivity(intent);
                    }
                }
            });

        }
    }



    @Override
    public int getItemCount() {
        return address_list.size();
    }

    public ArrayList<M_Address> getBean() {
        return address_list;
    }


}

