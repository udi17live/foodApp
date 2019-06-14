package com.udithamelan.foodapp.recyclervdisplay;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.udithamelan.foodapp.R;
import com.udithamelan.foodapp.database.ProductDBHelper;

import java.util.ArrayList;

public class AvailableAdapter extends RecyclerView.Adapter<AvailableAdapter.ProductViewHolder>{
    public ArrayList<Integer> availableList = new ArrayList<>();
    public ArrayList<Integer> removeFrom = new ArrayList();
    Context aContext;
    Cursor aCursor;

    public AvailableAdapter(Context context, Cursor cursor) {
        aContext = context;
        aCursor = cursor;
    }

    @NonNull
    @Override
    public AvailableAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(aContext);
        View v = inflater.inflate(R.layout.display_data_model_rv, viewGroup, false);
        return new AvailableAdapter.ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AvailableAdapter.ProductViewHolder productViewHolder, int i) {
        if (!aCursor.moveToPosition(i)) {
            return;
        }

        String prodName = aCursor.getString(aCursor.getColumnIndex(ProductDBHelper.PRODUCT_NAME));
        final int prodId = aCursor.getInt(aCursor.getColumnIndex(ProductDBHelper.PRODUCT_ID));



        productViewHolder.tv_prodName.setText(prodName);

        productViewHolder.cb_available.setChecked(true);

        productViewHolder.cb_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox)v).isChecked();

                if (checked){
                    try {
                        availableList.remove(new Integer(prodId));
                        removeFrom.remove(new Integer(productViewHolder.getAdapterPosition()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    availableList.add(prodId);
                    removeFrom.add(productViewHolder.getAdapterPosition());

                }
            }
        });







    }

    @Override
    public int getItemCount() {
        return aCursor.getCount();
    }

    //VIEWHOLDER CLASS
    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_prodName;
        CheckBox cb_available;

        ItemClickListener itemClickListener;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_prodName = (TextView) itemView.findViewById(R.id.tv_listItem);
            cb_available = (CheckBox) itemView.findViewById(R.id.cb_isAvailable);

            cb_available.setOnClickListener(this);
        }

        public void  setItemClickListener (ItemClickListener ic){
            this.itemClickListener = ic;
        }


        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        interface ItemClickListener{
            void onItemClick(View v, int pos);
        }
    }
}
