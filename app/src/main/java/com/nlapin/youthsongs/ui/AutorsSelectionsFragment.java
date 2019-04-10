package com.nlapin.youthsongs.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlapin.youthsongs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AutorsSelectionsFragment extends Fragment {


    public AutorsSelectionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_autors_choise, container, false);

        return view;
    }

}
