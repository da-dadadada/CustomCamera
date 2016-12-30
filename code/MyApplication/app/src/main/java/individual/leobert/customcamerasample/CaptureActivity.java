package individual.leobert.customcamerasample;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import individual.leobert.customcameralib.camera2.Camera2BasicFragment;

public class CaptureActivity extends AppCompatActivity  {
    private static final String TAG  = CaptureActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, Camera2BasicFragment.newInstance())
                        .commit();
            } else {
                Log.e(TAG,"android M 以下版本未实现");
                Toast.makeText(this,"android M 以下版本未实现",Toast.LENGTH_SHORT).show();
            }
        }
    }

}

