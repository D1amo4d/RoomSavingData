package com.example.bottomnavigationpractica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.bottomnavigationpractica.databinding.ActivityMainBinding
import com.example.bottomnavigationpractica.local.Item
import com.example.bottomnavigationpractica.local.MainDb

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainDb.getDb(this)
        db.getDao().getAllItem().asLiveData().observe(this){ list->
            binding.textView.text = ""
            list.forEach {
                val text = "Id: ${it.id} Name: ${it.name} Price: ${it.price}\n"
                binding.textView.append(text)
            }
        }
        binding.button.setOnClickListener {
            val item = Item(null,
                binding.editTextText1.text.toString(),
                binding.editTextText2.text.toString()
            )
            Thread{
                db.getDao().insertItem(item)
            }.start()
        }
    }
}