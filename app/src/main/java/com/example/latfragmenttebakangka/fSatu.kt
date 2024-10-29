package com.example.latfragmenttebakangka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fSatu.newInstance] factory method to
 * create an instance of this fragment.
 */
class fSatu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var score = 50
    private lateinit var scoreTextView: TextView
    private lateinit var numberButtons: List<TextView>
    private lateinit var numberList: List<Int>

    private var firstButton: TextView? = null
    private var secondButton: TextView? = null

    private var batasAwal: Int = 1
    private var batasAkhir: Int = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            batasAwal = it.getInt("batasAwal", 1)
            batasAkhir = it.getInt("batasAkhir", 6)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_f_satu, container, false)

        // Referensi ke TextView untuk score
        scoreTextView = view.findViewById(R.id.textViewScore)

        // Inisialisasi tombol angka
        numberButtons = listOf(
            view.findViewById(R.id.button1_text),
            view.findViewById(R.id.button2_text),
            view.findViewById(R.id.button3_text),
            view.findViewById(R.id.button4_text),
            view.findViewById(R.id.button5_text),
            view.findViewById(R.id.button6_text),
            view.findViewById(R.id.button7_text),
            view.findViewById(R.id.button8_text),
            view.findViewById(R.id.button9_text),
            view.findViewById(R.id.button10_text),
            view.findViewById(R.id.button11_text),
            view.findViewById(R.id.button12_text)
        )

        updateGenerateNumbers(batasAwal, batasAkhir)

        numberButtons.forEachIndexed { index, button ->
            button.text = "?" // Inisialisasi sebagai tanda tanya
            button.setOnClickListener { onButtonClicked(button, numberList[index]) }
        }

        updateScore()

        view.findViewById<LinearLayout>(R.id.button_giveup).setOnClickListener {
            endGame()
        }

        return view
    }

    private fun updateGenerateNumbers(batasAwal: Int, batasAkhir: Int) {
        numberList = (batasAwal..batasAkhir).flatMap { listOf(it, it) }.shuffled()
    }

    private fun onButtonClicked(button: TextView, number: Int) {
        if (firstButton == null) {
            // Simpan tombol pertama dan menampilkan angkanya
            firstButton = button
            firstButton?.text = number.toString()
        } else if (secondButton == null && button != firstButton) {
            // Simpan tombol kedua dan menampilkan angkanya
            secondButton = button
            secondButton?.text = number.toString()

            // Sebelum pengecekan terdapat delay
            button.postDelayed({
                if (firstButton?.text == secondButton?.text) {
                    // Jika cocok, tambahkan skor dan sembunyikan tombol
                    score += 10
                    firstButton?.visibility = View.INVISIBLE
                    secondButton?.visibility = View.INVISIBLE
                } else {
                    // Jika tidak cocok, kurangi skor dan reset tombol
                    score -= 5
                    resetButtons()
                }
                updateScore()

                // Reset firstButton dan secondButton setelah pengecekan
                firstButton = null
                secondButton = null

                // Panggil endGame() jika semua tombol sudah disembunyikan
                if (numberButtons.all { it.visibility == View.INVISIBLE }) {
                    endGame()
                }
            }, 1000) // Delay 1 detik (1000 ms)
        }
    }



    private fun resetButtons() {
        firstButton?.text = "?"
        secondButton?.text = "?"
    }

    private fun updateScore() {
        scoreTextView.text = score.toString()
        if (score < 0) {
            endGame()
        }
    }

    private fun endGame() {
        val fDuaFragment = fDua()
        fDuaFragment.setFinalScore(score)
        parentFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fDuaFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(batasAwal: Int, batasAkhir: Int): fSatu {
            val fragment = fSatu()
            fragment.arguments = Bundle().apply {
                putInt("batasAwal", batasAwal)
                putInt("batasAkhir", batasAkhir)
            }
            return fragment
        }
    }
}