package com.example.retrofitapiintregration


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitapiintregration.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var list: ArrayList<StudentMode>
    private lateinit var adapter : CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        val api = RetrofitHelper.getInstance().create(StudentApi::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.setHasFixedSize(true)
        adapter = CustomAdapter(application, list)
        binding.recyclerView.adapter = adapter


        lifecycleScope.launch(Dispatchers.IO){
            try {
                val studentResponse = api.getStudent()

                studentResponse.body()?.let { res ->
                    if (res.status == "success"){
                        list.clear()
                        list.addAll(res.data)

                        withContext(Dispatchers.Main){
                            adapter.notifyDataSetChanged()
                        }
                    }


                }
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.addBtn.setOnClickListener{
            startActivity(Intent(applicationContext, AddStudentActivity::class.java))
        }

    }

}