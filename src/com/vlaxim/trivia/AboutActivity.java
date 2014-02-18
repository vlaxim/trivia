/* class Game Activity
 * author : Maxime FLASQUIN
 * Date : 18/02/2014
 * Description : WebView a propos
 */

package com.vlaxim.trivia;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		webView = (WebView) findViewById(R.id.webViewAbout);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://max-site.fr/trivia/apropos.html");
		webView.setWebViewClient(new WebViewClient());
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		Intent intentHome = new Intent(AboutActivity.this, HomeActivity.class);
		startActivity(intentHome);
	}

}
