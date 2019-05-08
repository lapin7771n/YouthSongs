package com.nlapin.youthsongs.ui.authorsselection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nlapin.youthsongs.R;

public class AuthorsSelectionActivity extends AppCompatActivity {

    private static final String PAGE_NUMBER = "pageNumber";

    public static void start(Context context, int page) {
        Intent starter = new Intent(context, AuthorsSelectionActivity.class);
        starter.putExtra(PAGE_NUMBER, page);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors_selection);

        int pageNumber = getIntent().getIntExtra(PAGE_NUMBER, -1);

    }
}
