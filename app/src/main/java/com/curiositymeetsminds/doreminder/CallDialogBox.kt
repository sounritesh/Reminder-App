package com.curiositymeetsminds.doreminder

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException
import java.util.zip.Inflater

class CallDialogBox: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_call, null))

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}