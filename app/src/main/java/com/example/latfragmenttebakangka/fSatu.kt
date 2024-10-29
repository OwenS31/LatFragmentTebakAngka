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
    private var correctNumber: Int = 0

    private var batasAwal: Int = 1
    private var batasAkhir: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            batasAwal = it.getInt("batasAwal", 1)
            batasAkhir = it.getInt("batasAkhir", 5)
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
            view.findViewById(R.id.button9_text)
        )

        if (arguments != null) {
            updateGenerateNumbers(batasAwal, batasAkhir)
        } else {
            generateRandomNumbers()
        }

        // Set up event listener pada setiap tombol
        numberButtons.forEachIndexed { index, button ->
            button.text = numberList[index].toString() // Tampilkan angka acak
            button.setOnClickListener { checkGuess(numberList[index]) }
        }


        // Update score awal
        updateScore()

        view.findViewById<LinearLayout>(R.id.button_giveup).setOnClickListener {
            endGame()
        }

        return view
    }

    private fun generateRandomNumbers() {
        // Buat daftar angka dari 1 hingga 5, masing-masing muncul 2 kali
        numberList = (1..5).flatMap { listOf(it, it) }.shuffled()

        // Pilih salah satu angka sebagai angka benar
        correctNumber = numberList.random()
    }

    fun updateGenerateNumbers(batasAwal: Int, batasAkhir: Int) {
        // Perbarui angka acak sesuai batas yang diterima
        numberList = (batasAwal..batasAkhir).flatMap { listOf(it, it) }.shuffled()
        correctNumber = numberList.random()

        numberButtons.forEachIndexed { index, button ->
            button.text = numberList[index].toString()
        }
    }

    private fun checkGuess(guess: Int) {
        if (guess == correctNumber) {
            score += 10  // Jika benar, tambah score 10
        } else {
            score -= 5  // Jika salah, kurangi score 5
        }
        updateScore()
    }

    private fun updateScore() {
        scoreTextView.text = score.toString()

        // Periksa kondisi untuk memanggil endGame jika score mencapai batas
        if (score >= 100 || score < 0) {
            endGame()
        }
    }

    private fun endGame() {
        // Buat instance dari fDua
        val fDuaFragment = fDua()
        // Set nilai score akhir ke fDua
        fDuaFragment.setFinalScore(score)

        // Ganti fragment ke fDua
        parentFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fDuaFragment)
            .addToBackStack(null)
            .commit()
    }

    fun updateRandomNumberRange(batasAwal: Int, batasAkhir: Int) {
        numberList = (batasAwal..batasAkhir).flatMap { listOf(it, it) }.shuffled()
        correctNumber = numberList.random()

        // Update tombol-tombol dengan angka baru
        numberButtons.forEachIndexed { index, button ->
            button.text = numberList[index].toString()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fSatu.
         */
        // TODO: Rename and change types and number of parameters
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