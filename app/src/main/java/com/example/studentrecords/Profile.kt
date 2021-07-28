package com.example.studentrecords

import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener

class Profile : AppCompatActivity() {

    lateinit var etRoll: EditText
    lateinit var etPass: EditText
    lateinit var etMobile: EditText
    lateinit var etAddress: EditText
    lateinit var etQualification: EditText
    lateinit var btnUpdate: Button
    lateinit var btnDelete:Button
    lateinit var btnFetch:Button

    lateinit var roll:String
    lateinit var pass:String
    lateinit var mobile:String
    lateinit var address:String
    lateinit var qualification:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        etRoll=findViewById(R.id.etRoll)
        etPass=findViewById(R.id.etPassword)
        etAddress=findViewById(R.id.etAddress)
        etQualification=findViewById(R.id.etQualification)
        etMobile=findViewById(R.id.etMobile)
        btnUpdate=findViewById(R.id.btnUpdate)
        btnDelete=findViewById(R.id.btnDelete)
        btnFetch=findViewById(R.id.btnFetch)



        roll= intent.getStringExtra("roll").toString()
        etRoll.setText(roll)

        btnFetch.setOnClickListener {

            Toast.makeText(this, "Fetching", Toast.LENGTH_SHORT).show()
            fetch()


        }

        btnUpdate.setOnClickListener {
            //Toast.makeText(this, "Updating", Toast.LENGTH_SHORT).show()
            updateDetails()
        }


        btnDelete.setOnClickListener {

            deleteUser()
        }








    }

    private fun deleteUser() {
        roll=etRoll.text.toString()
        val url=getString(R.string.url_delete)+"?r="+roll
        val requestQueue=Volley.newRequestQueue(this)

        val request=object : StringRequest(Method.GET,url,
        Response.Listener {
                          if(it.contains("delete"))
                              Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                          else
                              Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener {

            }) {
//            override fun getParams(): MutableMap<String, String> {
//                val params=HashMap<String,String>()
//                params.put("r",roll)
//                return params
//            }

//            override fun getHeaders(): MutableMap<String, String> {
//                val params=HashMap<String,String>()
//                params.put("r",roll)
//                return params
            //}
        }

        requestQueue.add(request)

        
    }

    private fun updateDetails() {
        pass=etPass.text.toString()
        mobile=etMobile.text.toString()
        address=etAddress.text.toString()
        qualification=etQualification.text.toString()

        val url=getString(R.string.url_update)

        val requestQueue= Volley.newRequestQueue(this)

        val request= object : StringRequest(Method.POST,url,
        Response.Listener<String> {

            if(it.contains("success"))
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

            })
        {
            override fun getParams(): MutableMap<String, String> {
                val params=HashMap<String,String>()
                params.put("r",roll)
                params.put("p",pass)
                params.put("m",mobile)
                params.put("a",address)
                params.put("q",qualification)
                return params
            }
        }

        requestQueue.add(request)

    }

    private fun fetch() {
         roll=etRoll.text.toString()
        val url=getString(R.string.url_fetch)  //+"?r=101"
        val request= object: StringRequest(Request.Method.POST,   url,
          Response.Listener<String>{ response ->



              val jsonObject = JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1))
              val jsonArray = jsonObject.getJSONArray("result")



              for (i in 0 until jsonArray.length()) {
              val jo = jsonArray.getJSONObject(i);
              // Do you fancy stuff
              // Example: String gifUrl = jo.getString("url");
                  Log.d("Response", "Value: ${jo.getString("ADDRESS")}")
                  etPass.setText(jo.getString("PASSWORD"))
                 etMobile.setText(jo.getString("MOBILE"))
                  etAddress.setText(jo.getString("ADDRESS"))
                  etQualification.setText(jo.getString("QUALIFICATION"))
          }



              Log.d("response", "Response: $response")
          },Response.ErrorListener {
                Log.d("Response","Error $it" )
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params=HashMap<String,String>()
                params.put("r",roll)
                return params
            }

        }

        val rq=Volley.newRequestQueue(this)
        rq.add(request)

    }
}