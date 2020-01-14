package com.yazan98.wintrop.client.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yazan98.wintrop.client.R
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment @Inject constructor() : BottomSheetDialogFragment() {

    private var listener: SettingsClick? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        IrbidButton?.apply {
            this.setOnClickListener {
                listener?.onOptionChoose(1)
                dismiss()
            }
        }

        AqabaButton?.apply {
            this.setOnClickListener {
                listener?.onOptionChoose(2)
                dismiss()
            }
        }
    }

    fun attachListener(listener: SettingsClick) {
        this.listener = listener
    }

    fun destroyListener() {
        this.listener = null
    }

    interface SettingsClick {
        fun onOptionChoose(index: Int)
    }

}
