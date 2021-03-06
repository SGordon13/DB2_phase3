package com.example.db2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EditPasswordAdmin extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    EditText etNewPassword;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_student);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String password = intent.getStringExtra("password");
        final String phone = intent.getStringExtra("phone");
        final String name = intent.getStringExtra("name");

        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etNewPassword = (EditText) findViewById(R.id.newPassword);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String newPassword = etNewPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("pleaseHelp", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(EditPasswordAdmin.this, PageAdmin.class);
                                intent.putExtra("email", email);
                                intent.putExtra("password", newPassword);
                                intent.putExtra("phone", phone);
                                intent.putExtra("name", name);
                                EditPasswordAdmin.this.startActivity(intent);
                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditPasswordAdmin.this);
                                builder.setMessage("Email Change Failed").setNegativeButton("Retry", null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                EditPasswordRequest editPasswordRequest = new EditPasswordRequest(email, newPassword, "admin", getString(R.string.url) + "EditPassword.php", responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditPasswordAdmin.this);
                queue.add(editPasswordRequest);
            }
        });
    }
}