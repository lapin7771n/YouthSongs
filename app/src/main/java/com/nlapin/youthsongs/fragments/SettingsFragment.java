package com.nlapin.youthsongs.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nlapin.youthsongs.AboutFragment;
import com.nlapin.youthsongs.MainActivity;
import com.nlapin.youthsongs.R;

import java.util.Objects;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    public static final String APP_PREFERENCES = "appSettings";
    public static final String PREF_TEXT_SIZE = "appSettings";

    private static final int TEXT_SIZE_SMALL = 15;
    public static final int TEXT_SIZE_MED = 20;
    private static final int TEXT_SIZE_LARGE = 30;

    MainActivity parentActivity;

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    SharedPreferences settings;

    @BindView(R.id.textSizeRG) RadioGroup textSizeRG;
    @BindView(R.id.textSizeSmall) RadioButton textSizeSmall;
    @BindView(R.id.textSizeMedium) RadioButton textSizeMedium;
    @BindView(R.id.textSizeLarge) RadioButton textSizeLarge;
    @BindView(R.id.textSizeDemo) TextView textSizeDemo;
    @BindView(R.id.aboutBtn) Button aboutBtn;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        settings = Objects.requireNonNull(getContext()).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        textSizeDemo.setTextSize(settings.getFloat(PREF_TEXT_SIZE, TEXT_SIZE_LARGE));

        textSizeRG.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = settings.edit();

            switch (checkedId) {
                case R.id.textSizeSmall:
                    editor.putFloat(PREF_TEXT_SIZE, TEXT_SIZE_SMALL);
                    textSizeDemo.setTextSize(TEXT_SIZE_SMALL);
                    editor.apply();
                    break;
                case R.id.textSizeMedium:
                    editor.putFloat(PREF_TEXT_SIZE, TEXT_SIZE_MED);
                    textSizeDemo.setTextSize(TEXT_SIZE_MED);
                    editor.apply();
                    break;
                case R.id.textSizeLarge:
                    editor.putFloat(PREF_TEXT_SIZE, TEXT_SIZE_LARGE);
                    textSizeDemo.setTextSize(TEXT_SIZE_LARGE);
                    editor.apply();
                    break;
                default:
                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
        checkedBtn();

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.setFragment(new AboutFragment());
            }
        });
        return view;
    }

    private void checkedBtn() {
        int textSize = (int) settings.getFloat(PREF_TEXT_SIZE, TEXT_SIZE_MED);
        switch (textSize) {
            case TEXT_SIZE_SMALL:
                textSizeSmall.setChecked(true);
                break;
            case TEXT_SIZE_MED:
                textSizeMedium.setChecked(true);
                break;
            case TEXT_SIZE_LARGE:
                textSizeLarge.setChecked(true);
                break;
        }
    }

}
