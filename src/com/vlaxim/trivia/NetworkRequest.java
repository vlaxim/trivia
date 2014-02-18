/* class NetworkRequest
 * author : Maxime FLASQUIN
 * Date : 18/02/2014
 * Description : Gère les requêtes réseau avec la librairie volley
 */

package com.vlaxim.trivia;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkRequest extends Application {

	private RequestQueue mVolleyRequestQueue;

	@Override
	public void onCreate() {
		super.onCreate();

		// On initialise notre Thread-Pool
		mVolleyRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mVolleyRequestQueue.start();
	}

	/*
	 * Retourne le gestionnaire de requêtes
	 */
	public RequestQueue getVolleyRequestQueue() {
		return mVolleyRequestQueue;
	}

	@Override
	public void onTerminate() {
		mVolleyRequestQueue.stop();
		super.onTerminate();
	}
}
