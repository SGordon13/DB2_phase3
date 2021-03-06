package com.example.db2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LeaveAllButton {
    Button editButton;
    String sEmail;
    String sName;
    String sPEmail;
    String sPassword;
    String sPhone;
    int sGrade;
    int sMid;
    String url;

    public LeaveAllButton(final Context context, String text, String email, String name, int grade, String pEmail, String password, String phone, String myUrl){
        sEmail = email;
        sName = name;
        sPEmail = pEmail;
        sPassword = password;
        sPhone = phone;
        sGrade = grade;
        url = myUrl;
        editButton = new Button(context);
        editButton.setText(text);
        editButton.setId(sMid);
        editButton.setTag(sMid);

        editButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("LeftAllMeeting", response);
                            JSONObject jsonResponse = new JSONObject(response);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                };

                // filler info on mid and ment so i can use the same request but call to a different php file
                LeaveMeetingRequest leaveMeet = new LeaveMeetingRequest(sEmail, 0, "ment", url + "LeaveAll.php", responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(leaveMeet);

                Intent backPage = new Intent(context, PageStudent.class);
                backPage.putExtra("name", sName);
                backPage.putExtra("email", sEmail);
                backPage.putExtra("password", sPassword);
                backPage.putExtra("paEmail", sPEmail);
                backPage.putExtra("grade", sGrade);
                backPage.putExtra("phone", sPhone);
                context.startActivity(backPage);
            }
        });

    }
    public Button getButton(){
        return editButton;
    }
    public int getID(){
        return editButton.getId();
    }
    public int getTag(){
        return (int) editButton.getTag();
    }
}