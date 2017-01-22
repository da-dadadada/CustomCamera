package individual.leobert.customcameralib.camera2;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentCompat;

import individual.leobert.customcameralib.R;

/**
 * <p><b>Package</b> individual.leobert.customcameralib.camera2
 * <p><b>Project</b> MyApplication
 * <p><b>Classname</b> Dialogs
 * <p><b>Description</b>: TODO
 * <p>Created by leobert on 2017/1/22.
 */

public interface Dialogs {

    /**
     * Shows an error message dialog.
     */
    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.finish();
                        }
                    })
                    .create();
        }

    }

    /**
     * Shows OK/Cancel confirmation dialog about camera permission.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    class ConfirmationDialog extends DialogFragment {
        private static final String KEY_REQUEST_CODE = "REQUEST_CODE";

        public static ConfirmationDialog newInstance(int requestCode) {
            ConfirmationDialog dialog = new ConfirmationDialog();
            Bundle args = new Bundle();
            args.putInt(KEY_REQUEST_CODE, requestCode);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentCompat.requestPermissions(parent,
                                    new String[]{Manifest.permission.CAMERA},
                                    getArguments().getInt(KEY_REQUEST_CODE));
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Activity activity = parent.getActivity();
                                    if (activity != null) {
                                        activity.finish();
                                    }
                                }
                            })
                    .create();
        }
    }
}
