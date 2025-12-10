package com.barakat.quickroots

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.barakat.quickroots.databinding.ActivityMainBinding
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnCalculate.setOnClickListener {

            try {
                // ✅ قراءة معاملات المعادلة
                val a = binding.etA.text.toString().toDouble()
                val b = binding.etB.text.toString().toDouble()
                val c = binding.etC.text.toString().toDouble()
                val d = binding.etD.text.toString().toDouble()
                val e = binding.etE.text.toString().toDouble()
                val fConst = binding.etF.text.toString().toDouble()


                var xL = binding.etXLower.text.toString().toDouble()
                var xU = binding.etXUpper.text.toString().toDouble()
                val Es = binding.etStoppingError.text.toString().toDouble()

                // ✅ تعريف الدالة باستخدام الـ coefficients
                fun f(x: Double): Double {
                    return a * Math.pow(x, 5.0) +
                            b * Math.pow(x, 4.0) +
                            c * Math.pow(x, 3.0) +
                            d * Math.pow(x, 2.0) +
                            e * x +
                            fConst
                }


                if (f(xL) * f(xU) > 0) {
                    binding.tvResult.text = "❌ Invalid range! No root in this interval."
                    return@setOnClickListener
                }

                var xR_new: Double
                var xR_old = 0.0
                var Ea = Double.MAX_VALUE
                var firstIter = true


                do {
                    xR_new = (xL + xU) / 2.0

                    if (!firstIter)
                        Ea = Math.abs((xR_new - xR_old) / xR_new)
                    else firstIter = false

                    if (f(xL) * f(xR_new) < 0)
                        xU = xR_new
                    else
                        xL = xR_new

                    xR_old = xR_new

                } while (firstIter || Ea > Es)


                val rootFormatted = String.format("%.6f", xR_new)
                binding.tvResult.text = "The Root = $rootFormatted"

            } catch (e: Exception) {
                binding.tvResult.text = "⚠️ Please enter all values correctly"
            }
        }


    }

}