package com.example.papacodes.presentation.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

object DialogFactory {

    @JvmStatic
    fun showDialogWithOnButton(
        context: Context,
        @StringRes titleRes: Int,
        @StringRes messageRes: Int,
        @StringRes buttonTitleRes: Int,
        onClick: (dialogInterface: DialogInterface) -> Unit,
        onCancel: (dialogInterface: DialogInterface) -> Unit
    ) {
        showDialogWithOnButton(
            context,
            context.getString(titleRes),
            context.getString(messageRes),
            context.getString(buttonTitleRes),
            onClick,
            onCancel
        )
    }

    @JvmStatic
    fun showDialogWithOnButton(
        context: Context,
        title: String,
        message: String,
        buttonTitle: String,
        onClick: (dialogInterface: DialogInterface) -> Unit,
        onCancel: (dialogInterface: DialogInterface) -> Unit
    ) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setOnCancelListener {
                onCancel(it)
            }
            setPositiveButton(buttonTitle) { dialogInterface, _ ->
                dialogInterface.cancel()
                onClick(dialogInterface)
            }
        }.show()
    }
}