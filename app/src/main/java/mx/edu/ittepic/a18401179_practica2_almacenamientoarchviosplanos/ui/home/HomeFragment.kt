package mx.edu.ittepic.a18401179_practica2_almacenamientoarchviosplanos.ui.home

import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.a18401179_practica2_almacenamientoarchviosplanos.databinding.FragmentHomeBinding
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnInsertar.setOnClickListener {
            if (!(binding.campoNombre.text.toString().equals(""))||!(binding.campoTipo.text.toString().equals(""))||!(binding.campoPrecio.text.toString().equals(""))){
                    var pedido =
                        binding.campoNombre.text.toString() + "-" + binding.campoTipo.text.toString() +
                                "-" + binding.campoPrecio.text.toString() + "|"
                    guardarPedido(pedido)
                }else{
                    AlertDialog.Builder(requireContext()).setTitle("Campos vacios").setMessage("Por favor rellene los campos faltantes")
                        .setNeutralButton("Okay"){d,i->}
                        .show()
            }
        }
        return root
    }

    //Genera arreglo para los pedidos
    fun obtenerPedidos():Array<String>{
        var pedidos = ""
            try {
                var archivo = InputStreamReader(requireContext().openFileInput("pedidos.txt"))
                archivo.forEachLine {
                    pedidos = it+"\n"
                    println(pedidos)
                }
                archivo.close()
            }catch (e:Exception){
            }
        var arreglo = pedidos.split("|").toTypedArray()
        return arreglo
    }

    fun guardarPedido(pedido:String){
        var listaPedidos = obtenerPedidos()
        if (listaPedidos.size>=20){
            AlertDialog.Builder(requireContext()).setTitle("Maximo de pedidos alcanzado")
                .setMessage("El numero de pedidos maximos(20) ha sido alcanzado")
                .setNeutralButton("Ok"){d,i->
                }.show()
        }else {
            try {
                var archivo =
                    OutputStreamWriter(requireContext().openFileOutput("pedidos.txt", MODE_APPEND))
                archivo.write(pedido)
                archivo.flush()
                archivo.close()
                binding.campoNombre.setText("")
                binding.campoTipo.setText("")
                binding.campoPrecio.setText("")
                Toast.makeText(requireContext(), "Se guardo con exito", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}