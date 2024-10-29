package com.example.latfragmenttebakangka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fTiga.newInstance] factory method to
 * create an instance of this fragment.
 */
class fTiga : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var batasAwalEditText: EditText
    private lateinit var batasAkhirEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_f_tiga, container, false)

        batasAwalEditText = view.findViewById(R.id.batasAwal)
        batasAkhirEditText = view.findViewById(R.id.batasAkhir)
        val submitButton = view.findViewById<Button>(R.id.btn_submit)

        submitButton.setOnClickListener {
            val batasAwal = batasAwalEditText.text.toString().toIntOrNull() ?: 1
            val batasAkhir = batasAkhirEditText.text.toString().toIntOrNull() ?: 5

            // Validasi batas awal dan akhir agar batas akhir lebih besar
            if (batasAkhir > batasAwal) {
                // Pindahkan ke fSatu dan kirim batas baru
                val fSatuFragment = fSatu.newInstance(batasAwal, batasAkhir)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, fSatuFragment, "fSatu")
                    .commit()
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fTiga.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(): fTiga {
            return fTiga()
        }
    }
}