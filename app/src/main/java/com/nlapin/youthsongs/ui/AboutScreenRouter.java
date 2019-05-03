package com.nlapin.youthsongs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

/**
 * @author nikita on 17,February,2019
 */
public class AboutScreenRouter extends BaseRouter {

    FragmentActivity activity;

    public AboutScreenRouter(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
    }

    void openLinkedIn(DeveloperID developerID) {
        String link = "";
        if (developerID.equals(DeveloperID.Nikita)) {
            link = "https://www.linkedin.com/in/nikita-lapin-b7a98615a/";
        } else if (developerID.equals(DeveloperID.Mark)) {
            link = "https://www.linkedin.com/in/mark-altytsia-463287175/";
        }
        openExternalActivity(link);
    }

    void openFacebook(DeveloperID developerID) {
        if (developerID.equals(DeveloperID.Nikita)) {
            openExternalActivity("https://www.facebook.com/nikita.lapin.3");
        } else if (developerID.equals(DeveloperID.Mark)) {
            openExternalActivity("https://www.facebook.com/MarkAltytsia");
        }
    }

    void openInstagram(DeveloperID developerID) {
        if (developerID.equals(DeveloperID.Nikita)) {
            openExternalActivity("https://www.instagram.com/nikilapi/");
        } else if (developerID.equals(DeveloperID.Mark)) {
            openExternalActivity("https://www.instagram.com/mluck_ph/");
        }
    }

    public void openEmail(DeveloperID developerID) {
        if (developerID.equals(DeveloperID.Nikita)) {
            openEmailSender("nlapin.java@gmail.com");
        } else if (developerID.equals(DeveloperID.Mark)) {
            openEmailSender("photofreelan@gmail.com");
        }
    }

    public void openEmail(DeveloperID developerID, String text) {
        if (developerID.equals(DeveloperID.Nikita)) {
            openEmailSender("nlapin.java@gmail.com", text);
        } else if (developerID.equals(DeveloperID.Mark)) {
            openEmailSender("photofreelan@gmail.com", text);
        }
    }

    void openTwitter(DeveloperID developerID) {
        if (developerID.equals(DeveloperID.Nikita)) {
            openExternalActivity("https://twitter.com/nik_lapin_");
        } else if (developerID.equals(DeveloperID.Mark)) {
            openExternalActivity("https://twitter.com/altytsa_M");
        }
    }

    void openGithub() {
        openExternalActivity("https://github.com/lapin7771n");
    }

    void openBehance() {
        openExternalActivity("https://www.behance.net/markroll455eab");
    }

    void openTelegramChannel() {
        openExternalActivity("https://t.me/ysongs");
    }

    public enum DeveloperID {
        Nikita,
        Mark
    }
}
