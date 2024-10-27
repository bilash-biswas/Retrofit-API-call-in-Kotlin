package com.example.retrofitapiintregration

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapiintregration.CustomAdapter.MyViewHolder
import com.example.retrofitapiintregration.databinding.ItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomAdapter(val context: Context, val mList: ArrayList<StudentMode>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(ItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val student = mList.get(position)
        holder.binding.studentId.text = student.id
        holder.binding.studentName.text = student.name
        holder.binding.studentEmail.text = student.email
        holder.binding.studentSalary.text = student.salary
        holder.binding.studentDate.text = student.time
        holder.binding.delete.setOnClickListener {
            deleteStudent(student.id, position)
        }
        holder.binding.update.setOnClickListener {
            val indent = Intent(context, UpdateActivity::class.java)
            indent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            indent.putExtra("id", student.id)
            indent.putExtra("name", student.name)
            indent.putExtra("email", student.email)
            indent.putExtra("salary", student.salary)

            context.startActivity(indent)
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class MyViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private fun deleteStudent(id: String, position: Int) {
        val api = RetrofitHelper.getInstance().create(StudentApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.deleteStudent(id)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Student has been deleted", Toast.LENGTH_SHORT).show()
                    mList.removeAt(position)
                    notifyItemRemoved(position)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}

