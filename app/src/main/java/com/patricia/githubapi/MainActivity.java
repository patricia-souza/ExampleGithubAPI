package com.patricia.githubapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.patricia.githubapi.activities.UserActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonSearchUser;
    private EditText editTextUserName;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSearchUser = (Button) findViewById(R.id.btn_search_user);
        editTextUserName = (EditText) findViewById(R.id.input_username);
    }

    public void getUser(View view){
        intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra("STRING_I_NEED", editTextUserName.getText().toString());
        startActivity(intent);
    }
}
