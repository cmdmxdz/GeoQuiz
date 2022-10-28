package com.dy.geoquiz

import android.os.Bundle
import android.view.Gravity
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
            val toast = Toast(this)
            /// 当前版本为sdk=32>30, 重力位置设置不生效
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.setText(R.string.correct_toast)
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton?.setOnClickListener {
            // Does nothing yet, but soon!
            val toast = Toast(this)
            /// 当前版本为sdk=32>30, 重力位置设置不生效
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.setText(R.string.incorrect_toast)
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
    }
}