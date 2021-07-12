package net.zjueva.bytedance_hw1;


public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    public Question(int textId,boolean answertrue) {
        mTextResId=textId;
        mAnswerTrue=answertrue;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }
}
