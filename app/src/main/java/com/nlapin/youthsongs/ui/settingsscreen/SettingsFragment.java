package com.nlapin.youthsongs.ui.settingsscreen;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.YouthSongsApp;
import com.nlapin.youthsongs.data.SongRepository;
import com.nlapin.youthsongs.data.remote.ProposedSongsRepository;
import com.nlapin.youthsongs.ui.CustomItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    @BindView(R.id.settingItemsLV)
    ListView settingItemsLV;
    @BindView(R.id.proposeSongBtn)
    MaterialButton proposeSongBtn;
    private SettingsViewModel mViewModel;

    private SongRepository songRepository;

    public static final int SONGS_DATABASE_ITEM = 1;
    public static final int APPEARANCE_ITEM = 0;
    private Disposable disposable;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        songRepository = YouthSongsApp.getComponent().getSongRepository();

        List<String> headers = Arrays.asList(getResources().getStringArray(R.array.settings_headers));
        List<String> subtitles = Arrays.asList(getResources().getStringArray(R.array.settings_subtitles));


        SettingsListViewAdapter adapter = new SettingsListViewAdapter(getActivity(),
                headers,
                subtitles,
                customItemClickListener);

        settingItemsLV.setAdapter(adapter);
        proposeSongBtn.setOnClickListener(v -> {
            EditText nameET = new EditText(getContext());
            nameET.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nameET.setHint("Song name");

            EditText linkET = new EditText(getContext());
            linkET.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linkET.setHint("Link to song");

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(nameET);
            linearLayout.addView(linkET);

            new AlertDialog.Builder(getContext())
                    .setTitle("Thanks for your help")
                    .setMessage("Provide information about song below")
                    .setView(linearLayout)
                    .setPositiveButton("Propose", (dialog, which) -> {
                        new ProposedSongsRepository()
                                .addSongToProposed(nameET.getText().toString(),
                                        linkET.getText().toString());
                        dialog.dismiss();
                        Toast.makeText(getContext(),
                                "Thanks. We are check this song and maybe add it soon",
                                Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .create()
                    .show();
        });

        return view;
    }

    private CustomItemClickListener customItemClickListener = (v, position) -> {
        switch (position) {
            case APPEARANCE_ITEM:
                AppearanceSettingsActivity.start(getContext());
                break;

            case SONGS_DATABASE_ITEM:
                disposable = songRepository.getSongCloudRepository().provideAllSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(songs -> {
                            songRepository.cacheToLocalDatabase(songs);
                        }, throwable -> {
                            Log.e(TAG, "Migrating songs error - ", throwable);
                            Toast.makeText(getActivity(),
                                    "Error while migrating songs - "
                                            + throwable.getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }, () -> {
                            Log.i(TAG, "Songs successfully migrated");
                            Toast.makeText(getActivity(),
                                    "Songs successfully migrated",
                                    Toast.LENGTH_SHORT).show();
                            TextView subtitle = v.findViewById(R.id.settingSubtitle);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm");
                            String currentDateAndTime = sdf.format(new Date());
                            PreferenceManager.getDefaultSharedPreferences(getContext())
                                    .edit()
                                    .putString(getContext().getString(
                                            R.string.last_update_pref),
                                            currentDateAndTime)
                                    .apply();
                            subtitle.setText(String.format(getString(
                                    R.string.last_update_label),
                                    currentDateAndTime));
//                                        disposable.dispose();
                        });
                break;
        }
    };
}
