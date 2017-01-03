package individual.leobert.customcameralib.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;


/**
 * CameraManager
 *
 * @author leobert.lan
 * @version 1.0
 */
public final class CameraManager {
    private static CameraManager cameraManager;

    private static final String TAG = CameraManager.class.getSimpleName();

    static final int SDK_INT;

    static {
        int sdkInt;
        try {
            sdkInt = android.os.Build.VERSION.SDK_INT;
        } catch (NumberFormatException nfe) {
            sdkInt = 10000;
        }
        SDK_INT = sdkInt;
    }

    private final CameraConfigurationManager configManager;
    private Camera camera;
    private boolean initialized;
    private boolean previewing;
    private final boolean useOneShotPreviewCallback;
    private final PreviewCallback previewCallback;
    private final AutoFocusCallback autoFocusCallback;
    private Parameters parameter;

    public static void init(Context context) {
        if (cameraManager == null) {
            cameraManager = new CameraManager(context);
        }
    }

    public static CameraManager get() {
        return cameraManager;
    }

    private CameraManager(Context context) {
        this.configManager = new CameraConfigurationManager(context);

        useOneShotPreviewCallback = SDK_INT > 3;
        previewCallback = new PreviewCallback(configManager, useOneShotPreviewCallback);
        autoFocusCallback = new AutoFocusCallback();
    }

    public void openDriver(SurfaceHolder holder) throws IOException {
        if (camera == null) {
            camera = Camera.open();
            if (camera == null) {
                throw new IOException();
            }
            camera.setPreviewDisplay(holder);

            if (!initialized) {
                initialized = true;
                // TODO: 2017/1/3 密度适配
                Rect rect = holder.getSurfaceFrame();
                Point point = new Point(rect.right-rect.left,rect.bottom-rect.top);


                configManager.initFromCameraParameters(camera,point);
            }
            configManager.setDesiredCameraParameters(camera);
            FlashlightManager.enableFlashlight();
        }
    }

    public Point getCameraResolution() {
        return configManager.getCameraResolution();
    }

    public void closeDriver() {
        if (camera != null) {
            FlashlightManager.disableFlashlight();
            camera.release();
            camera = null;
        }
    }

    public void startPreview() {
        if (camera != null && !previewing) {
            camera.startPreview();
            previewing = true;
        }
    }

    public void stopPreview() {
        if (camera != null && previewing) {
            if (!useOneShotPreviewCallback) {
                camera.setPreviewCallback(null);
            }
            camera.stopPreview();
            previewCallback.setHandler(null, 0);
            autoFocusCallback.setHandler(null, 0);
            previewing = false;
        }
    }

    public void requestPreviewFrame(Handler handler, int message) {
        if (camera != null && previewing) {
            previewCallback.setHandler(handler, message);
            if (useOneShotPreviewCallback) {
                camera.setOneShotPreviewCallback(previewCallback);
            } else {
                camera.setPreviewCallback(previewCallback);
            }
        }
    }

    public void requestAutoFocus(Handler handler, int message) {
        if (camera != null && previewing) {
            autoFocusCallback.setHandler(handler, message);
            camera.autoFocus(autoFocusCallback);
        }
    }

    public void openLight() {
        if (camera != null) {
            parameter = camera.getParameters();
            parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameter);
        }
    }

    public void offLight() {
        if (camera != null) {
            parameter = camera.getParameters();
            parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameter);
        }
    }

    public void doTakePicture(){
        if(camera != null){
            camera.takePicture(mShutterCallback, null, mJpegPictureCallback);
        }
    }

    /*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            Log.i(TAG, "myShutterCallback:onShutter...");
        }
    };

//    Camera.PictureCallback mRawCallback = new Camera.PictureCallback() {
//        // 拍摄的未压缩原数据的回调,可以为null
//
//        public void onPictureTaken(byte[] data, Camera camera) {
//            Log.i(TAG, "myRawCallback:onPictureTaken...");
//
//        }
//    };

    Camera.PictureCallback mJpegPictureCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(TAG, "myJpegCallback:onPictureTaken...");
        }
    };

}
