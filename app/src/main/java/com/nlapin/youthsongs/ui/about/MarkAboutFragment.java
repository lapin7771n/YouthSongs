package com.nlapin.youthsongs.ui.about;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nlapin.youthsongs.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkAboutFragment extends Fragment {


    @BindView(R.id.linkedInBtn) ImageView linkedInBtn;
    @BindView(R.id.fbBtn) ImageView fbBtn;
    @BindView(R.id.instagramBtn) ImageView instagramBtn;
    @BindView(R.id.emailBtn) ImageView emailBtn;
    @BindView(R.id.twiterBtn) ImageView twiterBtn;
    @BindView(R.id.behanceBtn) ImageView behanceBtn;

    public MarkAboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mark_about_page, container, false);
        ButterKnife.bind(this, view);

        AboutScreenRouter router = ((AboutActivity) getActivity()).getRouter();

        linkedInBtn.setOnClickListener(v ->
                router.openLinkedIn(AboutScreenRouter.DeveloperID.Mark));

        fbBtn.setOnClickListener(v ->
                router.openFacebook(AboutScreenRouter.DeveloperID.Mark));

        instagramBtn.setOnClickListener(v ->
                router.openInstagram(AboutScreenRouter.DeveloperID.Mark));

        emailBtn.setOnClickListener(v ->
                router.openEmail(AboutScreenRouter.DeveloperID.Mark));

        twiterBtn.setOnClickListener(v ->
                router.openTwitter(AboutScreenRouter.DeveloperID.Mark));

        behanceBtn.setOnClickListener(v ->
                router.openBehance());

        return view;
    }

}
