package com.udithamelan.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.udithamelan.foodapp.database.ProductDBHelper;

public class RegisterProduct extends AppCompatActivity {
    ProductDBHelper productDB;
    Button btn_register_product;
    EditText et_product_name, et_product_weight, et_product_price,et_product_description;
    String prod_name, prod_desc;
    double prod_weight, prod_price;
    int prod_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);

        productDB = new ProductDBHelper(this);

        et_product_name = (EditText) findViewById(R.id.et_product_name);
        et_product_weight = (EditText) findViewById(R.id.et_product_weight);
        et_product_price = (EditText) findViewById(R.id.et_product_price);
        et_product_description = (EditText) findViewById(R.id.et_product_description);
        btn_register_product = (Button) findViewById(R.id.btn_register_product);

    }

    public void addProductData(View v){
        prod_name = et_product_name.getText().toString();

        if(et_product_weight.getText().toString().isEmpty()){
            prod_weight = 0.0;
        }else{
            prod_weight = Double.parseDouble(et_product_weight.getText().toString());
        }

        if (et_product_price.getText().toString().isEmpty()){
            prod_price = 0.0;
        }else{
            prod_price = Double.parseDouble(et_product_price.getText().toString());
        }


        prod_desc = et_product_description.getText().toString();
        prod_available = 0;

        if (validate()){
            boolean insertProducts = productDB.addProductData(prod_name,prod_weight,prod_price,prod_desc,prod_available);

            if (insertProducts){
                alertSuccess();
                resetFields();
            }else{
                alertFailed();
                resetFields();
            }
        }else{
            alertBlankFields();
        }
    }

    private void resetFields(){
        et_product_name.setText("");
        et_product_weight.setText("");
        et_product_price.setText("");
        et_product_description.setText("");
    }

    private boolean validate(){
        boolean validInputs = true;

        if (prod_name.isEmpty()){
            validInputs = false;
        }

        if (prod_weight <= 0.0 ){
            validInputs = false;
        }


        if (prod_price <= 0.0){
            validInputs = false;
        }

        if (prod_desc.isEmpty()){
            validInputs = false;
        }
        return validInputs;
    }

    private void alertFailed(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterProduct.this);
        alertBuilder.setMessage("Failed to Add Product to Database");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Okay got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.setTitle("Something went wrong!");
        alert.show();
    }

    private void alertBlankFields(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterProduct.this);
        alertBuilder.setMessage("There are Blank Fields here!");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Okay got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.setTitle("Oops!");
        alert.show();
    }

    private void alertSuccess(){
        String message = "Successfully Added the following record to Database\n\t Name: " + prod_name +
                "\n\tWeight: " + String.valueOf(prod_weight) + "\n\tPrice: " + String.valueOf(prod_price) +
                "\n\tDescription: " + prod_desc;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterProduct.this);
        alertBuilder.setMessage(message);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Add New Product", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertBuilder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegisterProduct.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.setTitle("Success!");
        alert.show();
    }
}
