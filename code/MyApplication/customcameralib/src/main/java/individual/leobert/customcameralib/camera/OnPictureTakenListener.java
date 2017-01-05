package individual.leobert.customcameralib.camera;

import android.util.Log;

/**
 * <p><b>Package</b> individual.leobert.customcameralib.camera
 * <p><b>Project</b> MyApplication
 * <p><b>Classname</b> OnPictureTakenListener
 * <p><b>Description</b>: 进行图像保存的回调
 * <p>Created by leobert on 2017/1/5.
 */

public interface OnPictureTakenListener {
    void onPictureSaved(String filePath);

    void onSaveError();

    OnPictureTakenListener defaultListener = new OnPictureTakenListener() {

        private static final String TAG = "PictureTaken-default";

        @Override
        public void onPictureSaved(String filePath) {
            Log.d(TAG,"onPictureSaved"+filePath);
        }

        @Override
        public void onSaveError() {
            Log.e(TAG,"onPictureSave error");
        }
    };
}
