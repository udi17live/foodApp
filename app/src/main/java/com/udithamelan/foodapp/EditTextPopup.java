package com.udithamelan.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.udithamelan.foodapp.database.ProductDBHelper;

public class EditTextPopup extends AppCompatActivity {
    EditText et_prodName, et_prodWeight, et_prodPrice, et_prodDesc;
    String prodName, prodDesc;
    double prodWeight, prodPrice;
    int prodAvailable, prodId;
    TextView tv_prodId;
    Spinner sp_availability;
    ArrayAdapter<CharSequence> adapter;
    ProductDBHelper productDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_popup);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int width = display.widthPixels;
        int height = display.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;

        getWindow().setAttributes(params);

        productDBHelper = new ProductDBHelper(this);

        et_prodName = (EditText) findViewById(R.id.ut_product_name);
        et_prodWeight = (EditText) findViewById(R.id.ut_product_weight);
        et_prodPrice = (EditText) findViewById(R.id.ut_product_price);
        et_prodDesc = (EditText) findViewById(R.id.ut_product_description);
        tv_prodId = (TextView) findViewById(R.id.tv_productId);
        sp_availability = (Spinner) findViewById(R.id.sp_Availability);

        adapter = ArrayAdapter.createFromResource(this, R.array.sp_available, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_availability.setAdapter(adapter);

        if (getIntent().hasExtra("prodId")){
            prodId = getIntent().getExtras().getInt("prodId");
            prodName = getIntent().getExtras().getString("prodName");
            prodWeight = getIntent().getExtras().getDouble("prodWeight");
            prodPrice = getIntent().getExtras().getDouble("prodPrice");
            prodDesc = getIntent().getExtras().getString("prodDescription");
            prodAvailable = getIntent().getExtras().getInt("prodAvailable");
        }

        tv_prodId.setText(String.valueOf(prodId));
        et_prodName.setText(prodName);
        et_prodWeight.setText(String.valueOf(prodWeight));
        et_prodPrice.setText(String.valueOf(prodPrice));
        et_prodDesc.setText(prodDesc);

        if (prodAvailable == 0){
            sp_availability.setSelection(1);
        }else{
            sp_availability.setSelection(0);
        }

    }

    public void updateProduct(View v){
        if(et_prodWeight.getText().toString().isEmpty()){
            prodWeight = 0.0;
        }else{
            prodWeight = Double.parseDouble(et_prodWeight.getText().toString());
        }

        if (et_prodPrice.getText().toString().isEmpty()){
            prodPrice = 0.0;
        }else{
            prodPrice = Double.parseDouble(et_prodPrice.getText().toString());
        }


        if (validate()){
            int _id = prodId;
            String _name = et_prodName.getText().toString();
            double _weight = Double.parseDouble(et_prodWeight.getText().toString());
            double _price = Double.parseDouble(et_prodPrice.getText().toString());
            String _desc = et_prodDesc.getText().toString();
            int _productAvailable;
            if (sp_availability.getSelectedItem().toString().equalsIgnoreCase("Yes")){
                _productAvailable = 1 ;
            }else{
                _productAvailable = 0;
            }

            boolean update = productDBHelper.updateData(_id,_name,_weight,_price,_desc,_productAvailable);


            if (update){
                alertSuccess();
            }else{
                alertFailed();
            }

            Toast.makeText(this, "THIS IS DONE", Toast.LENGTH_SHORT).show();

        }else{
            alertBlankFields();
        }


    }

    private boolean validate(){
        boolean validInput = true;

        if (et_prodName.getText().toString().trim().length() == 0){
            validInput = false;
        }

        //double weight = Double.parseDouble(et_prodWeight.getText().toString());
        if (et_prodWeight.getText().toString().trim().length() == 0 || prodWeight <= 0){
            validInput = false;
        }

        //double price = Double.parseDouble(et_prodPrice.getText().toString());
        if (et_prodPrice.getText().toString().trim().length() ==0 || prodPrice <=0){
            validInput = false;
        }

        if(et_prodDesc.getText().toString().trim().length() == 0){
            validInput = false;
        }

        return validInput;
    }

    private void alertSuccess(){
        String message = "Successfully Updated to Database";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditTextPopup.this);
        alertBuilder.setMessage(message);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Okay Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditTextPopup.this, EditProducts.class);
                startActivity(intent);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.setTitle("Success!");
        alert.show();
    }

    private void alertBlankFields(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditTextPopup.this);
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

    private void alertFailed(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditTextPopup.this);
        alertBuilder.setMessage("Failed to Update Product in Database");
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
}
