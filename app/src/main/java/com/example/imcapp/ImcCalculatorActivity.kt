package com.example.imcapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.ButtonBarLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import org.w3c.dom.Text
import java.text.DecimalFormat

private lateinit var viewMale: CardView
private lateinit var viewFemale: CardView
private lateinit var tviewHeight: TextView
private lateinit var rSliderHeight: RangeSlider
private lateinit var plusButtonWeight: FloatingActionButton
private lateinit var lessButtonWeight: FloatingActionButton
private lateinit var plusButtonAge: FloatingActionButton
private lateinit var lessButtonAge: FloatingActionButton
private lateinit var viewWeight: TextView
private lateinit var viewAge: TextView
private lateinit var calcularButton: AppCompatButton
private var isMaleSelected:Boolean = true
private var weight:Int = 40
private var age:Int = 12

class ImcCalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tviewHeight = findViewById(R.id.textviewHeight)
        rSliderHeight = findViewById(R.id.rangesliderHeight)
        plusButtonWeight = findViewById(R.id.btnMoreWeight)
        lessButtonWeight = findViewById(R.id.btnLessWeight)
        plusButtonAge = findViewById(R.id.btnMoreAge)
        lessButtonAge = findViewById(R.id.btnLessAge)
        viewWeight = findViewById(R.id.textviewWeight)
        viewAge = findViewById(R.id.textviewAge)
        calcularButton = findViewById(R.id.buttonCalcular)
    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            isMaleSelected = true
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            isMaleSelected = false
            setGenderColor()
        }
        rSliderHeight.addOnChangeListener{ _, value, _ ->
            tviewHeight.text = DecimalFormat("#.##").format(value)
        }

        plusButtonWeight.setOnClickListener {
            weight = weight + 1
            setWeight()
        }

        lessButtonWeight.setOnClickListener {
            weight = weight - 1
            setWeight()
        }

        plusButtonAge.setOnClickListener {
            age = age + 1
            setAge()
        }

        lessButtonAge.setOnClickListener {
            age = age - 1
            setAge()
        }

        calcularButton.setOnClickListener {
            calculateIMC()
        }
    }

    private fun initUI() {
        setWeight()
        setAge()
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColorMale(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColorFemale(!isMaleSelected))
    }
    //Get para Male
    private fun getBackgroundColorMale(isComponentSelected: Boolean):Int {
        val colorReference = if(isComponentSelected) {
            R.color.bg_comp_selected
        } else {
            R.color.bg_comp_men
        }
        return ContextCompat.getColor(this, colorReference)
    }
    //Get para Female
    private fun getBackgroundColorFemale(isComponentSelected: Boolean):Int {
        val colorReference = if(isComponentSelected) {
            R.color.bg_comp_selected
        } else {
            R.color.bg_comp_women
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun setWeight() {
        viewWeight.text = weight.toString()
    }

    private fun setAge() {
        viewAge.text = age.toString()
    }

    private fun calculateIMC() {
        var peso: Double = viewWeight.text.toString().toDouble()
        var altura: Double = tviewHeight.text.toString().toDouble()

        var tituloPeso: String = ""
        var textoPeso: String = ""

        altura = (altura / 100)
        altura = altura * altura
        val resultado: Double = peso / altura

        when (resultado) {
            in 0.0..18.5 -> {
                tituloPeso = getString(R.string.bajopeso)
                textoPeso = getString(R.string.textoBajoPeso)
            }

            in 18.6..24.9 -> {
                tituloPeso = getString(R.string.normal)
                textoPeso = getString(R.string.textoNormal)
            }

            in 25.0..29.9 -> {
                tituloPeso = getString(R.string.superior)
                textoPeso = getString(R.string.textoSuperior)
            }

            in 30.0..Double.MAX_VALUE -> {
                tituloPeso = getString(R.string.obesidad)
                textoPeso = getString(R.string.textoObesidad)
            }

            else -> {
                tituloPeso = getString(R.string.error)
                textoPeso = getString(R.string.textoError)
            }
        }

        val decimalFormat = DecimalFormat("#.##")
        val res = decimalFormat.format(resultado)

        val intentImcResult = Intent(this, ImcResultActivity::class.java)
        intentImcResult.putExtra("Título", tituloPeso)
        intentImcResult.putExtra("Descripción", textoPeso)
        intentImcResult.putExtra("Resultado", res)
        startActivity(intentImcResult)
    }
}