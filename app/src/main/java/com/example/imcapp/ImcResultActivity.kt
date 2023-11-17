package com.example.imcapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import android.content.Intent

class ImcResultActivity : AppCompatActivity() {

    private lateinit var tituloPeso: TextView
    private lateinit var resultadoPeso: TextView
    private lateinit var descripcionPeso: TextView
    private lateinit var recalcularButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_result)
        initComponent()
        initUI()
        initListeners()
    }

    private fun initComponent() {
        tituloPeso = findViewById(R.id.tituloPeso)
        resultadoPeso = findViewById(R.id.resultadoPeso)
        descripcionPeso = findViewById(R.id.descripcionPeso)
        recalcularButton = findViewById(R.id.recalcularButton)
    }

    private fun initUI() {
        val titulo: String = intent.extras?.getString("Título").orEmpty()
        tituloPeso.text = titulo
        val resultado: String? = intent.extras?.getString("Resultado")
        resultadoPeso.text = resultado.toString()
        val descripcion = intent.extras?.getString("Descripción").orEmpty()
        descripcionPeso.text = descripcion.toString()
    }

    private fun initListeners() {
        recalcularButton.setOnClickListener {
            navigateRecalcular()
        }
    }

    private fun navigateRecalcular() {
        val intent = Intent(this, ImcCalculatorActivity::class.java)
        startActivity(intent)
    }
}