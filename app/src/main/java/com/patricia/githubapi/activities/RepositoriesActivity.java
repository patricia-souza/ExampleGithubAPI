package com.patricia.githubapi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.patricia.githubapi.R;
import com.patricia.githubapi.adapter.ReposAdapter;
import com.patricia.githubapi.model.GitHubRepo;
import com.patricia.githubapi.rest.APIClient;
import com.patricia.githubapi.rest.GitHubRepoEndPoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoriesActivity extends AppCompatActivity {

    private String receivedUserName;
    private TextView userNameTV;
    private RecyclerView recyclerView;
    private List<GitHubRepo> dataSource = new ArrayList<>();
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        Bundle extras = getIntent().getExtras();
        receivedUserName = extras.getString("username");

        userNameTV = (TextView) findViewById(R.id.userNameTV);

        userNameTV.setText("User: " + receivedUserName);

        recyclerView = (RecyclerView) findViewById(R.id.repos_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new ReposAdapter(dataSource, R.layout.item_repository, getApplicationContext());
        recyclerView.setAdapter(myAdapter);
        loadRepositories();
    }

    public void loadRepositories() {

        GitHubRepoEndPoint apiService = APIClient.getClient().create(GitHubRepoEndPoint.class);

        Call<List<GitHubRepo>> call = apiService.getRepo(receivedUserName);

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                dataSource.clear();
                dataSource.addAll(response.body());
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Log.e("Failed get repos:", t.toString());
                Toast.makeText(RepositoriesActivity.this, "Falha ao recuperar dados.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
