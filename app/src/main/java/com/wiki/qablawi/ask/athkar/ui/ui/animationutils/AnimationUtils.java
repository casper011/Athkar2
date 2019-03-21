package com.wiki.qablawi.ask.athkar.ui.ui.animationutils;

import android.widget.ImageButton;

public class AnimationUtils {
    public static void buttonAnimation(ImageButton button) {
        button.animate().scaleY(1.5f).scaleX(1.5f).setDuration(200).start();
    }

    public static void removeButtonAnimation(ImageButton button) {
        button.animate().scaleX(1).scaleY(1).setDuration(200).start();
    }

    public static void rightArrowAnimation(ImageButton button) {
        button.animate().translationX(10.0f).setDuration(200).start();
    }

    public static void removeRightArrowAnimation(ImageButton button) {
        button.animate().translationX(-10.0f).setDuration(200).start();
    }

    public static void leftArrowButtonAnimation(ImageButton button) {
        button.animate().translationX(-10).setDuration(200).start();
    }

    public static void removeLeftArrowButtonAnimation(ImageButton button) {
        button.animate().translationX(10).setDuration(200).start();
    }
}