package com.nlapin.youthsongs.ui.settingsscreen;

import android.annotation.SuppressLint;
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
import com.nlapin.youthsongs.ui.aboutappscreen.AboutAppActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    @BindView(R.id.settingItemsLV)
    ListView settingItemsLV;
    @BindView(R.id.proposeSongBtn)
    MaterialButton proposeSongBtn;

    private SongRepository songRepository;

    private static final int APPEARANCE_ITEM = 0;
    static final int SONGS_DATABASE_ITEM = 1;
    private static final int ABOUT_APP_ITEM = 2;
    private CompositeDisposable disposables;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        songRepository = YouthSongsApp.getComponent().getSongRepository();
        disposables = new CompositeDisposable();

        List<String> headers = Arrays.asList(getResources().getStringArray(R.array.settings_headers));
        List<String> subtitles = Arrays.asList(getResources().getStringArray(R.array.settings_subtitles));


        SettingsListViewAdapter adapter = new SettingsListViewAdapter(
                Objects.requireNonNull(getActivity()),
                headers,
                subtitles,
                customItemClickListener);

        settingItemsLV.setAdapter(adapter);
        proposeSongBtn.setOnClickListener(v -> {
            EditText nameET = new EditText(getContext());
            nameET.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nameET.setHint(getString(R.string.songName));

            EditText linkET = new EditText(getContext());
            linkET.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linkET.setHint(getString(R.string.linkToSong));

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setPadding(5, 5, 5, 5);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(nameET);
            linearLayout.addView(linkET);

            new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                    .setTitle(getString(R.string.proposeSongAlertDialogLabel))
                    .setMessage(getString(R.string.proposeSongDescription))
                    .setView(linearLayout)
                    .setPositiveButton(getString(R.string.propose), (dialog, which) -> {
                        ProposedSongsRepository.getInstance()
                                .addSongToProposed(nameET.getText().toString(),
                                        linkET.getText().toString());
                        dialog.dismiss();
                        Toast.makeText(getContext(),
                                getString(R.string.proposeSongResponce),
                                Toast.LENGTH_LONG).show();
                    })
                    .setNegativeButton(getString(R.string.cansel), (dialog, which) -> dialog.cancel())
                    .create()
                    .show();
        });

        return view;
    }

    private CustomItemClickListener customItemClickListener = (v, position) -> {
        switch (position) {
            case APPEARANCE_ITEM:
                AppearanceSettingsActivity.start(Objects.requireNonNull(getContext()));
                break;

            case SONGS_DATABASE_ITEM:
                disposables.add(songRepository.getSongCloudRepository().provideAllSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(songs -> songRepository.cacheToLocalDatabase(songs),
                                throwable -> {
                                    Log.e(TAG, "Migrating songs error - ", throwable);

                                    Toast.makeText(getActivity(),
                                            getString(R.string.errorMigratingSongs)
                                                    + throwable.getLocalizedMessage(),
                                            Toast.LENGTH_SHORT).show();

                                }, () -> {
                                    Log.i(TAG, "Songs successfully migrated");

                                    Toast.makeText(getActivity(),
                                            getString(R.string.songsMigrated),
                                            Toast.LENGTH_SHORT).show();


                                    @SuppressLint("SimpleDateFormat")
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm");
                                    String currentDateAndTime = sdf.format(new Date());

                                    PreferenceManager.getDefaultSharedPreferences(getContext())
                                            .edit()
                                            .putString(Objects.requireNonNull(getContext()).getString(
                                                    R.string.last_update_pref),
                                                    currentDateAndTime)
                                            .apply();

                                    TextView subtitle = v.findViewById(R.id.settingSubtitle);
                                    subtitle.setText(String.format(getString(
                                            R.string.last_update_label),
                                            currentDateAndTime));
                                }));
                break;

            case ABOUT_APP_ITEM:
                AboutAppActivity.start(getContext());
                break;

            default:
                Log.d(TAG, "Unhandled position!");
                break;
        }
    };
}
