package com.takhyungmin.dowadog.communitywrite;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.ExifInterface;

import java.io.IOException;
import java.io.InputStream;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class abc {

    @SuppressLint("NewApi")
    Bitmap fixOrientation(Bitmap bitmap, InputStream mCurrentPhotoPath) {
        ExifInterface ei = null;
        Bitmap selectedBitmap;
        try {
            ei = new ExifInterface(mCurrentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                selectedBitmap = rotateImage(bitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                selectedBitmap = rotateImage(bitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                selectedBitmap = rotateImage(bitmap, 270);
                break;
            case ExifInterface.ORIENTATION_NORMAL:
                selectedBitmap = bitmap;
                break;
            default:
                selectedBitmap = bitmap;
        }
        return selectedBitmap;
    }
}
