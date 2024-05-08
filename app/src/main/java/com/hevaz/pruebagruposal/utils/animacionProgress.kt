package com.hevaz.pruebagruposal.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.airbnb.lottie.LottieAnimationView
import com.hevaz.pruebagruposal.R

class animacionProgress {

    companion object {
         private var epicDialog2: Dialog? = null

         fun mostrarCarga(context: Context) {
            epicDialog2 = Dialog(context)
            epicDialog2!!.setContentView(R.layout.progress_layout)

            epicDialog2!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            epicDialog2!!.setCanceledOnTouchOutside(false)
            epicDialog2!!.show()
        }

        fun esconderCarga( ) {

            epicDialog2!!.dismiss()
        }
    }

}