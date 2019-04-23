package com.nlapin.youthsongs.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.AuthorsSelectionUI;
import com.nlapin.youthsongs.ui.CustomItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthorsSelectionsRVAdapter
        extends RecyclerView.Adapter<AuthorsSelectionsRVAdapter.AuthorsSelectionViewHolder> {
    private static final String TAG = "AuthorsSelectionsRVAdap";

    private List<AuthorsSelectionUI> authorsSelections;
    private final CustomItemClickListener clickListener;
    private ArrayList<Drawable> backgroundDrawables = new ArrayList<>();
    private int textSize;
    private int state;
    public static final int COLLAPSED = 0;
    public static final int EXPANDED = 1;


    public AuthorsSelectionsRVAdapter(List<AuthorsSelectionUI> authorsSelections,
                                      CustomItemClickListener clickListener, Context context) {
        this.clickListener = clickListener;
        this.authorsSelections = new ArrayList<>(authorsSelections);
        backgroundDrawables.addAll(Arrays.asList(
                ContextCompat.getDrawable(context, R.drawable.authors_choise_blue_bg),
                ContextCompat.getDrawable(context, R.drawable.authors_choise_red_bg)
        ));
    }

    public void setAuthorsSelections(List<AuthorsSelectionUI> authorsSelections) {
        this.authorsSelections = authorsSelections;
    }

    @Override
    public int getItemCount() {
        return authorsSelections.size();
    }

    @NonNull
    @Override
    public AuthorsSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.authors_selection_item,
                        viewGroup,
                        false);

        return new AuthorsSelectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorsSelectionViewHolder authorsSelectionViewHolder, int position) {
        AuthorsSelectionUI authorsSelectionUI = authorsSelections.get(position);
        long id = authorsSelectionUI.getId();
        String selectionName = authorsSelectionUI.getSelectionName();
        int songsCount = authorsSelectionUI.getSongsCount();

        authorsSelectionViewHolder.itemView.setBackground((position % 2 == 0)
                ? backgroundDrawables.get(0)
                : backgroundDrawables.get(1));
        authorsSelectionViewHolder.selectionNameTV.setText(selectionName);
        authorsSelectionViewHolder.countOfSongs.setText(String.valueOf(songsCount) + " songs");

        authorsSelectionViewHolder.itemView.setOnClickListener(v ->
                clickListener.onItemClick(v, (int) id));

        authorsSelectionViewHolder.selectionNameTV.setTextSize(textSize);

        switch (state) {
            case COLLAPSED:
                authorsSelectionViewHolder.countOfSongs.setVisibility(View.VISIBLE);
                break;
            case EXPANDED:
                authorsSelectionViewHolder.countOfSongs.setVisibility(View.GONE);
                break;
        }

        authorsSelectionViewHolder.itemView.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                    textSize = bottom / 10;
                    authorsSelectionViewHolder.selectionNameTV.setTextSize(textSize);
                    if (bottom > oldBottom) {
                        state = EXPANDED;
                        authorsSelectionViewHolder.countOfSongs.setVisibility(View.VISIBLE);
                    } else {
                        state = COLLAPSED;
                        authorsSelectionViewHolder.countOfSongs.setVisibility(View.GONE);
                    }
                });
    }


    class AuthorsSelectionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.selectionNameTV)
        TextView selectionNameTV;

        @BindView(R.id.countOfSongs)
        TextView countOfSongs;

        AuthorsSelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
