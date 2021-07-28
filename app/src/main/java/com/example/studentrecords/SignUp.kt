package com.example.studentrecords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class SignUp : AppCompatActivity() {

    lateinit var etRoll:EditText
    lateinit var etPass:EditText
    lateinit var etMobile:EditText
    lateinit var etAddress:EditText
    lateinit var etQualification:EditText
    lateinit var btnRegister:Button

    lateinit var roll:String
    lateinit var pass:String
    lateinit var mobile:String
    lateinit var address:String
    lateinit var qualification:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        etRoll=findViewById(R.id.etRoll)
        etPass=findViewById(R.id.etPassword)
        etAddress=findViewById(R.id.etAddress)
        etQualification=findViewById(R.id.etQualification)
        etMobile=findViewById(R.id.etMobile)
        btnRegister=findViewById(R.id.btnRegister)



        btnRegister.setOnClickListener {
            roll=etRoll.text.toString()
            mobile=etMobile.text.toString()
            pass=etPass.text.toString()
            address=etAddress.text.toString()
            qualification=etQualification.text.toString()


            var rq = Volley.newRequestQueue(this)
            var url = getString(R.string.url_register).toString()
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->

                    val intent= Intent(this,Profile::class.java)
                    startActivity(intent)
//Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()

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
                    params.put("m", mobile)
                    params.put("a", address)
                    params.put("q", qualification)
                    return params
                }
            }
//adding request to queue
            rq.add(stringRequest)
        }




        }

    }
