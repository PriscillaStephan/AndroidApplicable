package prescilla.com.eurisko.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import prescilla.com.eurisko.R;

import static prescilla.com.eurisko.utils.Config.LIST_COMPLETED;
import static prescilla.com.eurisko.utils.Config.LIST_ID;
import static prescilla.com.eurisko.utils.Config.LIST_IDUSER;
import static prescilla.com.eurisko.utils.Config.LIST_TITLE;

public class ListDetailsActivity extends AppCompatActivity {
    @BindView(R.id.idN)
    AppCompatTextView idN;
    @BindView(R.id.idUser)
    AppCompatTextView idUser;
    @BindView(R.id.title)
    AppCompatTextView titleN;
    @BindView(R.id.completed)
    AppCompatTextView completedN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        String id = extras.getString(LIST_ID);
        String userId = extras.getString(LIST_IDUSER);
        String title = extras.getString(LIST_TITLE);
        String completed = extras.getString(LIST_COMPLETED);
        idN.setText(id);
        idUser.setText(userId);
        titleN.setText(title);
        completedN.setText(completed);

    }
}
