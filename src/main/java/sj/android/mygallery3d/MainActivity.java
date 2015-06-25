package sj.android.mygallery3d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Administrator on 2015/6/17.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(new RenderView(this));
        setContentView(glSurfaceView);
    }

    PowerManager pManager;
    PowerManager.WakeLock mWakeLock;

    @Override
    protected void onResume() {
        super.onResume();
        pManager = ((PowerManager) getSystemService(POWER_SERVICE));
        mWakeLock = pManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.ON_AFTER_RELEASE, "");
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (null != mWakeLock) {
            mWakeLock.release();
        }
    }
}
