/* class ScoreRequest
 * author : Maxime FLASQUIN
 * Date : 18/02/2014
 * Description : Ajoute un score dans la bdd distante
 */

package com.vlaxim.trivia;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

public class ScoreRequest {

	private RequestQueue mRequestQueue;

	public ScoreRequest(NetworkRequest app, final String idUser,
			final String score) {

		// On récupère notre RequestQueue
		mRequestQueue = app.getVolleyRequestQueue();

		String url = "http://max-site.fr/ws/public/score/index.php?method=setScore";
		StringRequest postRequest = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// response
						Log.d("Response", response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// error
						Log.d("Error.Response", error.toString());
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("idUser", idUser);
				params.put("score", score);

				return params;
			}
		};
		mRequestQueue.add(postRequest);

	}

}
