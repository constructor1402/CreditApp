package com.example.creditapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etValorPropiedad, etMontoCredito, etPlazo, etTasaInteres;
    private Button btnSimular;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Verifica que este sea el nombre correcto del archivo XML

        // Inicializar vistas
        etValorPropiedad = findViewById(R.id.etValorPropiedad);
        etMontoCredito = findViewById(R.id.etMontoCredito);
        etPlazo = findViewById(R.id.etPlazo);
        etTasaInteres = findViewById(R.id.etTasaInteres);
        btnSimular = findViewById(R.id.btnSimular);
        tvResultado = findViewById(R.id.tvResultado);

        btnSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simularCredito();
            }
        });
    }

    private void simularCredito() {
        // Verificar que ningún campo esté vacío
        if (etValorPropiedad.getText().toString().isEmpty() ||
                etMontoCredito.getText().toString().isEmpty() ||
                etPlazo.getText().toString().isEmpty() ||
                etTasaInteres.getText().toString().isEmpty()) {

            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double valorPropiedad = Double.parseDouble(etValorPropiedad.getText().toString());
            double montoCredito = Double.parseDouble(etMontoCredito.getText().toString());
            int plazo = Integer.parseInt(etPlazo.getText().toString());
            double tasaInteres = Double.parseDouble(etTasaInteres.getText().toString()) / 100;

            // Validaciones de valores ingresados
            if (valorPropiedad < 70000000) {
                Toast.makeText(this, "El valor de la propiedad no puede ser inferior a $70.000.000", Toast.LENGTH_LONG).show();
                return;
            }

            if (montoCredito < 50000000 || montoCredito > valorPropiedad * 0.7) {
                Toast.makeText(this, "El monto del crédito debe estar entre $50.000.000 y el 70% del valor de la propiedad", Toast.LENGTH_LONG).show();
                return;
            }

            if (plazo < 5 || plazo > 20) {
                Toast.makeText(this, "El plazo debe estar entre 5 y 20 años", Toast.LENGTH_LONG).show();
                return;
            }

            // Cálculo de la cuota mensual
            double tasaMensual = tasaInteres / 12;
            int numerosPagos = plazo * 12;
            double cuotaMensual = (montoCredito * tasaMensual * Math.pow(1 + tasaMensual, numerosPagos)) /
                    (Math.pow(1 + tasaMensual, numerosPagos) - 1);

            String resultado = String.format("Cuota mensual estimada: $%.2f\n", cuotaMensual) +
                    String.format("Total a pagar: $%.2f\n", cuotaMensual * numerosPagos) +
                    String.format("Intereses totales: $%.2f", (cuotaMensual * numerosPagos) - montoCredito);

            tvResultado.setText(resultado);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese valores válidos en todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
