package prescilla.com.eurisko;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import prescilla.com.eurisko.connectivity.ConnectionDetector;


public class BaseActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean isConnection() {
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet())
            return true;
        else
            Toast.makeText(getApplicationContext(), getString(R.string.no_connection), Toast.LENGTH_SHORT).show();

        return false;
    }
}
