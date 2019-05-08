package com.nlapin.youthsongs.ui.aboutappscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nlapin.youthsongs.R;

public class AboutAppActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutAppActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
    }
}
