package individual.leobert.customcameralib.camera;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import individual.leobert.customcameralib.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraBasicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraBasicFragment extends Fragment implements SurfaceHolder.Callback, OnPictureTakenListener {

    protected boolean hasSurface;

    protected CaptureHandler handler;

    public CameraBasicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static CameraBasicFragment newInstance() {
        return new CameraBasicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CameraManager.init(getActivity());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_basic, container, false);
    }

    private View contentView;

    private static final String TAG = CameraBasicFragment.class.getSimpleName();


    @Override
    public void onPictureSaved(String filePath) {
        Log.d(TAG, "onPictureSaved" + filePath);
    }

    @Override
    public void onSaveError() {
        Log.e(TAG, "onPictureSave error");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        contentView = view;
        view.findViewById(R.id.picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraManager.get().doTakePicture(CameraBasicFragment.this);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onPause();
        } else {
            onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupSurfaceViewAndCamera(contentView);
    }

    private void setupSurfaceViewAndCamera(View view) {
        SurfaceView surfaceView = (SurfaceView) view.findViewById(R.id.capture_preview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
        }
    }


    protected void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);

        } catch (IOException | RuntimeException ioe) {
            ioe.printStackTrace();
            return;
        }
        if (handler == null) {
            handler = new CaptureHandler();
        }
    }

    private boolean isLightClose;

    /**
     * turn on/off the light
     */
    protected void toggleLight() {
        if (isLightClose) {
            isLightClose = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            isLightClose = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }
}
