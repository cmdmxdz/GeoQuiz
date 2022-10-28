package com.dy.geoquiz

class Question(textResId: Int, answerTrue: Boolean) {
    private var mTextResId: Int? = textResId
    private var mAnswerTrue: Boolean? = answerTrue
    private var mIsAnswered: Boolean? = false

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


    fun isAnswered(): Boolean? {
        return mIsAnswered
    }

    fun setAnswerState(isAnswered: Boolean) {
        mIsAnswered = isAnswered
    }
}