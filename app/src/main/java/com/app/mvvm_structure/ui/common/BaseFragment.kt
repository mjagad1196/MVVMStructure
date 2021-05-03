package com.app.mvvm_structure.ui.common

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<B: ViewDataBinding, V: ViewModel>: Fragment(), BaseView {

    protected lateinit var dataBinding: B
    private var dialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, onInflateLayout(), container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun showProgress(resourceId: Int) {

            if (dialog == null || dialog!!.isShowing) {
                dialog = Dialog(requireContext())
                dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                try {
                    val dividerId: Int = dialog!!.context.resources.getIdentifier("android:id/titleDivider", null, null)
                    val divider: View = dialog!!.findViewById(dividerId)
                    divider.setBackgroundColor(requireContext().resources.getColor(R.color.transparent))
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
                dialog?.setContentView(resourceId)
                /*val tvMessage: TextView = dialog!!.findViewById(R.id.txtMessage)
                if (!TextUtils.isEmpty(message)) {
                    tvMessage.setText(message)
                }*/
                dialog?.setCancelable(false)
                dialog?.show()

            }

    }

    override fun hideProgress() {
        try {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (throwable: Throwable) {
            throwable.stackTrace
        } finally {
            dialog = null
        }
    }

}