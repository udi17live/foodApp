package com.udithamelan.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udithamelan.foodapp.database.ProductDBHelper;
import com.udithamelan.foodapp.entity.Product;
import com.udithamelan.foodapp.recyclervdisplay.DisplayProductAdapter;
import com.udithamelan.foodapp.recyclervdisplay.EditProductAdapter;

import java.util.ArrayList;

public class EditProducts extends AppCompatActivity {
    ProductDBHelper productDB;
    Product product = new Product();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    EditProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        productDB = new ProductDBHelper(this);
        checkProducts();

        recyclerView = (RecyclerView) findViewById(R.id.rv_productsEdit);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new EditProductAdapter(this, productDB.displayProducts());
        recyclerView.setAdapter(adapter);


    }

    private void checkProducts() {
        if (productDB.displayProducts().getCount() <= 0){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditProducts.this);
            alertBuilder.setMessage("No Records to be Found. Add some products to get started with!");
            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton("Add Products", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(EditProducts.this, RegisterProduct.class);
                    startActivity(intent);
                }
            });
            alertBuilder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(EditProducts.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.setTitle("Oops!");
            alert.show();
        }
    }
}
