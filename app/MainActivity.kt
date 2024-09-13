import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etValorPropiedad: EditText
    private lateinit var etMontoCredito: EditText
    private lateinit var etPlazo: EditText
    private lateinit var etTasaInteres: EditText
    private lateinit var btnSimular: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        etValorPropiedad = findViewById(R.id.etValorPropiedad)
        etMontoCredito = findViewById(R.id.etMontoCredito)
        etPlazo = findViewById(R.id.etPlazo)
        etTasaInteres = findViewById(R.id.etTasaInteres)
        btnSimular = findViewById(R.id.btnSimular)
        tvResultado = findViewById(R.id.tvResultado)

        btnSimular.setOnClickListener {
            simularCredito()
        }
    }

    private fun simularCredito() {
        // Verificar que ningún campo esté vacío
        if (etValorPropiedad.text.toString().isEmpty() ||
            etMontoCredito.text.toString().isEmpty() ||
            etPlazo.text.toString().isEmpty() ||
            etTasaInteres.text.toString().isEmpty()) {

            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val valorPropiedad = etValorPropiedad.text.toString().toDouble()
            val montoCredito = etMontoCredito.text.toString().toDouble()
            val plazo = etPlazo.text.toString().toInt()
            val tasaInteres = etTasaInteres.text.toString().toDouble() / 100

            // Validaciones de valores ingresados
            if (valorPropiedad < 70000000) {
                Toast.makeText(this, "El valor de la propiedad no puede ser inferior a $70.000.000", Toast.LENGTH_LONG).show()
                return
            }

            if (montoCredito < 50000000 || montoCredito > valorPropiedad * 0.7) {
                Toast.makeText(this, "El monto del crédito debe estar entre $50.000.000 y el 70% del valor de la propiedad", Toast.LENGTH_LONG).show()
                return
            }

            if (plazo < 5 || plazo > 20) {
                Toast.makeText(this, "El plazo debe estar entre 5 y 20 años", Toast.LENGTH_LONG).show()
                return
            }

            // Cálculo de la cuota mensual
            val tasaMensual = tasaInteres / 12
            val numerosPagos = plazo * 12
            val cuotaMensual = (montoCredito * tasaMensual * Math.pow(1 + tasaMensual, numerosPagos.toDouble())) /
                    (Math.pow(1 + tasaMensual, numerosPagos.toDouble()) - 1)

            val resultado = String.format("Cuota mensual estimada: $%.2f\n", cuotaMensual) +
                    String.format("Total a pagar: $%.2f\n", cuotaMensual * numerosPagos) +
                    String.format("Intereses totales: $%.2f", (cuotaMensual * numerosPagos) - montoCredito)

            tvResultado.text = resultado
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Por favor, ingrese valores válidos en todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
