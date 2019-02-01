package com.nlapin.youthsongs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author nikita on 27,January,2019
 */
public abstract class BaseRouter {

    private AppCompatActivity activity;

    public BaseRouter(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction =
                activity.getSupportFragmentManager()
                        .beginTransaction();

        transaction.replace(R.id.mainFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void openActivity(Activity to) {
        Intent intent = new Intent(activity, to.getClass());
        activity.startActivity(intent);
    }

    public void openActivityWithExtra(Activity to, Bundle bundle) {
        Intent intent = new Intent(activity, to.getClass());
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
