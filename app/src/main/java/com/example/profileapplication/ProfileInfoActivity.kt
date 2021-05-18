package com.example.profileapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.TextView
import androidx.core.view.children
import com.example.profileapplication.databinding.ActivityProfileInfoBinding
import java.time.LocalDate
import java.util.*

class ProfileInfoActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_ACCESS_TYPE = 1
        const val ACCESS_MESSAGE = "ACCESS_MESSAGE"
        const val USER_KEY = "USER"
    }

    private lateinit var binding: ActivityProfileInfoBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ACCESS_TYPE){
            if(resultCode == RESULT_OK){
                user = data?.extras?.getParcelable(ACCESS_MESSAGE)!!
                setUser()
            }
        }
    }


    private fun init() {
        user = User("Revaz", "Giorgadze", "revaz961@gmail.com", 1996, "Male")
        setUser()
        binding.btnModify.setOnClickListener {
            modifyClick()
        }
    }

    private fun modifyClick() {
        val intent = Intent(this, ProfileModifyActivity::class.java)
        intent.putExtra(USER_KEY, user)
        startActivityForResult(intent, REQUEST_ACCESS_TYPE)
    }

    private fun setUser() {
        binding.tvFirstname.text = user.name
        binding.tvSecondname.text = user.secondName
        binding.tvEmail.text = user.email
        binding.tvBirthDay.text = user.birthDate.toString()
        binding.tvGender.text = user.gender
    }
}