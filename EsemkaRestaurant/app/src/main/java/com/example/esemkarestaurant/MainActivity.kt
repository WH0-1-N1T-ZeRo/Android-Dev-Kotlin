package com.example.esemkarestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkarestaurant.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    companion object{
        var Url = "http://10.0.2.2:5000"
        lateinit var id: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        Binding.editTextText.setText("8tY6mH")
Binding.button.setOnClickListener {
    GlobalScope.launch(Dispatchers.IO){
        val tableP = URL("$Url/Api/Table/${Binding.editTextText.text}").openConnection() as HttpURLConnection
        tableP.requestMethod ="POST"
        tableP.setRequestProperty("Content-Type","application/json")

        val Code = tableP.responseCode
        runOnUiThread {
            try {

                if (Code == 200) {
                    startActivity(Intent(this@MainActivity, MainActivity2::class.java))
                    id = Binding.editTextText.text.toString()
                } else {
                    Toast.makeText(this@MainActivity, "Table code Error", Toast.LENGTH_SHORT).show()
                }
            }catch (ex:Exception) {
                Toast.makeText(this@MainActivity, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
    }
}