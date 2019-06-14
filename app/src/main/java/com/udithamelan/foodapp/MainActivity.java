package com.udithamelan.foodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registerProduct(View v){
        Intent intent = new Intent(this,RegisterProduct.class);
        startActivity(intent);
    }

    public void displayProduct(View v){
        Intent intent = new Intent(this,DisplayProducts.class);
        startActivity(intent);
    }

    public void availability(View v){
        Intent intent = new Intent(this,Availability.class);
        startActivity(intent);
    }

    public void editProduct(View v){
        Intent intent = new Intent(this,EditProducts.class);
        startActivity(intent);
    }

    public void search(View v){
        Intent intent = new Intent(this,Search.class);
        startActivity(intent);
    }

    public void recipes(View v){
        Intent intent = new Intent(this,Recipes.class);
        startActivity(intent);
    }

    public void aboutFoodApp(View v){
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
    }
}
