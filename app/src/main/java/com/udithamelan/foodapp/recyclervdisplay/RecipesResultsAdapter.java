package com.udithamelan.foodapp.recyclervdisplay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udithamelan.foodapp.R;
import com.udithamelan.foodapp.entity.Product;

import java.util.ArrayList;

public class RecipesResultsAdapter extends RecyclerView.Adapter<RecipesResultsAdapter.rrProductViewHolder> {
    Context rrContext;
    ArrayList<Product> arrayList = new ArrayList<>();

    public RecipesResultsAdapter (Context context, ArrayList<Product> arr){
        this.rrContext = context;
        this.arrayList = arr;
    }

    @NonNull
    @Override
    public rrProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(rrContext).inflate(R.layout.edit_product_view_rv, viewGroup, false);
        return new rrProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull rrProductViewHolder rrProductViewHolder, int i) {
        Product product = arrayList.get(i);

        int id = product.getId();
        String name = product.getName();
        String source_url = product.getSource_url();
        String image_url = product.getImage_url();

        rrProductViewHolder.tv_prodName_et.setText(name);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class rrProductViewHolder extends RecyclerView.ViewHolder {
        TextView tv_prodName_et;
        CardView parentLayout;

        public rrProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_prodName_et = (TextView) itemView.findViewById(R.id.tv_listItem_et);
            parentLayout =  (CardView) itemView.findViewById(R.id.parentLayout);

        }
    }
}
