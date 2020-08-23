package com.curiositymeetsminds.doreminder

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

class CallDialogBox : DialogFragment() {
    private val TAG = "CallDialogBox"

    lateinit var listener: ClickListener

    interface ClickListener {
        fun onCallPositiveClickListener (dialog: DialogFragment)
        fun onCallNegativeClickListener (dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater;

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView(inflater.inflate(R.layout.dialog_call, null))
            .setPositiveButton("OKAY", DialogInterface.OnClickListener { dialog, which ->
                listener.onCallPositiveClickListener(this)
            })
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, which ->
                listener.onCallNegativeClickListener(this)
            })
        val dialogBox = builder.create()
            ?: throw IllegalStateException("Activity cannot be null")

        dialogBox.setOnShowListener {
            val btnOkay = dialogBox.getButton(DialogInterface.BUTTON_POSITIVE)
            val btnCancel = dialogBox.getButton(DialogInterface.BUTTON_NEGATIVE)
            btnOkay.setBackgroundColor(Color.WHITE)
            btnCancel.setBackgroundColor(Color.WHITE)
            btnOkay.setTextColor(ContextCompat.getColor(context!!, R.color.colorSecondary))
            btnCancel.setTextColor(ContextCompat.getColor(context!!, R.color.colorSecondary))
        }
        return dialogBox
    }

    override fun onAttach(activity: Activity) {
        try {
            listener = context as ClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException("${context.toString()} must implement ClickListener")
        }
        super.onAttach(activity)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Log.d(TAG, "dialog cancelled")
    }
}