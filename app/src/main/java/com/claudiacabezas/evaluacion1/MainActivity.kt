package com.claudiacabezas.evaluacion1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.claudiacabezas.evaluacion1.items.CuentaMesa
import com.claudiacabezas.evaluacion1.items.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var cuentaMesa: CuentaMesa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pastel = ItemMenu(getString(R.string.pastel_choclo), 12000)
        val cazuela = ItemMenu(getString(R.string.cazuela), 10000)
        cuentaMesa = CuentaMesa()

        val etCantidadPastel = findViewById<EditText>(R.id.cantPastelET)
        val tvSubtotalPastel = findViewById<TextView>(R.id.subtotalPastelTV)
        val etCantidadCazuela = findViewById<EditText>(R.id.cantCazuelaET)
        val tvSubtotalCazuela = findViewById<TextView>(R.id.subtotalCazuelaTV)
        val tvTotalSinPropina = findViewById<TextView>(R.id.totalSinPropinaTV)
        val tvTotalConPropina = findViewById<TextView>(R.id.totalConPropinaTV)
        val switchPropina = findViewById<SwitchCompat>(R.id.propinaSwitch)

        fun actualizarTotales() {
            val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
            val totalConPropina = cuentaMesa.calcularTotalConPropina()
            val formatoPeso = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            tvTotalSinPropina.text = getString(R.string.total_sin_propina, formatoPeso.format(totalSinPropina))
            tvTotalConPropina.text = getString(R.string.total_con_propina, formatoPeso.format(totalConPropina))
        }

        etCantidadPastel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(pastel, cantidad)
                val subtotal = pastel.precio * cantidad
                val formatoPeso = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                tvSubtotalPastel.text = getString(R.string.subtotal_text, formatoPeso.format(subtotal))
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etCantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(cazuela, cantidad)
                val subtotal = cazuela.precio * cantidad
                val formatoPeso = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                tvSubtotalCazuela.text = getString(R.string.subtotal_text, formatoPeso.format(subtotal))
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotales()
        }
    }
}