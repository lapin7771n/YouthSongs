package com.nlapin.youthsongs.ui.settingsscreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nlapin.youthsongs.R;

import java.util.List;

public class SettingsListViewAdapter extends ArrayAdapter<String> {

    private List<String> headers;
    private List<String> subtitles;
    private Activity context;

    public SettingsListViewAdapter(@NonNull Activity context, List<String> headers,
                                   List<String> subtitles) {
        super(context, R.layout.settings_item);
        this.headers = headers;
        this.subtitles = subtitles;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = context.getLayoutInflater()
                .inflate(R.layout.settings_item, parent, true);
        TextView header = view.findViewById(R.id.settingName);
        TextView subtitle = view.findViewById(R.id.settingSubtitle);

        header.setText(headers.get(position));
        subtitle.setText(subtitles.get(position));
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
