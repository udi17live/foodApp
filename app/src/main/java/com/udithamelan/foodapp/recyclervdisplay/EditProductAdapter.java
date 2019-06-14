package com.udithamelan.foodapp.recyclervdisplay;

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
import com.udithamelan.foodapp.database.ProductDBHelper;
import com.udithamelan.foodapp.entity.Product;

public class EditProductAdapter  extends RecyclerView.Adapter<EditProductAdapter.EtProductViewHolder>{
    Context etContext;
    Cursor etCursor;

    public EditProductAdapter(Context context, Cursor cursor) {
        etContext = context;
        etCursor = cursor;
    }

    @NonNull
    @Override
    public EtProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(etContext);
        View v = inflater.inflate(R.layout.edit_product_view_rv, viewGroup, false);
        return new EditProductAdapter.EtProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EtProductViewHolder etProductViewHolder, int i) {
        if (!etCursor.moveToPosition(i)) {
            return;
        }

        final String prodName = etCursor.getString(etCursor.getColumnIndex(ProductDBHelper.PRODUCT_NAME));
        final double prodWeight = etCursor.getDouble(etCursor.getColumnIndex(ProductDBHelper.PRODUCT_WEIGHT));
        final double prodPrice = etCursor.getDouble(etCursor.getColumnIndex(ProductDBHelper.PRODUCT_PRICE));
        final String prodDesc = etCursor.getString(etCursor.getColumnIndex(ProductDBHelper.PRODUCT_DESCRIPTION));
        final int prodAvailable = etCursor.getInt(etCursor.getColumnIndex(ProductDBHelper.PRODUCT_AVAILABLE));
        final int productId = etCursor.getInt(etCursor.getColumnIndex(ProductDBHelper.PRODUCT_ID));

        etProductViewHolder.tv_prodName_et.setText(prodName);

        etProductViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(etContext, EditTextPopup.class);
                intent.putExtra("prodId", productId);
                intent.putExtra("prodName", prodName);
                intent.putExtra("prodWeight", prodWeight);
                intent.putExtra("prodPrice", prodPrice);
                intent.putExtra("prodDescription", prodDesc);
                intent.putExtra("prodAvailable", prodAvailable);

                etContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return etCursor.getCount();
    }


    public static class EtProductViewHolder extends RecyclerView.ViewHolder {
        TextView tv_prodName_et;
        CardView parentLayout;

        public EtProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_prodName_et = (TextView) itemView.findViewById(R.id.tv_listItem_et);
            parentLayout =  (CardView) itemView.findViewById(R.id.parentLayout);

        }


    }


}
