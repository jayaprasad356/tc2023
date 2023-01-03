package com.greymatter.telugucalender.Activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.greymatter.telugucalender.R;
import com.greymatter.telugucalender.helper.ApiConfig;
import com.greymatter.telugucalender.helper.Constant;
import com.greymatter.telugucalender.helper.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {
    Activity activity;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = SplashScreenActivity.this;
        databaseHelper = new DatabaseHelper(activity);




        if (ApiConfig.isConnected(activity)){
            getDatalist();
        }

    }


    private void getDatalist()
    {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Log.d("response", response);
                        databaseHelper.deleteDb(activity);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.PANCHANGAM_LIST);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                databaseHelper.AddToPanchangam(jsonObject1.getString(Constant.ID),jsonObject1.getString(Constant.DATE),jsonObject1.getString(Constant.SUNRISE),jsonObject1.getString(Constant.SUNSET),jsonObject1.getString(Constant.MOONRISE), jsonObject1.getString(Constant.MOONSET));
                            } else {
                                break;
                            }
                        }
                        JSONArray jsonArray2 = object.getJSONArray(Constant.PANCHANGAM_TAB_LIST);

                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject jsonObject1 = jsonArray2.getJSONObject(i);
                            if (jsonObject1 != null) {
                                databaseHelper.AddToPanchangamTab(jsonObject1.getString(Constant.ID),jsonObject1.getString(Constant.PANCHANGAM_ID),jsonObject1.getString(Constant.TITLE),jsonObject1.getString(Constant.DESCRIPTION));


                            } else {
                                break;
                            }
                        }
                        JSONArray jsonArray3 = object.getJSONArray(Constant.FESTIVALS_LIST);


                        Intent i = new Intent(activity, HomeActivity.class);
                        startActivity(i);
                        finish();


                    }
                    else {


                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.ALLDATALIST_URL, params,true);


    }

}