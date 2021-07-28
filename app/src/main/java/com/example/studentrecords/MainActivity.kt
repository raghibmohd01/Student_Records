package com.example.studentrecords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import javax.security.auth.login.LoginException

class MainActivity : AppCompatActivity() {

    lateinit var etRoll: EditText
    lateinit var etPass: EditText
    lateinit var btnLogin: Button
    lateinit var btnSignUp: Button
    lateinit var roll: String
    lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etRoll = findViewById(R.id.etRoll)
        etPass = findViewById(R.id.etPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp.setOnClickListener {

            val i = Intent(this@MainActivity, SignUp::class.java)
            startActivity(i)
        }

        btnLogin.setOnClickListener {



//
//            val url = getString(R.string.url_login)
//
//            val jsonObjectRequest = object : StringRequest(Request.Method.POST,
//                url, Response.Listener {
//                    Log.d("Response", "$it")
//
//
//
//                    if (it.contains("done")) {
//
//                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//                        val i = Intent(this, Profile::class.java)
//                        i.putExtra("roll",roll)
//                        startActivity(i)
//
//                    } else {
//                        Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show()
//                    }
//
//                }, Response.ErrorListener {
//                    Log.d("Response", "Error $it ")
//                }) {
//
//                override fun getParams(): MutableMap<String, String> {
//                    val params = HashMap<String, String>()
//                    params["r"] = roll
//                    params["p"] = pass
//                    return params
//                }
//            }
//            val requestQueue = Volley.newRequestQueue(applicationContext)
//            requestQueue.add(jsonObjectRequest)
//            requestQueue.start()
//


            login()
        }

    }

    private fun login() {
        roll = etRoll.text.toString()
        pass = etPass.text.toString()
        var rq = Volley.newRequestQueue(this)
        var url = getString(R.string.url_login)
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
//t1.setText(response)
                if(response.contains("done")){
                    val intent=Intent(this,Profile::class.java)
                    intent.putExtra("roll",roll)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "login", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext, "Not login", Toast.LENGTH_LONG).show()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("r", roll)
                params.put("p", pass)
                return params
            }
        }

//adding request to queue
        rq.add(stringRequest)


    }

    override fun onBackPressed() {
            super.onBackPressed()
            finishAffinity()
        }

}