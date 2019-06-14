package com.udithamelan.foodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.udithamelan.foodapp.database.ProductDBHelper;

public class SearchResultPopUp extends AppCompatActivity {
    ProductDBHelper productDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_pop_up);

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
    }
}
