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
import com.udithamelan.foodapp.recyclervdisplay.RecipesAdapter;

import java.util.ArrayList;

public class Recipes extends AppCompatActivity {
    ProductDBHelper productDB;
    Product product = new Product();
    ArrayList<String> productList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipesAdapter aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        productDB = new ProductDBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recipes);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        aAdapter = new RecipesAdapter(this, productDB.availableProducts());
        recyclerView.setAdapter(aAdapter);
    }

    private void checkProducts() {

        if (productDB.availableProducts().getCount() <= 0) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Recipes.this);
            alertBuilder.setMessage("No Records to be Found. Add some products to get started with!");
            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton("Add Products", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Recipes.this, RegisterProduct.class);
                    startActivity(intent);
                }
            });
            alertBuilder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Recipes.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.setTitle("Oops!");
            alert.show();
        }
    }

    public void findRecipes(View v){
        int size = aAdapter.ravailableList.size();

        if (size == 0){
            Toast.makeText(this, "Please select some products", Toast.LENGTH_SHORT).show();
        }else{
            for (int i = 0; i < size; i++){
                productList.add(aAdapter.ravailableList.get(i));
            }

            Intent intent = new Intent(this, RecipesViewArchive.class);
            intent.putStringArrayListExtra("productName", productList);
            productList.clear();
            aAdapter.ravailableList.clear();
            startActivity(intent);

            Toast.makeText(this, size + " PRODUCT(S) ADDED TO KITCHEN", Toast.LENGTH_SHORT).show();
        }
    }
}
