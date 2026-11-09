package net.tubalcain.cinemaxxml

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import net.tubalcain.cinemaxxml.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    private val precioEntrada = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Conectar Kotlin con activity_main.xml mediante ViewBinding.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Mostrar las películas en un Spinner.
        val peliculas = listOf(
            "Minecraft: La película",
            "Sonic 3",
            "Super Mario Bros.: La película",
            "Dune: Parte 2",
            "La hipótesis del amor"
        )

        val adaptador = ArrayAdapter(this, R.layout.item_spinner, peliculas)
        adaptador.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spinnerPeliculas.adapter = adaptador

        // 3. Escuchar el clic del botón y calcular el precio.
        binding.buttonCalcular.setOnClickListener {
            calcularTotal()
        }

        // 4. Mostrar un menú básico.
        binding.buttonMenu.setOnClickListener {
            mostrarMenu()
        }
    }

    private fun calcularTotal() {
        val cantidad = binding.editTextEntradas.text.toString().toIntOrNull()

        if (cantidad == null || cantidad !in 1..10) {
            binding.editTextEntradas.error = getString(R.string.error_entradas)
            binding.textViewTotal.text = getString(R.string.total_inicial)
            return
        }

        val pelicula = binding.spinnerPeliculas.selectedItem.toString()
        val total = cantidad * precioEntrada

        binding.textViewTotal.text = getString(
            R.string.resultado_total,
            pelicula,
            cantidad,
            total
        )
    }

    private fun mostrarMenu() {
        val menu = PopupMenu(this, binding.buttonMenu)
        menu.menuInflater.inflate(R.menu.menu_cinemax, menu.menu)

        menu.setOnMenuItemClickListener { opcion ->
            when (opcion.itemId) {
                R.id.menuReiniciar -> {
                    binding.spinnerPeliculas.setSelection(0)
                    binding.editTextEntradas.text.clear()
                    binding.textViewTotal.text = getString(R.string.total_inicial)
                    true
                }

                R.id.menuAcercaDe -> {
                    Toast.makeText(this, R.string.acerca_de, Toast.LENGTH_LONG).show()
                    true
                }

                R.id.menuTextoPersonalizado-> {
                    Toast.makeText(this, "Bienvenid@ de nuevo!", Toast.LENGTH_LONG).show()
                    true
                }

                else -> false
            }
        }

        menu.show()
    }
}
