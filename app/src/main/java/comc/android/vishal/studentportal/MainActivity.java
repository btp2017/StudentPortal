package comc.android.vishal.studentportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import comc.android.vishal.studentportal.Auth.ManageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void manageButtonPressed(View v) {
        Intent intent = new Intent(this, ManageActivity.class);
        startActivity(intent);
    }
    public void detailsButtonPressed(View v) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        startActivity(intent);
    }

    public void requestNotificationButtonPressed(View v) {
        Intent intent = new Intent(this, RequestNotifiacationActivity.class);
        startActivity(intent);
    }

    public void noticeBoardButtonPressed(View v) {
        Intent intent = new Intent(this, NoticeBoardActivity.class);
        startActivity(intent);
    }
}
