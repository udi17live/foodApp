package com.udithamelan.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.udithamelan.foodapp.database.ProductDBHelper;
import com.udithamelan.foodapp.entity.Product;
import com.udithamelan.foodapp.recyclervdisplay.AvailableAdapter;
import com.udithamelan.foodapp.recyclervdisplay.DisplayProductAdapter;

import java.util.ArrayList;

public class Availability extends AppCompatActivity {
    ProductDBHelper productDB;
    Product product = new Product();
    ArrayList<Integer> productList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AvailableAdapter aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        productDB = new ProductDBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_available);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        aAdapter = new AvailableAdapter(this, productDB.availableProducts());
        recyclerView.setAdapter(aAdapter);
    }

    private void checkProducts() {

        if (productDB.availableProducts().getCount() <= 0) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Availability.this);
            alertBuilder.setMessage("No Records to be Found. Add some products to get started with!");
            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton("Add Products", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Availability.this, RegisterProduct.class);
                    startActivity(intent);
                }
            });
            alertBuilder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Availability.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.setTitle("Oops!");
            alert.show();
        }
    }

    public void saveProducts(View v){
        int size = aAdapter.availableList.size();

        if (size == 0){
            Toast.makeText(this, "Please select some products", Toast.LENGTH_SHORT).show();
        }else{
            for (int i = 0; i < size; i++){
                productList.add(aAdapter.availableList.get(i));
            }

            for (int i = 0; i< productList.size();i++){
                int pos = productList.get(i);


                try {
                    //int j = aAdapter.removeFrom.get(i);
                    //aAdapter.notifyItemRemoved(new Integer(j));
                    aAdapter.notifyDataSetChanged();

                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }


            //aAdapter.removeFrom.clear();
            Toast.makeText(this, size + " PRODUCT(S) ADDED TO KITCHEN", Toast.LENGTH_SHORT).show();
        }
    }
}
