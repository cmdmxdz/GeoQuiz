package com.dy.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private var mTrueButton: Button? = null
    private var mFalseButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mTrueButton = findViewById(R.id.true_button)
        mTrueButton?.setOnClickListener {
            // Does nothing yet, but soon!
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        }
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton?.setOnClickListener {
            // Does nothing yet, but soon!
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }
}