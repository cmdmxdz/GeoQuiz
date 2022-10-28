package com.dy.geoquiz

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class QuizActivity : AppCompatActivity() {

    private var mTrueButton: Button? = null
    private var mFalseButton: Button? = null
    private var mNextButton: Button? = null
    private var mQuestionTextView: TextView? = null
    private val mQuestionBank = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var mCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mQuestionTextView = findViewById<View>(R.id.question_text_view) as TextView
        val question: Int? = mQuestionBank[mCurrentIndex].getTextResId()
        question?.let { mQuestionTextView?.setText(it) }

        mTrueButton = findViewById(R.id.true_button)
        mTrueButton?.setOnClickListener {
            checkAnswer(true)
        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton?.setOnClickListener {
            checkAnswer(false)
        }
        mNextButton = findViewById<View>(R.id.next_button) as Button
        mNextButton?.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            val question = mQuestionBank[mCurrentIndex].getTextResId()!!
            mQuestionTextView!!.setText(question)
            updateQuestion()
        }
        mQuestionTextView?.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            val question = mQuestionBank[mCurrentIndex].getTextResId()!!
            mQuestionTextView!!.setText(question)
            updateQuestion()
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].getTextResId()!!
        mQuestionTextView!!.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue()!!
        val messageResId: Int = if (userPressedTrue == answerIsTrue) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}