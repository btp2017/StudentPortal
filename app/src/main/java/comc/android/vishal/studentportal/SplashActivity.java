package comc.android.vishal.studentportal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import comc.android.vishal.studentportal.Auth.LoginActivity;


public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT;

    /* renamed from: com.example.sulabhkumar.testing.SplashActivity.1 */
    class C01981 implements Runnable {
        C01981() {
        }

        public void run() {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            SplashActivity.this.finish();
        }
    }

    static {
        SPLASH_TIME_OUT = 2000;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new C01981(), (long) SPLASH_TIME_OUT);
    }
}