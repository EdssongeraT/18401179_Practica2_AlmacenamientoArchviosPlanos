package mx.edu.ittepic.a18401179_practica2_almacenamientoarchviosplanos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PedidosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is pedidos Fragment"
    }
    val text: LiveData<String> = _text
}