package com.udithamelan.foodapp.recyclervdisplay;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.udithamelan.foodapp.R;
import com.udithamelan.foodapp.database.ProductDBHelper;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ProductViewHolder> {
    public ArrayList<String> ravailableList = new ArrayList<>();
    public ArrayList<Integer> removeFrom = new ArrayList();
    Context rContext;
    Cursor rCursor;

    public RecipesAdapter(Context context, Cursor cursor) {
        rContext = context;
        rCursor = cursor;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(rContext);
        View v = inflater.inflate(R.layout.display_data_model_rv, viewGroup, false);
        return new RecipesAdapter.ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        if (!rCursor.moveToPosition(i)) {
            return;
        }

        final String prodName = rCursor.getString(rCursor.getColumnIndex(ProductDBHelper.PRODUCT_NAME));
        final int prodId = rCursor.getInt(rCursor.getColumnIndex(ProductDBHelper.PRODUCT_ID));



        productViewHolder.tv_prodName.setText(prodName);

        productViewHolder.cb_available.setChecked(false);

        productViewHolder.cb_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox)v).isChecked();

                if (checked){
                    ravailableList.add(prodName);
                }else{
                    try{
                        ravailableList.remove(new String(prodName));
                    }catch (IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        ravailableList.clear();
    }

    @Override
    public int getItemCount() {
        return rCursor.getCount();
    }

    //VIEWHOLDER CLASS
    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_prodName;
        CheckBox cb_available;

        AvailableAdapter.ProductViewHolder.ItemClickListener itemClickListener;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_prodName = (TextView) itemView.findViewById(R.id.tv_listItem);
            cb_available = (CheckBox) itemView.findViewById(R.id.cb_isAvailable);

            cb_available.setOnClickListener(this);
        }

        public void  setItemClickListener (AvailableAdapter.ProductViewHolder.ItemClickListener ic){
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
