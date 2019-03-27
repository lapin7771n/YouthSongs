package com.nlapin.youthsongs.ui.about;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nlapin.youthsongs.BuildConfig;
import com.nlapin.youthsongs.R;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppAboutFragment extends Fragment {


    @BindView(R.id.telegramBtn)
    ImageView telegramBtn;
    @BindView(R.id.aboutAppTV)
    TextView aboutAppTV;

    public AppAboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_about, container, false);
        ButterKnife.bind(this, view);

        AboutScreenRouter router = ((AboutActivity) getActivity()).getRouter();

        telegramBtn.setOnClickListener((v) -> router.openTelegramChannel());

        CharSequence aboutAppTVText = aboutAppTV.getText();
        String aboutAppText = String.valueOf(aboutAppTVText);
        String versionName = BuildConfig.VERSION_NAME;
        String aboutNewText = aboutAppText.replace("%version%", versionName);
        aboutAppTV.setText(aboutNewText);

        return view;
    }

}
