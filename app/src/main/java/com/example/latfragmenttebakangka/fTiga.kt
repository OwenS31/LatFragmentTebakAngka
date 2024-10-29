package com.example.latfragmenttebakangka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class fTiga : Fragment() {

    private lateinit var batasAwalEditText: EditText
    private lateinit var batasAkhirEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_f_tiga, container, false)

        batasAwalEditText = view.findViewById(R.id.batasAwal)
        batasAkhirEditText = view.findViewById(R.id.batasAkhir)
        val submitButton = view.findViewById<Button>(R.id.btn_submit)

        submitButton.setOnClickListener {
            val batasAwal = batasAwalEditText.text.toString().toIntOrNull()
            val batasAkhir = batasAkhirEditText.text.toString().toIntOrNull()

            if (batasAwal != null && batasAkhir != null) {
                val rangeSize = batasAkhir - batasAwal + 1

                if (rangeSize == 6) {
                    // Pindah ke fSatu dengan membawa batas awal dan akhir yang baru
                    val fSatuFragment = fSatu.newInstance(batasAwal, batasAkhir)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, fSatuFragment, "fSatu")
                        .addToBackStack(null)
                        .commit()
                } else {
                    // Tampilkan pesan error jika range tidak tepat 6
                    Toast.makeText(requireContext(), "Range harus tepat 6 angka", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Tampilkan pesan error jika input tidak valid
                Toast.makeText(requireContext(), "Masukkan batas awal dan akhir yang valid", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): fTiga {
            return fTiga()
        }
    }
}
