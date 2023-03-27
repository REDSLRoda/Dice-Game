package com.example.dicegame2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
//This is the Explanation Video for the Kotlin Application…..
//https://drive.google.com/file/d/1t-ZMw-23cAEKootqbdOVK_zB9KwXuIBO/view?usp=sharing
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            openNewGame()

        }
        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener {
            about()

        }
/*val button2: Button = findViewById(R.id.button2)
button2.setOnClickListener {
    Toast.makeText(applicationContext,"20210646 " + " Achintha Rodrigo \n" + "I confirm that I understand what plagiarism is and have read and\n" +
            "understood the section on Assessment Offences in the Essential\n" +
            "Information for Students. The work that I have submitted is\n" +
            "entirely my own. Any work from other authors is duly referenced\n" +
            "and acknowledged",Toast.LENGTH_LONG).show()
            }*/

}
    @SuppressLint("SuspiciousIndentation")
    private fun openNewGame() {
    val intent = Intent(this, DiceActivity::class.java)
        startActivity(intent)
    }
    private fun about(){
        MaterialAlertDialogBuilder(this)
            .setTitle("20210646 " +" Achintha Rodrigo")
            .setMessage("I confirm that I understand what plagiarism is and have read and\n" +
                    "understood the section on Assessment Offences in the Essential\n" +
                    "Information for Students. The work that I have submitted is\n" +
                    "entirely my own. Any work from other authors is duly referenced\n" +
                    "and acknowledged")
            .show()
    }


}