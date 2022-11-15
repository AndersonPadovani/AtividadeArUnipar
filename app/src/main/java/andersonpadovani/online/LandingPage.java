package andersonpadovani.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, LoginPage.class);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 1000);
    }
}