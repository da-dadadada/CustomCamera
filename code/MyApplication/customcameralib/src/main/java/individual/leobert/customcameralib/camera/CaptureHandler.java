package individual.leobert.customcameralib.camera;

import android.os.Handler;
import android.os.Message;

import individual.leobert.customcameralib.R;


public final class CaptureHandler extends Handler {

    public CaptureHandler() {
        CameraManager.get().startPreview();
        restartPreview();
    }

    @Override
    public void handleMessage(Message message) {

//        if (message.what == R.id.auto_focus) {
//            CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
//        }

    }

    public void quitSynchronously() {
        CameraManager.get().stopPreview();
        removeMessages(R.id.auto_focus);
    }

    private void restartPreview() {
        CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
    }
}
