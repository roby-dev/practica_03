package pe.upt.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class ActivityResultado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)
        try {
            val nombres:String? = intent.getStringExtra(MainActivity.NOMBRES)
            val direccion:String? = intent.getStringExtra(MainActivity.DIRECCION)
            val celular:String? = intent.getStringExtra(MainActivity.CELULAR)
            val correo:String? = intent.getStringExtra(MainActivity.CORREO)
            val gitHub:String? = intent.getStringExtra(MainActivity.GITHUB)
            val imagen:Bitmap? = intent.getParcelableExtra(MainActivity.IMAGEN)

            findViewById<TextView>(R.id.tvNombres).setText(resources.getString(R.string.tvNombre) + " "+nombres)
            findViewById<TextView>(R.id.tvDireccion).setText(resources.getString(R.string.tvDireccion) + " "+direccion)
            findViewById<TextView>(R.id.tvCelular).setText(resources.getString(R.string.tvCelular) + " "+celular)
            findViewById<TextView>(R.id.tvCorreo).setText(resources.getString(R.string.tvCorreo) + " "+correo)
            findViewById<TextView>(R.id.tvGitHub).setText(resources.getString(R.string.tvPerfil) + " "+gitHub)
            findViewById<ImageView>(R.id.ivFoto).setImageBitmap(imagen)

            findViewById<Button>(R.id.btnDireccion).setOnClickListener {
                if(!direccion.isNullOrEmpty()){
                    var lugar:String = direccion.replace(" ","+")
                    var location:Uri = Uri.parse("geo:0.0?q="+lugar)
                    var locationIntent = Intent(Intent.ACTION_VIEW,location)
                    startActivity(locationIntent)
                }
            }

            findViewById<Button>(R.id.btnCelular).setOnClickListener {
                if(!celular.isNullOrEmpty()){
                    var number:Uri = Uri.parse("tel:"+celular)
                    var callIntent = Intent(Intent.ACTION_DIAL,number)
                    startActivity(callIntent)
                }
            }

            findViewById<Button>(R.id.btnCorreo).setOnClickListener {
                if(!correo.isNullOrEmpty()) {
                    var emailIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("mailto:" + correo + "?subject=" + resources.getString(R.string.titulo))
                    )
                    emailIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        getResources().getString(R.string.mensaje1) + " " + nombres + getResources().getString(
                            R.string.mensaje2
                        ) + " " + celular + " " + getResources().getString(R.string.mensaje3) + "\"" + gitHub + "\"" + getResources().getString(
                            R.string.mensaje4
                        )
                    );
                    startActivity(emailIntent)
                }
            }
            findViewById<Button>(R.id.btnPerfil).setOnClickListener {
                var portal : Uri = Uri.parse("https://github.com/"+gitHub)
                var webIntent: Intent = Intent(Intent.ACTION_VIEW,portal)
                var activities:List<ResolveInfo> = packageManager.queryIntentActivities(webIntent,PackageManager.MATCH_DEFAULT_ONLY)
                var isIntentSafe:Boolean = activities.size >0
                var titulo:String = resources.getString(R.string.escoger)
                var wChooser = Intent.createChooser(webIntent,titulo)
                startActivity(wChooser)
            }

        }catch (e : Exception){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}