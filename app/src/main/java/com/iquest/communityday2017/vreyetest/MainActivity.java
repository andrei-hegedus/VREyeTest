package com.iquest.communityday2017.vreyetest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.vr.sdk.widgets.common.VrWidgetView;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;

public class MainActivity extends Activity {

    private VREyeTestView vrEyeTestView;

    public boolean loadImageSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vrEyeTestView = (VREyeTestView) findViewById(R.id.pano_view);
        vrEyeTestView.setEventListener(new ActivityEventListener());
        vrEyeTestView.setEyeTest(VREyeTestView.EyeTest.STEREO_EYE_TEST);
    }


    @Override
    protected void onPause() {
        vrEyeTestView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vrEyeTestView.resumeRendering();
        vrEyeTestView.setTransitionViewEnabled(true);
        vrEyeTestView.setDisplayMode(VrWidgetView.DisplayMode.FULLSCREEN_STEREO);
    }


    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        vrEyeTestView.shutdown();
        super.onDestroy();
    }


    /**
     * Listen to the important events from widget.
     */
    private class ActivityEventListener extends VrPanoramaEventListener {
        /**
         * Called by pano widget on the UI thread when it's done loading the image.
         */
        @Override
        public void onLoadSuccess() {
            loadImageSuccessful = true;
        }

        /**
         * Called by pano widget on the UI thread on any asynchronous error.
         */
        @Override
        public void onLoadError(String errorMessage) {
            loadImageSuccessful = false;
            Toast.makeText(MainActivity.this, "Error loading pano: " + errorMessage, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
