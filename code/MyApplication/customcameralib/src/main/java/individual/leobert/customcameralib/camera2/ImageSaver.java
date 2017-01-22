package individual.leobert.customcameralib.camera2;

/**
 * <p><b>Package</b> individual.leobert.customcameralib.camera2
 * <p><b>Project</b> MyApplication
 * <p><b>Classname</b> ImageSaver
 * <p><b>Description</b>: TODO
 * <p>Created by leobert on 2017/1/22.
 */

import android.annotation.TargetApi;
import android.media.Image;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Saves a JPEG {@link Image} into the specified {@link File}.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class ImageSaver implements Runnable {

    /**
     * The JPEG image
     */
    private final Image mImage;
    /**
     * The file we save the image into.
     */
    private final File mFile;

    ImageSaver(Image image, File file) {
        mImage = image;
        mFile = file;
    }

    @Override
    public void run() {
        ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(mFile);
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mImage.close();
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
