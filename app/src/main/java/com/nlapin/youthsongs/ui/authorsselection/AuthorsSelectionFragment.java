package com.nlapin.youthsongs.ui.authorsselection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.AuthorsSelectionUI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthorsSelectionFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    public static final int COLLAPSED = 0;
    public static final int EXPANDED = 1;
    private int state;

    final static ArrayList<AuthorsSelectionUI> authorsSelections = new ArrayList<>();

    static {
        authorsSelections.add(new AuthorsSelectionUI("Test", new ArrayList<>()));
        authorsSelections.add(new AuthorsSelectionUI("Medium Text Test", new ArrayList<>()));
        authorsSelections.add(new AuthorsSelectionUI("Very very long name test", new ArrayList<>()));
    }

    @BindView(R.id.selectionNameTV)
    TextView selectionNameTV;
    @BindView(R.id.countOfSongs)
    TextView countOfSongs;

    private int pageNumber;

    public static AuthorsSelectionFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_PAGE_NUMBER, page);
        AuthorsSelectionFragment fragment = new AuthorsSelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.authors_selection_item, null);
        ButterKnife.bind(this, view);

        AuthorsSelectionUI authorsSelectionUI = authorsSelections.get(pageNumber);
        selectionNameTV.setText(authorsSelectionUI.getSelectionName());
        countOfSongs.setText(authorsSelectionUI.getSongsCount() + " songs");

        return view;
    }


}
