package prescilla.com.eurisko.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import prescilla.com.eurisko.BaseActivity;
import prescilla.com.eurisko.R;
import prescilla.com.eurisko.adapter.ListItemAdapter;
import prescilla.com.eurisko.model.DataList;

public class ListActivity extends BaseActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private ListItemAdapter listAdapter;
    boolean onRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        if (isConnection()) {
            progressBar.setVisibility(View.VISIBLE);
            getList();
        }
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
                swipe.setRefreshing(false);
                onRefresh = true;
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    void getList() {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                StringBuilder responseString = new StringBuilder();
                try {
                    HttpsURLConnection urlConnection = (HttpsURLConnection) new URL("https://jsonplaceholder.typicode.com/todos").openConnection();
                    urlConnection.setRequestMethod("GET");
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            responseString.append(line);
                        }
                    }
                    urlConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return responseString.toString();
            }

            @Override
            protected void onPostExecute(String o) {
                try {
                    Gson gson = new Gson();
                    Type list = new TypeToken<List<DataList>>() {
                    }.getType();
                    List<DataList> result = gson.fromJson(o, list);
                    if (onRefresh)
                        listAdapter.notifyDataSetChanged();
                    else
                        setupAdapter(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }.execute("");
    }

    void setupAdapter(List<DataList> result) {
        progressBar.setVisibility(View.GONE);
        listAdapter = new ListItemAdapter(ListActivity.this, result);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
    }
}

