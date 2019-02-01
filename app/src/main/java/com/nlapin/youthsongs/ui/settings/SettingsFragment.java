package com.nlapin.youthsongs.ui.settings;


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

import com.nlapin.youthsongs.PresenterManager;
import com.nlapin.youthsongs.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment
        extends Fragment
        implements SettingsContract.View {

    public static final String APP_PREFERENCES = "appSettings";
    public static final String PREF_TEXT_SIZE = "textSizePref";

    private SettingsPresenter presenter;

    private SharedPreferences settings;

    @BindView(R.id.textSizeRG) RadioGroup textSizeRG;

    @BindView(R.id.textSizeSmall) RadioButton textSizeSmallRB;
    @BindView(R.id.textSizeMedium) RadioButton textSizeMediumRB;
    @BindView(R.id.textSizeLarge) RadioButton textSizeLargeRB;

    @BindView(R.id.textSizeDemo) TextView textSizeDemo;
    @BindView(R.id.aboutBtn) Button aboutBtn;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState == null) {
            presenter = new SettingsPresenter(this);
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        settings = getContext()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        presenter.setTextSize(settings.getInt(PREF_TEXT_SIZE, presenter.defaultTextSize()));

        textSizeRG.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = settings.edit();

            switch (checkedId) {
                case R.id.textSizeSmall:
                    editor.putInt(PREF_TEXT_SIZE, presenter.setSmallTextSize());
                    textSizeDemo.setMaxLines(6);
                    editor.apply();
                    break;
                case R.id.textSizeMedium:
                    editor.putInt(PREF_TEXT_SIZE, presenter.setMediumTextSize());
                    textSizeDemo.setMaxLines(6);
                    editor.apply();
                    break;
                case R.id.textSizeLarge:
                    editor.putInt(PREF_TEXT_SIZE, presenter.setLargeTextSize());
                    textSizeDemo.setMaxLines(6);
                    editor.apply();
                    break;
                default:
                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });


        aboutBtn.setOnClickListener(v -> {
            //parentActivity.setFragment(new AboutFragment());
        });
        presenter.setUpRadioBtn();
        return view;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void setTextSizeForDemoTV(int textSize) {
        textSizeDemo.setTextSize(textSize);
    }

    @Override
    public void setRadioBtnChecked(int radioBtnNum) {
        switch (radioBtnNum) {
            case 0:
                textSizeSmallRB.setChecked(true);
                break;
            case 1:
                textSizeMediumRB.setChecked(true);
                break;
            case 2:
                textSizeLargeRB.setChecked(true);
                break;
        }
    }
}

