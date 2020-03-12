package mcq.kasun.acer.firebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;


public class DownloadWebview extends Activity {
    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        webview.setWebChromeClient(new WebChromeClient());
        WebViewClient client = new ChildBrowserClient();
        webview.setWebViewClient(client);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setInitialScale(1);
        webview.getSettings().setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setBuiltInZoomControls(true);
        settings.setPluginState(PluginState.ON);
        settings.setDomStorageEnabled(true);
        webview.loadUrl("https://comfortyou986137434.wordpress.com/");
        //webview.setId(5);
        webview.setInitialScale(0);
        webview.requestFocus();
        webview.requestFocusFromTouch();
        setContentView(webview);
    }
    /**
     * The webview client receives notifications about appView
     */
    public class ChildBrowserClient extends WebViewClient {
        @SuppressLint("InlinedApi")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean value = true;
            String extension = MimeTypeMap.getFileExtensionFromUrl(url);
            if (extension != null) {
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String mimeType = mime.getMimeTypeFromExtension(extension);
                if (mimeType != null) {
                    if (mimeType.toLowerCase().contains("video")
                            || extension.toLowerCase().contains("mov")
                            || extension.toLowerCase().contains("mp3")) {
                        DownloadManager mdDownloadManager = (DownloadManager) DownloadWebview.this
                                .getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(
                                Uri.parse(url));
                        File destinationFile = new File(
                                Environment.getExternalStorageDirectory(),
                                getFileName(url));
                        request.setDescription("Downloading via Your app name..");
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationUri(Uri.fromFile(destinationFile));
                        mdDownloadManager.enqueue(request);
                        value = false;
                    }
                }
                if (value) {
                    view.loadUrl(url);
                }
            }
            return value;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
        /**
         * Notify the host application that a page has started loading.
         *
         * @param view
         *      The webview initiating the callback.
         * @param url
         *      The url of the page.
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }
    /**
     * File name from URL
     *
     * @param url
     * @return
     */
    public String getFileName(String url) {
        String filenameWithoutExtension = "";
        filenameWithoutExtension = String.valueOf(System.currentTimeMillis()
                + ".mp4");
        return filenameWithoutExtension;
    }
}