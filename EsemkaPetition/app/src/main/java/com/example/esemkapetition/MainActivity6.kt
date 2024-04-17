package com.example.esemkapetition

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkapetition.databinding.ActivityMain6Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain6Binding.inflate(layoutInflater)
        setContentView(Binding.root)
//        #9F9F9F
            val id = intent.getStringExtra("Id")
        Binding.button4.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {

                val Chechked =
                    URL("http://10.0.2.2:5000/petition/$id/sign").openConnection() as HttpURLConnection
                Chechked.requestMethod = "POST"
                Chechked.setRequestProperty("Content-Type", "application/json")

                val Json = JSONObject().apply {
                    put("signerID", MainActivity.MyID)
                }
                val Out = DataOutputStream(Chechked.outputStream)
                Out.write(Json.toString().toByteArray())

                val Con = Chechked.responseCode
                runOnUiThread {
                    if (Con == 200) {
                        Toast.makeText(this@MainActivity6, "Succes", Toast.LENGTH_SHORT).show()
                    finish()
                    }
                }
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            val PetitionID =
                URL("http://10.0.2.2:5000/petition/$id").openStream().bufferedReader().readText()

            val Json = JSONObject(PetitionID)
            Binding.textView18.text = Json.getString("title")
            Binding.textView19.text = Json.getString("creatorName")
            Binding.textView20.text = Json.getString("description")
            Binding.textView21.text =
                "Singed By ${Json.getString("target")} People - ${Json.getString("totalSigners")} more to go"
//            "petitionID": 1,
//            "title": "string",
//            "description": "string",
//            "target": 2147483647,
//            "creatorName": "string string",
//            "totalSigners": 1
            val Cek_petition =
                URL("http://10.0.2.2:5000/petition/$id/is-signed?signerID=${MainActivity.MyID}").openStream()
                    .bufferedReader().readText()
            val JsonCek = JSONObject(Cek_petition)

            if (JsonCek.getString("isSigned") == "true") {
                runOnUiThread {

                    Binding.button4.isEnabled = false
                    val backgroundColor = ContextCompat.getColor(this@MainActivity6, R.color.active)
                    Binding.button4.backgroundTintList = ColorStateList.valueOf(backgroundColor)
                }

            }
        }
    }
}