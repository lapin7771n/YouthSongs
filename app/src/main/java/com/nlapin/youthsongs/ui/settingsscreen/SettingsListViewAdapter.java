package com.nlapin.youthsongs.ui.settingsscreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.CustomItemClickListener;

import java.util.List;

import static com.nlapin.youthsongs.ui.settingsscreen.SettingsFragment.SONGS_DATABASE_ITEM;

public class SettingsListViewAdapter extends ArrayAdapter<String> {

    private List<String> headers;
    private List<String> subtitles;
    private Activity context;
    private CustomItemClickListener listener;

    public SettingsListViewAdapter(@NonNull Activity context, List<String> headers,
                                   List<String> subtitles, CustomItemClickListener listener) {
        super(context, R.layout.settings_item);
        this.headers = headers;
        this.subtitles = subtitles;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = context.getLayoutInflater()
                .inflate(R.layout.settings_item, parent, false);
        TextView header = view.findViewById(R.id.settingName);
        TextView subtitle = view.findViewById(R.id.settingSubtitle);

        header.setText(headers.get(position));
        if (position == SONGS_DATABASE_ITEM) {
            String lastUpdate = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(context.getString(R.string.last_update_pref),
                            context.getString(R.string.last_update_label));
            subtitle.setText(String.format(subtitles.get(position), lastUpdate));
        } else {
            subtitle.setText(subtitles.get(position));
        }

        view.setOnClickListener(v -> listener.onItemClick(v, position));
        return view;
    }

    @Override
    public int getCount() {
        return headers.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }
}
