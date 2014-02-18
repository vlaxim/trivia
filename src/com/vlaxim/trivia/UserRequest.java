/* class User Request
 * author : Maxime FLASQUIN
 * Date : 18/02/2014
 * Description : Requête réseau pour ajouter un utilisateur dans la bdd distante
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

public class UserRequest {

	private RequestQueue mRequestQueue;

	public UserRequest(NetworkRequest app, final String firstName,
			final String lastName, final String pseudo, final String password,
			final String mail) {

		// On récupère notre RequestQueue
		mRequestQueue = app.getVolleyRequestQueue();

		String url = "http://max-site.fr/ws/public/user/index.php?method=setUser";
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
				params.put("firstName", firstName);
				params.put("lastName", lastName);
				params.put("pseudo", pseudo);
				params.put("password", password);
				params.put("mail", mail);
				return params;
			}
		};
		mRequestQueue.add(postRequest);

	}

}
