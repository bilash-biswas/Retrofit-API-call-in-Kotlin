package com.example.retrofitapiintregration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofitapiintregration.databinding.ActivityAddStudentBinding
import com.example.retrofitapiintregration.databinding.ItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = RetrofitHelper.getInstance().create(StudentApi::class.java)

        binding.button.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                withContext(Dispatchers.Main){
                    try {
                        val response = api.addStudent(binding.name.text.toString(),binding.email.text.toString(),binding.salary.text.toString())
                        if (response.isSuccessful){
                            withContext(Dispatchers.Main){
                                Toast.makeText(applicationContext, "Student Successfully added", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                finish()
                            }
                        }
                    }catch (e: Exception){
                        withContext(Dispatchers.Main){
                            Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }


                }
            }
        }

    }
}