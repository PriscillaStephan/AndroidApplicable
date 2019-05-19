package prescilla.com.eurisko;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prescilla.com.eurisko.model.DrawerCategory;
import prescilla.com.eurisko.ui.GalleryActivity;
import prescilla.com.eurisko.ui.ListActivity;
import prescilla.com.eurisko.ui.SplashActivity;
import prescilla.com.eurisko.ui.TimerActivity;
import prescilla.com.eurisko.utils.Methods;

import static prescilla.com.eurisko.utils.Methods.createNotification;

public class MainActivity extends BaseActivity {
    public static final String TIMER_SCREEN = "110";
    public static final String LIST_VIEW = "111";

    public static final String LOGOUT = "112";
    public static final String GALLERY = "113";
    private List<DrawerCategory> mCategoryList;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setTitle(R.string.app_name);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setupDrawerRecyclerView((RecyclerView) findViewById(R.id.recyclerview));

    }

    @OnClick(R.id.notify_bt)
    public void OnClickNotify() {
        createNotification(this, "test", "this is a test");
    }


    private void setupDrawerRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(this, new ArrayList<DrawerCategory>()));
    }

    public class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final View seperator_view;
            public final TextView mTextView;
            public final AppCompatImageView icon;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTextView = view.findViewById(android.R.id.text1);
                seperator_view = view.findViewById(R.id.seperator_view);
                icon = view.findViewById(R.id.icon);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<DrawerCategory> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mCategoryList = items;
            addMandatoryItems();
        }

        private void addMandatoryItems() {
            final DrawerCategory timerCategory = new DrawerCategory();
            timerCategory.setId(TIMER_SCREEN);
            timerCategory.setTitle(getString(R.string.timerCategory));

            final DrawerCategory listCategory = new DrawerCategory();
            listCategory.setId(LIST_VIEW);
            listCategory.setTitle(getString(R.string.listCategory));


            final DrawerCategory gallery = new DrawerCategory();
            gallery.setId(GALLERY);
            gallery.setTitle(getString(R.string.gallery));

            final DrawerCategory logout = new DrawerCategory();
            logout.setId(LOGOUT);
            logout.setTitle(getString(R.string.logout));
            logout.setIcon(R.drawable.ic_exit);
            // Our Showroom, Our Service Centers, Our Distributors

            mCategoryList.add(timerCategory);
            mCategoryList.add(listCategory);
            mCategoryList.add(gallery);
            mCategoryList.add(logout);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.mBoundString = mCategoryList.get(position).getTitle();
            holder.mTextView.setText(mCategoryList.get(position).getTitle());
            holder.icon.setImageResource(mCategoryList.get(position).getIcon());

            if (mCategoryList.get(position).getId() != LIST_VIEW)
                holder.seperator_view.setVisibility(View.VISIBLE);
            else
                holder.seperator_view.setVisibility(View.GONE);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onNavigationItemSelected(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }
    }

    public void onNavigationItemSelected(int position) {

        switch (mCategoryList.get(position).getId()) {

            case TIMER_SCREEN:
                startActivity(new Intent(this, TimerActivity.class));
                break;

            case LIST_VIEW:
                startActivity(new Intent(this, ListActivity.class));
                break;
            case GALLERY:
                startActivity(new Intent(this, GalleryActivity.class));
                break;
            case LOGOUT:

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure that you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(MainActivity.this, SplashActivity.class));
                        Methods.clearPref(MainActivity.this);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

                break;
            default:
        }
        drawer.closeDrawer(GravityCompat.START);
    }

}
