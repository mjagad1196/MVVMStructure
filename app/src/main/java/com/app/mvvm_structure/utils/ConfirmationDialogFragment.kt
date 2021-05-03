package com.app.mvvm_structure.utils

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfirmationDialogFragment: DialogFragment() {

    companion object {
        private const val BUNDLE_DIALOG_MESSAGE = "BUNDLE_DIALOG_MESSAGE"
        private const val BUNDLE_DIALOG_TITLE = "BUNDLE_DIALOG_TITLE"
        private const val BUNDLE_DIALOG_BTN_POSITIVE = "BUNDLE_DIALOG_BTN_POSITIVE"
        private const val BUNDLE_DIALOG_BTN_NEGATIVE = "BUNDLE_DIALOG_BTN_NEGATIVE"

        fun getInstance(
            dialogMessage: String,
            dialogTitle: String,
            positiveButtonText: String,
            negativeButtonText: String,
            positiveClickListener: DialogInterface.OnClickListener,
            negativeClickListener: DialogInterface.OnClickListener
        ): ConfirmationDialogFragment {
            val dialogFragment = ConfirmationDialogFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_DIALOG_MESSAGE, dialogMessage)
            bundle.putString(BUNDLE_DIALOG_TITLE, dialogTitle)
            bundle.putString(BUNDLE_DIALOG_BTN_POSITIVE, positiveButtonText)
            bundle.putString(BUNDLE_DIALOG_BTN_NEGATIVE, negativeButtonText)
            dialogFragment.arguments = bundle
            dialogFragment.positiveClickListener = positiveClickListener
            dialogFragment.negativeClickListener = negativeClickListener
            return dialogFragment
        }

    }

    private var dialogMessage = ""
    private var dialogTitle = ""
    private var positiveBtnText = "Yes"
    private var negativeBtnText = "No"
    private var positiveClickListener: DialogInterface.OnClickListener? = null
    private var negativeClickListener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundleArguments = arguments
        if(bundleArguments != null && bundleArguments.containsKey(BUNDLE_DIALOG_MESSAGE)){
            dialogMessage = bundleArguments.getString(BUNDLE_DIALOG_MESSAGE, dialogMessage)
            dialogTitle = bundleArguments.getString(BUNDLE_DIALOG_TITLE, dialogTitle)
            positiveBtnText = bundleArguments.getString(BUNDLE_DIALOG_BTN_POSITIVE, positiveBtnText)
            negativeBtnText = bundleArguments.getString(BUNDLE_DIALOG_BTN_NEGATIVE, negativeBtnText)
        }

        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setMessage(dialogMessage)
            .setTitle(dialogTitle)
            .setCancelable(false)
            .setPositiveButton(positiveBtnText, positiveClickListener)
            .setNegativeButton(negativeBtnText, negativeClickListener)

        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.setCancelable(false)
        }

        return dialog
    }

}