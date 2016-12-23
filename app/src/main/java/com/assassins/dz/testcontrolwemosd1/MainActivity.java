package com.assassins.dz.testcontrolwemosd1;

import android.os.AsyncTask;
import android.os.Debug;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity {

    private ToggleButton led1;
    private static final String TAG = "HttpLog";
    private WebView webContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webContent = (WebView) findViewById(R.id.webContent);

        led1 = (ToggleButton) findViewById(R.id.led1);

        led1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HttpRequestTest","SendRequest>>>>>");

                JSONObject testSendJsonObj = new JSONObject();
                try {
                    testSendJsonObj.put("showData",true);
                    testSendJsonObj.put("dataInt",15);
                    Log.d("HttpRequestTest","JSONObject > "+testSendJsonObj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestParams params = new RequestParams();
                params.put("testSendJsonObj", testSendJsonObj.toString());
                Log.d("HttpRequestTest","RequestParams > "+params);

                AsyncHttpClient client = new AsyncHttpClient();
                client.post("http://192.168.4.1/testJsonObj", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
//                        updateWebView(response);
                        Log.d("HttpRequestTest",response);
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            JSONObject testObj = mainObject.getJSONObject("jsonTest");
                            boolean obj1 = testObj.getBoolean("success");
                            Log.d("HttpRequestTest",Boolean.toString(obj1));
                            if (obj1)
                                Log.d("HttpRequestTest",testObj.getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

//        led1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("HttpRequestTest","SendRequest>>>>>");
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.get("http://192.168.4.1", new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
////                        updateWebView(response);
//                        Log.d("HttpRequestTest",response);
//                        try {
//                            JSONObject mainObject = new JSONObject(response);
//                            JSONObject testObj = mainObject.getJSONObject("jsonTest");
//                            boolean obj1 = testObj.getBoolean("success");
//                            Log.d("HttpRequestTest",Boolean.toString(obj1));
//                            if (obj1)
//                                Log.d("HttpRequestTest",testObj.getString("name"));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });

    }//end onCreate

    private void updateWebView(String result) {
        webContent.getSettings().setJavaScriptEnabled(true);
        webContent.loadData(result, "text/html; charset=utf-8", "utf-8");
    }



}//end MainActivity
