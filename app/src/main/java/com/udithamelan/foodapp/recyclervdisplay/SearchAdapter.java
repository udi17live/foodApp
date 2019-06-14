package com.udithamelan.foodapp.recyclervdisplay;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.udithamelan.foodapp.EditTextPopup;
import com.udithamelan.foodapp.R;
import com.udithamelan.foodapp.SearchResultPopUp;
import com.udithamelan.foodapp.database.ProductDBHelper;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ProductViewHolder>{
    Context sContext;
    Cursor sCursor;
    
    public SearchAdapter(Context context, Cursor cursor){
        sContext = context;
        sCursor = cursor;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(sContext);
        View v = inflater.inflate(R.layout.search_rv, viewGroup, false);
        return new SearchAdapter.ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        if (!sCursor.moveToPosition(i)) {
            return;
        }

        final String prodName = sCursor.getString(sCursor.getColumnIndex(ProductDBHelper.PRODUCT_NAME));
        final double prodWeight = sCursor.getDouble(sCursor.getColumnIndex(ProductDBHelper.PRODUCT_WEIGHT));
        final double prodPrice = sCursor.getDouble(sCursor.getColumnIndex(ProductDBHelper.PRODUCT_PRICE));
        final String prodDesc = sCursor.getString(sCursor.getColumnIndex(ProductDBHelper.PRODUCT_DESCRIPTION));
        final int prodAvailable = sCursor.getInt(sCursor.getColumnIndex(ProductDBHelper.PRODUCT_AVAILABLE));
        final int productId = sCursor.getInt(sCursor.getColumnIndex(ProductDBHelper.PRODUCT_ID));

        productViewHolder.searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sContext, SearchResultPopUp.class);
                intent.putExtra("prodId", productId);
                intent.putExtra("prodName", prodName);
                intent.putExtra("prodWeight", prodWeight);
                intent.putExtra("prodPrice", prodPrice);
                intent.putExtra("prodDescription", prodDesc);
                intent.putExtra("prodAvailable", prodAvailable);

                sContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sCursor.getCount();
    }


    //VIEWHOLDER CLASS
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tv_result_txt, tv_id ;
        CardView searchLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_result_txt = (TextView) itemView.findViewById(R.id.tv_result_txt);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            searchLayout =  (CardView) itemView.findViewById(R.id.searchLayout);


        }


    }
}
