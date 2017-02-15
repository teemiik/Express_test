package com.example.tanat.express_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by tanat on 13.02.2017.
 */

public class DialogScreen extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.b_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // ((TouchScreenTest) getActivity()).okClicked();

                    }
                })
                .setNegativeButton(R.string.b_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((ScreenTestActivity) getActivity()).cancelClicked();
                    }
                })
                .setNeutralButton(R.string.b_nuetral, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((ScreenTestActivity) getActivity()).neutralClicked();
                    }
                })
                .setMessage(R.string.vibor);
        return adb.create();
    }
}

