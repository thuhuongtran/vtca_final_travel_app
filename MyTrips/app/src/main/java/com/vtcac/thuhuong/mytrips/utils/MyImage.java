package com.vtcac.thuhuong.mytrips.utils;

import com.vtcac.thuhuong.mytrips.R;

import java.util.Random;

public class MyImage {
    public static int getDefaultImgID(int itemPosition) {
        int tempPosition = 0;
        if (itemPosition < 10) {
            tempPosition = itemPosition;
        }
        else {
            String tempPositionTxt = String.valueOf(itemPosition);
            tempPosition = Integer.parseInt(String.valueOf(tempPositionTxt.charAt(tempPositionTxt.length() - 1)));
        }
        switch (tempPosition) {
            case 0:
                return R.mipmap.default_img1;
            case 1:
                return R.mipmap.default_img2;
            case 2:
                return R.mipmap.default_img3;
            case 3:
                return R.mipmap.default_img4;
            case 4:
                return R.mipmap.default_img5;
            case 5:
                return R.mipmap.default_img6;
            case 6:
                return R.mipmap.default_img7;
            case 7:
                return R.mipmap.default_img8;
            case 8:
                return R.mipmap.default_img9;
            case 9:
                return R.mipmap.default_img10;
        }
        return R.mipmap.default_img1;
    }

    // get random number
    public static int getRandomNumber() {
        Random rand = new Random();
        int value = rand.nextInt(50);
        return value;
    }
}
