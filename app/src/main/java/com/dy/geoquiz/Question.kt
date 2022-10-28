package com.dy.geoquiz

class Question(textResId: Int, answerTrue: Boolean) {
    private var mTextResId: Int? = textResId
    private var mAnswerTrue: Boolean? = answerTrue

    fun getTextResId(): Int? {
        return mTextResId
    }

    fun setTextResId(textResId: Int) {
        mTextResId = textResId
    }

    fun isAnswerTrue(): Boolean? {
        return mAnswerTrue
    }

    fun setAnswerTrue(answerTrue: Boolean) {
        mAnswerTrue = answerTrue
    }
}