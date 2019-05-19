package prescilla.com.eurisko.ui;

import android.content.Intent;
import android.os.Bundle;

import prescilla.com.eurisko.BaseActivity;
import prescilla.com.eurisko.MainActivity;
import prescilla.com.eurisko.R;
import prescilla.com.eurisko.SnappyDb.SnappyHelper;
import prescilla.com.eurisko.model.LoginModel;
import prescilla.com.eurisko.registration.LoginActivity;
import prescilla.com.eurisko.utils.Methods;

import static prescilla.com.eurisko.utils.Config.PREFS_LOGGEDIN;
import static prescilla.com.eurisko.utils.Config.PREFS_REG;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AddUserToDb();

        switch (Methods.getPref(SplashActivity.this, PREFS_REG)) {
            case PREFS_LOGGEDIN:
                startActivity(new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                break;

            default:
                startActivity(new Intent(this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
        }

    }

    private void AddUserToDb() {
        LoginModel loginModel = new LoginModel();
        loginModel.setUsername("test");
        loginModel.setPassword("123");
        SnappyHelper.setLoginModel(SplashActivity.this, loginModel);
    }
}
