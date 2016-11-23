package me.abala.codeup.androiddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import me.abala.codeup.androiddemo.FlickrApi;
import me.abala.codeup.androiddemo.R;

/**
 * Activity used to load image URLs.
 */
public class LoaderActivity extends Activity {

    private RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_loader);
    }

    @Override
    public void onResume() {
        super.onResume();

        String url = "https://api.flickr.com/services/rest?" +
                "method=flickr.interestingness.getList&" +
                "format=json&" +
                "nojsoncallback=1&" +
                "api_key=" + FlickrApi.KEY;

        queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (readUrlsFromResponse(response)) {
                            Intent intent = new Intent(LoaderActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                            startActivity(intent);
                            finish();

                        } else {
                            exitOnLoadError();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        exitOnLoadError();
                    }
                });
        queue.add(jsObjRequest);
    }

    @Override
    public void onPause() {
        super.onPause();

        queue.cancelAll(this);
    }

    private boolean readUrlsFromResponse(JSONObject response) {
        try {
            Log.i("json", response.toString(2));
            return true;

        } catch (JSONException e) {
            return false;
        }
    }

    private void exitOnLoadError() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.err_failed_to_get_image_info);
        int duration = Toast.LENGTH_LONG;

        Toast.makeText(context, text, duration).show();

        finish();
    }
}
