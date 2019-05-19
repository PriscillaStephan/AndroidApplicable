package prescilla.com.eurisko.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import prescilla.com.eurisko.BaseActivity;
import prescilla.com.eurisko.R;
import prescilla.com.eurisko.SnappyDb.SnappyHelper;

public class TimerActivity extends BaseActivity {
    @BindView(R.id.loginDate_tv)
    AppCompatTextView loginDate;
    @BindView(R.id.appTimer_tv)
    AppCompatTextView timer;

    private boolean pauseTimer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);
        updateDisplay();
        if (SnappyHelper.getTimeLoggedIn(this) != null) {
            loginDate.setText(SnappyHelper.getTimeLoggedIn(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pauseTimer = false;
        mRunnable.run();
    }


    @Override
    protected void onStop() {
        super.onStop();
        pauseTimer = true;
    }

    int time = 0;
    Runnable mRunnable;

    private void updateDisplay() {

        final Handler handler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (!pauseTimer) {
                    String result = String.format("%02dm:%02ds", time / 60, time % 60);
                    timer.setText(result);
                    time++;
                    handler.postDelayed(this, 1000);
                }
            }
        };


    }

}
