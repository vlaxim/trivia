/* class RulesActivity
 * author : Maxime FLASQUIN
 * Date : 18/02/2014
 * Description : WebView des règles du jeu
 */

package com.vlaxim.trivia;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RulesActivity extends Activity {

	
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);
		
		webView = (WebView) findViewById(R.id.webViewRules);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://max-site.fr/trivia/regle.html");
		webView.setWebViewClient(new WebViewClient());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rules, menu);
		return true;
	}
	
	// Tuer l'activité lors de l'appuie sur le bouton de retour
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		Intent intentHome = new Intent(RulesActivity.this,
				HomeActivity.class);
		startActivity(intentHome);
	}

}
