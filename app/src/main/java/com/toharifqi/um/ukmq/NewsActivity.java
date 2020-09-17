package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        WebView browser = (WebView) findViewById(R.id.webview);

        browser.loadUrl("https://search.kompas.com/search/?q=ukm&submit=Kirim+Kueri");
    }

    public void toHome(View view){
        onBackPressed();
    }
}