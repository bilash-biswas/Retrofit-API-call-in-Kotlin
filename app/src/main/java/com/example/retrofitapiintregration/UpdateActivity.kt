package com.example.retrofitapiintregration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofitapiintregration.databinding.ActivityUpdateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.name.setText(intent.getStringExtra("name"))
        binding.email.setText(intent.getStringExtra("email"))
        binding.salary.setText(intent.getStringExtra("salary"))

        val api = RetrofitHelper.getInstance().create(StudentApi::class.java)

        binding.button.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = api.updateStudent(
                        intent.getStringExtra("id").toString(),
                        binding.name.text.toString(),
                        binding.email.text.toString(),
                        binding.salary.text.toString()
                    )
                    Log.d("res", intent.getStringExtra("id").toString())
                        if (response.isSuccessful) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    applicationContext,
                                    "Student Successfully Updated",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                finish()
                            }
                        }
                } catch (e: Exception) {
                    Log.d("res", e.toString())
                }

            }
        }

    }
}