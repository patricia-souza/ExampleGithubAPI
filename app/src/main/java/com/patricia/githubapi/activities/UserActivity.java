package com.patricia.githubapi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.patricia.githubapi.R;
import com.patricia.githubapi.model.GitHubUser;
import com.patricia.githubapi.rest.APIClient;
import com.patricia.githubapi.rest.GitHubUserEndPoints;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private String TAG = UserActivity.class.getSimpleName();
    private ImageView avatarImg;
    private TextView userNameTV;
    private TextView followersTV;
    private TextView followingTV;
    private TextView logIn;
    private TextView email;
    private Button ownedrepos;
    private Bundle extras;
    private String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        avatarImg = (ImageView) findViewById(R.id.avatar);
        userNameTV = (TextView) findViewById(R.id.username);
        followersTV = (TextView) findViewById(R.id.followers);
        followingTV = (TextView) findViewById(R.id.following);
        logIn = (TextView) findViewById(R.id.logIn);
        email = (TextView) findViewById(R.id.email);
        ownedrepos = (Button) findViewById(R.id.ownedReposBtn);
        extras = getIntent().getExtras();
        newString = extras.getString("STRING_I_NEED");
        loadData();
    }

    public void loadOwnRepos(View view){
        Intent intent = new Intent(UserActivity.this,RepositoriesActivity.class);
        intent.putExtra("username", newString);
        startActivity(intent);
    }


    public void loadData() {

        final GitHubUserEndPoints apiService = APIClient.getClient().create(GitHubUserEndPoints.class);

        Call<GitHubUser> call = apiService.getUser(newString);

        call.enqueue(new Callback<GitHubUser>() {

            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser>
                    response) {

                Picasso.with(UserActivity.this)
                        .load(response.body().getAvatar())
                        .resize(220,220)
                        .into(avatarImg);

                if(response.body().getName() == null){
                    userNameTV.setText("No name provided");
                } else {
                    userNameTV.setText("Username: " + response.body().getName());
                }

                followersTV.setText("Followers: " + response.body().getFollowers());
                followingTV.setText("Following: " + response.body().getFollowing());
                logIn.setText("Login: " + response.body().getLogin());

                if(response.body().getEmail() == null){
                    email.setText("No email provided");
                } else{
                    email.setText("Email: " + response.body().getEmail());
                }
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                Log.e(TAG, "Failed to get data. " + t.toString() );
                Toast.makeText(UserActivity.this, "Falha ao recuperar dados.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
