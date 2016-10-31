package com.example.elliot.buzztrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void changeDrink(View view) //move to change drink activity
    {
        Intent intent = new Intent(this, change_drink.class);
        startActivity(intent);
    }
}
