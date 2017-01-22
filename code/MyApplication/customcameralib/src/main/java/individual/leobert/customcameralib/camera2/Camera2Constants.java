package individual.leobert.customcameralib.camera2;

/**
 * <p><b>Package</b> individual.leobert.customcameralib.camera2
 * <p><b>Project</b> MyApplication
 * <p><b>Classname</b> Camera2Constants
 * <p><b>Description</b>: TODO
 * <p>Created by leobert on 2017/1/22.
 */

public interface Camera2Constants {
    /**
     * Camera state: Showing camera preview.
     */
    int STATE_PREVIEW = 0;

    /**
     * Camera state: Waiting for the focus to be locked.
     */
    int STATE_WAITING_LOCK = 1;

    /**
     * Camera state: Waiting for the exposure to be precapture state.
     */
    int STATE_WAITING_PRECAPTURE = 2;

    /**
     * Camera state: Waiting for the exposure state to be something other than precapture.
     */
    int STATE_WAITING_NON_PRECAPTURE = 3;

    /**
     * Camera state: Picture was taken.
     */
    int STATE_PICTURE_TAKEN = 4;

    /**
     * Max preview width that is guaranteed by Camera2 API
     */
    int MAX_PREVIEW_WIDTH = 1920;

    /**
     * Max preview height that is guaranteed by Camera2 API
     */
    int MAX_PREVIEW_HEIGHT = 1080;
}
