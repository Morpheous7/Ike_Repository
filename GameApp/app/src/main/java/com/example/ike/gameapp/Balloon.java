package com.example.ike.gameapp;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.ike.gameapp.utils.PixelHelper;

/**
 * Created by Ike on 5/29/2017.
 */

public class Balloon extends android.support.v7.widget.AppCompatImageView implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
    private ValueAnimator mAnimator;
    private BalloonListener mListener;
    private boolean mPopped;

    public Balloon(Context context) {
        super(context);
    }

    public Balloon(Context context, int color, int rawHeight) {
        super(context);
        mListener = (BalloonListener) context;

        this.setImageResource(R.drawable.balloon);
        this.setColorFilter(color);

        int rawWidth = rawHeight / 2;

        int dpHeight = PixelHelper.pixelsToDp(rawHeight,context);
        int dpWidth = PixelHelper.pixelsToDp(rawWidth,context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dpWidth, dpHeight);
        setLayoutParams(params);
    }

    //This method is used to set animation making the balloon go up from bottom to top
    public void releaseBalloon(int screenHeight, int duration){
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(duration);//Setting Animation duration
        mAnimator.setFloatValues(screenHeight,0f);//Using float values makes it smooth (better than integers)
        mAnimator.setInterpolator(new LinearInterpolator());//Determines whether you're animating in a linear fashion
        mAnimator.setTarget(this);//using "this" forces it to implement the interfaces
        mAnimator.addListener(this);//using "this" forces it to implement the interfaces
        mAnimator.addUpdateListener(this);//using "this" forces it to implement the interfaces
        mAnimator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if(!mPopped){//To pop balloon when it gets to top of screen and not by user contact
            mListener.popBalloon(this, false);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {

        setY((Float) valueAnimator.getAnimatedValue());//Dont use Fraction use Value for object and cast to cast to float
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (!mPopped && event.getAction()==MotionEvent.ACTION_DOWN){
            mListener.popBalloon(this, true);
            mPopped = true;//To confirm the balloon has been popped
            mAnimator.cancel();//To stop the animation from moving after it's popped
        }return super.onTouchEvent(event);
    }

    public void setPopped(boolean popped) {
        mPopped = popped;
        if(popped){
            mAnimator.cancel();//Prevents
        }
    }

    public interface BalloonListener {
        void popBalloon(Balloon balloon, boolean userTouch);
    }
}
