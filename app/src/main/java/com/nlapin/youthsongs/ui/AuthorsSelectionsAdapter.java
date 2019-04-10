package com.nlapin.youthsongs.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.AuthorSelectionUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthorsSelectionsAdapter
        extends RecyclerView.Adapter<AuthorsSelectionsAdapter.SelectionViewHolder> {

    private ArrayList<Drawable> backgroundDrawables = new ArrayList<>();
    private ArrayList<AuthorSelectionUI> authorSelectionUIS = new ArrayList<>();

    public AuthorsSelectionsAdapter(@NonNull Context context, List<AuthorSelectionUI> selections) {
        backgroundDrawables.addAll(Arrays.asList(
                ContextCompat.getDrawable(context, R.drawable.authors_choise_blue_bg),
                ContextCompat.getDrawable(context, R.drawable.authors_choise_red_bg)
        ));
        authorSelectionUIS.addAll(selections);
    }

    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.authors_choise_item, parent, false);

        return new SelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectionViewHolder holder, int position) {
        String selectionName = authorSelectionUIS.get(position).getSelectionName();
        int songsCount = authorSelectionUIS.get(position).getSongsCount();

        holder.selectionNameTV.setText(selectionName);
        holder.countOfSongs.setText(String.valueOf(songsCount));
        holder.itemView.setBackground((position % 2 == 0)
                ? backgroundDrawables.get(0) : backgroundDrawables.get(1));
    }

    @Override
    public int getItemCount() {
        return authorSelectionUIS.size();
    }

    class SelectionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.selectionNameTV)
        TextView selectionNameTV;

        @BindView(R.id.countOfSongs)
        TextView countOfSongs;

        private SelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
