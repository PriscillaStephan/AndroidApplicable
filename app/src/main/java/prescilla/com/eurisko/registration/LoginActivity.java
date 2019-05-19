package prescilla.com.eurisko.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prescilla.com.eurisko.BaseActivity;
import prescilla.com.eurisko.MainActivity;
import prescilla.com.eurisko.R;
import prescilla.com.eurisko.SnappyDb.SnappyHelper;
import prescilla.com.eurisko.model.LoginModel;
import prescilla.com.eurisko.utils.Methods;

import static prescilla.com.eurisko.utils.Config.PREFS_LOGGEDIN;
import static prescilla.com.eurisko.utils.Config.PREFS_REG;
import static prescilla.com.eurisko.utils.Methods.getStringDate;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    AppCompatEditText usernameEt;
    @BindView(R.id.password)
    AppCompatEditText passwordEt;
    @BindView(R.id.login)
    AppCompatButton login;
    @BindView(R.id.keep_login)
    CheckBox keep_login;
    Boolean canLogin = true;
    private LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.keep_login)
    public void OnKeepLogedIn() {
        if (keep_login.isChecked()) {
            Methods.savePre(LoginActivity.this, PREFS_LOGGEDIN, PREFS_REG);
        } else {
            Methods.savePre(LoginActivity.this, " ", PREFS_REG);
        }
    }

    @OnClick(R.id.login)
    public void OnClickLogin() {
        canLogin = true;
        if (usernameEt.getText().toString().isEmpty()) {
            canLogin = false;
            usernameEt.setError("Username can not be empty");
        } else if (passwordEt.getText().toString().isEmpty()) {
            canLogin = false;
            passwordEt.setError("Password can not be empty");
        }

        if (canLogin) {
            loginModel = SnappyHelper.getLoginModel(this);
            if (loginModel.getUsername().equals(usernameEt.getText().toString()) && loginModel.getPassword().equals(passwordEt.getText().toString())) {
                SnappyHelper.setTimeLoggedin(LoginActivity.this, getStringDate(new Date()));
                SnappyHelper.setLoginModel(LoginActivity.this, loginModel);
                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();

            } else {
                Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
