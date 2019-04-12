package com.nlapin.youthsongs.ui.homescreen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.models.AuthorsSelectionUI;
import com.nlapin.youthsongs.ui.adapters.AuthorsSelectionsRVAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthorsSelectionsFragment extends Fragment {


    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.authorsSelectionsRV)
    RecyclerView authorsSelectionsRV;

    public AuthorsSelectionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_autors_choise, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        final ArrayList<AuthorsSelectionUI> authorsSelections = new ArrayList<>();
        authorsSelections.add(new AuthorsSelectionUI("Test", new ArrayList<>()));
        authorsSelections.add(new AuthorsSelectionUI("Test", new ArrayList<>()));
        authorsSelections.add(new AuthorsSelectionUI("Test", new ArrayList<>()));
        final AuthorsSelectionsRVAdapter adapter = new AuthorsSelectionsRVAdapter(
                authorsSelections,
                (v, position) -> {

                },
                getContext());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        authorsSelectionsRV.setLayoutManager(layoutManager);
        authorsSelectionsRV.setAdapter(adapter);
    }

}
