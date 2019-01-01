package com.nlapin.youthsongs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    @BindView(R.id.aboutFL) FrameLayout aboutFL;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AboutView view = AboutBuilder.with(getContext())
                .setAppIcon(R.mipmap.ic_launcher_round)
                .setAppName(R.string.app_name)
                .setPhoto(R.mipmap.nikita)
                .setCover(R.mipmap.cover)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName("Nikita")
                .setSubTitle("Java Developer")
                .setLinksColumnsCount(4)
                .setBrief("This app has been made for Severodonetsk youth")
                .addGitHubLink("lapin7771n")
                .addBitbucketLink("nlapin")
                .addFacebookLink("nikita.lapin.3")
                .addTwitterLink("nik_lapin_")
                .addInstagramLink("nikitikitavi_")
                .addLinkedInLink("nikita-lapin-b7a98615a")
                .addEmailLink("nlapin.java@gmail.com")
                .addSkypeLink("lapin7771n")
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addFiveStarsAction()
                .addFeedbackAction("nlapin.java@gmail.com")
                .setWrapScrollView(true)
                .setShowAsCard(true)
                .build();


        return view;
    }

}
