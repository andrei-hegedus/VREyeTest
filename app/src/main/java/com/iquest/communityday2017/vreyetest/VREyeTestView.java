package com.iquest.communityday2017.vreyetest;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author andrei.hegedus
 */

public class VREyeTestView extends VrPanoramaView {

    private static final String TAG = "vr-motion";
    private AssetManager assetManager;
    private EyeTest eyeTest = EyeTest.STEREO_EYE_TEST;

    public enum EyeTest {
        STEREO_EYE_TEST("vr_eye_test_hd.jpg"), RIGHT_EYE_TEST("vr_eye_test_left_eye_off.jpg"), LEFT_EYE_TEST("vr_eye_test_right_eye_off.jpg");

        private String testName;

        EyeTest(String testName) {
            this.testName = testName;
        }
    }

    private EyeTestImageLoaderTask eyeTestImageLoaderTask;

    public VREyeTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VREyeTestView(Context context) {
        super(context);
        init();
    }

    private void init(){
        assetManager = getContext().getAssets();
    }

    public void setEyeTest(EyeTest eyeTest) {
        this.eyeTest = eyeTest;
    }

    @Override
    public void resumeRendering() {
        super.resumeRendering();
        eyeTestImageLoaderTask = new EyeTestImageLoaderTask();
        eyeTestImageLoaderTask.execute(eyeTest.testName);
    }

    @Override
    public void shutdown() {
        super.shutdown();
        // The background task has a 5 second timeout so it can potentially stay alive for 5 seconds
        // after the activity is destroyed unless it is explicitly cancelled.
        if (eyeTestImageLoaderTask != null) {
            eyeTestImageLoaderTask.cancel(true);
        }
    }

    class EyeTestImageLoaderTask extends AsyncTask<String, Void, Boolean> {

        /**
         * Reads the bitmap from disk in the background and waits until it's loaded by pano widget.
         */
        @Override
        protected Boolean doInBackground(String... imageName) {
            final VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
            InputStream istr = null;
            try {
                istr = assetManager.open(imageName[0]);
                panoOptions.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
            } catch (IOException e) {
                Log.e(TAG, "Could not decode default bitmap: " + e);
                return false;
            }

            final Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(istr), 4096, 4096, false);


            post(new Runnable() {
                @Override
                public void run() {
                    loadImageFromBitmap(bitmap, panoOptions);
                }
            });
            try {
                istr.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close input stream: " + e);
            }

            return true;
        }
    }
}
