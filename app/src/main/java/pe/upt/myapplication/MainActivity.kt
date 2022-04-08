package pe.upt.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object{
        var NOMBRES : String = "NOMBRES"
        var DIRECCION : String = "DIRECCION"
        var CELULAR : String = "CELULAR"
        var CORREO : String = "CORREO"
        var GITHUB : String = "GITHUB"
        var IMAGEN : String = "IMAGEN"
    }

    var imagen:Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnTomarFoto).setOnClickListener{
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(intent)
        }


        if(ImageHelp.imagen!=null){
            imagen = ImageHelp.imagen
            findViewById<ImageView>(R.id.ivFoto).setImageBitmap(ImageHelp.imagen)
        }

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            try {

                    ImageHelp.imagen = null
                    var nombres:String = findViewById<EditText>(R.id.edtNombres).text.toString()
                    var direccion:String = findViewById<EditText>(R.id.edtDireccion).text.toString()
                    var celular:String = findViewById<EditText>(R.id.edtCelular).text.toString()
                    var correo:String = findViewById<EditText>(R.id.edtCorreo).text.toString()
                    var gitHub:String = findViewById<EditText>(R.id.edtGitHub).text.toString()

                    Toast.makeText(applicationContext,nombres + " "+direccion,Toast.LENGTH_LONG).show()

                    var intent = Intent(applicationContext,ActivityResultado::class.java)
                    intent.putExtra(NOMBRES,nombres)
                    intent.putExtra(DIRECCION,direccion)
                    intent.putExtra(CELULAR,celular)
                    intent.putExtra(CORREO,correo)
                    intent.putExtra(GITHUB,gitHub)
                    intent.putExtra(IMAGEN,imagen)
                    startActivity(intent)

            }catch (exception:Exception){
                Toast.makeText(applicationContext,exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result -> if(result.resultCode==Activity.RESULT_OK){
            val extras = result.data?.extras
            imagen = extras?.get("data") as Bitmap
            ImageHelp.imagen = imagen
            var foto:ImageView = findViewById(R.id.ivFoto)
            foto.setImageBitmap(imagen)
        }
    }
}