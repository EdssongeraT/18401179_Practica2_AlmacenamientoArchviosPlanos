package mx.edu.ittepic.a18401179_practica2_almacenamientoarchviosplanos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import mx.edu.ittepic.a18401179_practica2_almacenamientoarchviosplanos.databinding.FragmentPedidosBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pedidosViewModel =
            ViewModelProvider(this).get(PedidosViewModel::class.java)

        _binding = FragmentPedidosBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val recycler = binding.recyclerView
        val adapter = CustomAdapter{posicion -> onItemClick(posicion)}

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        var pedidos = obtenerPedidos()
        if(pedidos.size>0) {

            for (pedido in pedidos) {
                var elementos = pedido.split("-").toTypedArray()
                if(elementos.size==3) {
                    adapter.titles += elementos[0]
                    adapter.types += elementos[1]
                    adapter.prices+= elementos[2]
                }
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
                pedidos = it
            }
            archivo.close()
        }catch (e:Exception){
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG)
        }
        var arreglo = pedidos.split("|").toTypedArray()
        return arreglo
    }
    fun eliminarPedido(posicion: Int,pedidos:Array<String>){
            var i=0
            var datos = ""
        while (i<pedidos.size-1){
            if(i!=posicion) {
                var elementos = pedidos[i].split("-").toTypedArray()
                datos += elementos[0]+"-"+elementos[1]+"-"+elementos[2]+"|"
            }
            i++
        }
        try {
            var archivo =
                OutputStreamWriter(requireContext().openFileOutput("pedidos.txt",
                    Context.MODE_PRIVATE
                ))
            archivo.write(datos)
            archivo.flush()
            archivo.close()
            Toast.makeText(requireContext(), "Se Elimino con exito,vuelva a recargar pagina", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }
    fun actualizarPedido(posicion: Int,dato:String,pedidos: Array<String>){
        var i=0
        var datos = ""
        while (i<pedidos.size-1){
            if (i==posicion){
                datos += dato
            }else {
                var elementos = pedidos[i].split("-").toTypedArray()
                datos += elementos[0] + "-" + elementos[1] + "-" + elementos[2] + "|"
            }
            i++
        }
        try {
            var archivo =
                OutputStreamWriter(requireContext().openFileOutput("pedidos.txt",
                    Context.MODE_PRIVATE
                ))
            archivo.write(datos)
            archivo.flush()
            archivo.close()
            Toast.makeText(requireContext(), "Se Actualizo con exito,vuelva a recargar pagina", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }
    fun onItemClick(posicion:Int){
        var pedidos = obtenerPedidos()
        var elementos = pedidos[posicion].split("-").toTypedArray()
        var layout = LinearLayout(requireContext())
        layout.orientation= LinearLayout.VERTICAL
        val input = EditText(requireContext())
        input.setHint("Nombre del que realizo pedido")
        input.setText(elementos[0])
        val input2 = EditText(requireContext())
        input2.setHint("Sencilla, Especial, Super, etc.")
        input2.setText(elementos[1])
        val input3 = EditText(requireContext())
        input3.setHint("Costo")
        input3.setText(elementos[2])
        layout.addView(input)
        layout.addView(input2)
        layout.addView(input3)

        AlertDialog.Builder(requireContext()).setTitle("Acciones")
            .setMessage("¿Que desea realizar en pedido de ${elementos[0]}?")
            .setPositiveButton("Actualizar"){d,i->
                AlertDialog.Builder(requireContext()).setTitle("Actualizar").setMessage("rellene campos").setView(layout)
                    .setNegativeButton("Cancelar"){d,i->}
                    .setPositiveButton("Actualizar"){d,i->
                        if (!(input.text.toString().equals(""))||!(input2.text.toString().equals(""))||!(input3.text.toString().equals(""))){
                        var i1 = input.text.toString()
                        var i2 = input2.text.toString()
                        var i3 = input3.text.toString()
                        var dato = i1+"-"+i2+"-"+i3+"|"
                        actualizarPedido(posicion,dato,pedidos)
                        }else{
                            Toast.makeText(requireContext(),"Campos vacios, por favor rellene faltantes",Toast.LENGTH_LONG)
                        }
                    }.show()
            }.setNegativeButton("Eliminar"){d,i->
                AlertDialog.Builder(requireContext()).setTitle("¿Esta seguro?")
                    .setPositiveButton("SI"){d,i->
                        eliminarPedido(posicion,pedidos)
                    }
                    .setNegativeButton("NO"){d,i->}
                    .show()
            }.setNeutralButton("Cancelar") { d, i ->
            }.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
