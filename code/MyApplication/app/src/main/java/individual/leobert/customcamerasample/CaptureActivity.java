package individual.leobert.customcamerasample;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import individual.leobert.customcameralib.camera.CameraBasicFragment;
import individual.leobert.customcameralib.camera2.Camera2BasicFragment;

public class CaptureActivity extends AppCompatActivity {
    private static final String TAG = CaptureActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, Camera2BasicFragment.newInstance())
                        .commit();
            } else {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, CameraBasicFragment.newInstance())
                        .commit();
            }
        }
    }

}

