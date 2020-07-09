package com.example.diako.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((AppCompatActivity) this).getSupportFragmentManager().
                beginTransaction().replace(R.id.Fragment_Main,new Fragment()).commit();
    }
}
