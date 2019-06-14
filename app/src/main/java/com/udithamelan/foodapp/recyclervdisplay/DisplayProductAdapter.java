package com.udithamelan.foodapp.recyclervdisplay;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.udithamelan.foodapp.R;
import com.udithamelan.foodapp.database.ProductDBHelper;
import com.udithamelan.foodapp.entity.Product;

import java.util.ArrayList;

public class DisplayProductAdapter extends RecyclerView.Adapter<DisplayProductAdapter.ProductViewHolder> {
    public ArrayList<Integer> adapterList = new ArrayList<>();
    Context mContext;
    Cursor mCursor;

    public DisplayProductAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.display_data_model_rv, viewGroup, false);
        return new ProductViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder viewHolder, int i) {
        if (!mCursor.moveToPosition(i)) {
            return;
        }

        String prodName = mCursor.getString(mCursor.getColumnIndex(ProductDBHelper.PRODUCT_NAME));
        final int prodId = mCursor.getInt(mCursor.getColumnIndex(ProductDBHelper.PRODUCT_ID));
        int prodAvailable = mCursor.getInt(mCursor.getColumnIndex(ProductDBHelper.PRODUCT_AVAILABLE));



        viewHolder.tv_prodName.setText(prodName);

        viewHolder.cb_available.setChecked(false);

        viewHolder.cb_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox)v).isChecked();

                if (checked){
                    adapterList.add(prodId);
                }else{
                    try{
                        adapterList.remove(new Integer(prodId));
                    }catch (IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
            }
        });






    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
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
