package com.nlapin.youthsongs.ui.settingsscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.nlapin.youthsongs.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    @BindView(R.id.settingItemsLV)
    ListView settingItemsLV;
    @BindView(R.id.proposeSongBtn)
    MaterialButton proposeSongBtn;
    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        List<String> headers = Arrays.asList(getResources().getStringArray(R.array.settings_headers));
        List<String> subtitles = Arrays.asList(getResources().getStringArray(R.array.settings_subtitles));

        SettingsListViewAdapter adapter = new SettingsListViewAdapter(getActivity(), headers, subtitles);
        settingItemsLV.setAdapter(adapter);


        return view;
    }
}
