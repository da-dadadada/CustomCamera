package individual.leobert.customcameralib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import individual.leobert.customcameralib.camera.OnPictureTakenListener;

public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * 保存Bitmap到sdcard
     */
    public static void saveBitmap(Context context, Bitmap b,@NonNull OnPictureTakenListener onPictureTakenListener) {

         File ext = context.getExternalFilesDir(null);
        if (ext == null) {
            onPictureTakenListener.onSaveError();
            return;
        }
        String path = ext.getAbsolutePath();

        long dataTake = System.currentTimeMillis();
        String jpegName = path + "/" + dataTake + ".jpg";
        Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i(TAG, "saveBitmap成功");
            onPictureTakenListener.onPictureSaved(jpegName);
        } catch (IOException e) {
            Log.i(TAG, "saveBitmap:失败");
            e.printStackTrace();
            onPictureTakenListener.onSaveError();
        }
    }


}
