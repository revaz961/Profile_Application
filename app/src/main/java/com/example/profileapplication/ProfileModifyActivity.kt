package com.example.profileapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log.d
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.example.profileapplication.databinding.ActivityProfileModifyBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ProfileModifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileModifyBinding
    private lateinit var user: User
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.etBirth.minValue = 1900
        binding.etBirth.maxValue = 2020
        binding.etBirth.wrapSelectorWheel = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.etBirth.textColor = ContextCompat.getColor(this, R.color.textColor)
        }


        setUser()

        binding.btnSave.setOnClickListener {
            saveUser()
        }
    }

    private fun saveUser() {
        if (!validateUser())
            return
        modifyUser()

        val data = Intent()
        data.putExtra(ProfileInfoActivity.ACCESS_MESSAGE, user);
        setResult(RESULT_OK, data);
        finish();
    }

    private fun modifyUser(){
        user.name = binding.etFirstName.text.trim().toString()
        user.secondName = binding.etSecondName.text.trim().toString()
        user.email = binding.etEmail.text.trim().toString()
        user.birthDate = binding.etBirth.value
        binding.rgGender.forEach {
            if (it is RadioButton && it.id == binding.rgGender.checkedRadioButtonId)
                user.gender = it.text.toString()
        }
    }

    private fun setUser() {
        user = intent.extras?.getParcelable(ProfileInfoActivity.USER_KEY)!!
        d("user", user.toString())
        binding.etFirstName.setText(user?.name)
        binding.etSecondName.setText(user?.secondName)
        binding.etEmail.setText(user?.email)
        binding.etBirth.value = user!!.birthDate
        binding.rgGender.forEach {
            if (it is RadioButton && it.text.toString() == user.gender)
                it.isChecked = true
        }
    }

    private fun validateUser(): Boolean {
        var valid = true
        binding.etFirstName.setBackgroundResource(R.color.green)
        binding.etSecondName.setBackgroundResource(R.color.green)
        binding.etEmail.setBackgroundResource(R.color.green)
        binding.etBirth.setBackgroundResource(R.color.green)

        if (binding.etFirstName.text.trim().isEmpty()) {
            binding.etFirstName.setBackgroundResource(R.color.red)
            valid = false
        }
        if (binding.etSecondName.text.trim().isEmpty()) {
            binding.etSecondName.setBackgroundResource(R.color.red)
            valid = false
        }
        if (!binding.etEmail.text.trim().matches(emailPattern.toRegex())) {
            binding.etEmail.setBackgroundResource(R.color.red)
            valid = false
        }
        return valid
    }
}