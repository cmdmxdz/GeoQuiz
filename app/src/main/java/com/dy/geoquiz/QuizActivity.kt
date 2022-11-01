package com.dy.geoquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat


class QuizActivity : AppCompatActivity() {
    private val TAG = "QuizActivity"
    private val KEY_INDEX = "index"
    private val KEY_IS_ANSWERED = "is_answered"
    private val REQUEST_CODE_CHEAT = 0

    private var mTrueButton: Button? = null
    private var mFalseButton: Button? = null
    private var mPrevButton: ImageButton? = null
    private var mNextButton: ImageButton? = null
    private var mCheatButton: Button? = null
    private var mQuestionTextView: TextView? = null
    private var mIsShowResult = false
    private val mQuestionBank = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var mCurrentIndex = 0
    private var mIsCheater = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.activity_quiz)
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)

            var isAnswerStateList = mutableListOf<Boolean>()
            if (savedInstanceState.getBooleanArray(KEY_IS_ANSWERED) != null) {
                isAnswerStateList = savedInstanceState.getBooleanArray(KEY_IS_ANSWERED)?.toMutableList()!!
            } else {
                for (i in mQuestionBank.indices) {
                    isAnswerStateList.add(i, false)
                }
            }
            for (i in mQuestionBank.indices) {
                mQuestionBank[i].setAnswerState(isAnswerStateList[i])
            }
        }

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
        mPrevButton = findViewById(R.id.prev_button)
        mPrevButton?.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + mQuestionBank.size - 1) % mQuestionBank.size
            updateQuestion()
        }
        mNextButton = findViewById(R.id.next_button)
        mNextButton?.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            mIsCheater = false
            updateQuestion()
        }
        mCheatButton = findViewById(R.id.cheat_button)
        mCheatButton!!.setOnClickListener {
            // Start CheatActivity
            val answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue()!!
            val intent: Intent = CheatActivity.newIntent(this@QuizActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        mQuestionTextView?.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return
            }
            mIsCheater = CheatActivity.wasAnswerShown(data)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex)
        val isAnswerStateList = mutableListOf<Boolean>()
        isAnswerStateList.clear()
        for (question in mQuestionBank.iterator()) {
            isAnswerStateList.add(question.isAnswered() ?: false)
        }
        savedInstanceState.putBooleanArray(KEY_IS_ANSWERED, isAnswerStateList.toBooleanArray())

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].getTextResId()!!
        mQuestionTextView!!.setText(question)
        updateAnswerButtons()
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue()!!
        val messageResId: Int = if (mIsCheater) {
            R.string.judgment_toast
        } else {
            if (userPressedTrue == answerIsTrue) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        mQuestionBank[mCurrentIndex].setAnswerResult(userPressedTrue == answerIsTrue)
        mQuestionBank[mCurrentIndex].setAnswerState(true)
        updateAnswerButtons()
        checkShowResult()
    }

    private fun updateAnswerButtons() {
        if (mQuestionBank[mCurrentIndex].isAnswered() == true) {
            mTrueButton?.let {
                it.isEnabled = false
                it.isClickable = false
            }
            mFalseButton?.let {
                it.isEnabled = false
                it.isClickable = false
            }
        } else {
            mTrueButton?.let {
                it.isEnabled = true
                it.isClickable = true
            }
            mFalseButton?.let {
                it.isEnabled = true
                it.isClickable = true
            }
        }
    }

    private fun checkShowResult() {
        if (mIsShowResult) {
            return
        } else {
            var shouldShowResult = true
            for (i in mQuestionBank.indices) {
                if (mQuestionBank[i].isAnswered() == false) {
                    shouldShowResult = false
                    break
                }
            }
            if (shouldShowResult) {
                mIsShowResult = true
                var correctNumber = 0
                for (i in mQuestionBank.indices) {
                    if (mQuestionBank[i].isAnswerCorrect() == true) {
                        ++correctNumber
                    }
                }
                Toast.makeText(this, "result = ${getNoMoreThanTwoDigits(correctNumber * 100.0 / mQuestionBank.size)}%", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 对入参保留最多两位小数(舍弃末尾的0)，如:
     * 3.345->3.34
     * 3.40->3.4
     * 3.0->3
     */
    private fun getNoMoreThanTwoDigits(number: Double): String {
        val format = DecimalFormat("0.##")
        //未保留小数的舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(number)
    }


}