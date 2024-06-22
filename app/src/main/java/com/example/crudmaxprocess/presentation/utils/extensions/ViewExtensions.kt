package com.example.crudmaxprocess.presentation.utils.extensions

import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.crudmaxprocess.R
import com.example.crudmaxprocess.databinding.BottomSheetBinding
import com.example.crudmaxprocess.domain.model.Client
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.hideKeyboard() {
    val view = activity?.currentFocus
    if (view != null) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Fragment.showClientDetailsBottomSheet(
    client: Client? = null, onClick: () -> Unit = {}
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
    val bottomSheetBinding: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    bottomSheetBinding.textName.text = client?.name
    bottomSheetBinding.textCpf.text = client?.cpf
    bottomSheetBinding.date.text = client?.birthDate
    bottomSheetBinding.uf.text = client?.uf
    bottomSheetBinding.phone.text = client?.phone

    bottomSheetBinding.btnEdit.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.show()
}