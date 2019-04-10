package com.nlapin.youthsongs.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nlapin.youthsongs.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment
        extends Fragment {
    @BindView(R.id.textSizeRG)
    RadioGroup textSizeRG;

    @BindView(R.id.textSizeSmall)
    RadioButton textSizeSmallRB;
    @BindView(R.id.textSizeMedium)
    RadioButton textSizeMediumRB;
    @BindView(R.id.textSizeLarge)
    RadioButton textSizeLargeRB;

    @BindView(R.id.textSizeDemo)
    TextView textSizeDemo;
    @BindView(R.id.aboutBtn)
    Button aboutBtn;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}

