package com.example.getphotolibrary.addphoto;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static com.example.getphotolibrary.addphoto.AddPhotoBottomSheetButtons.FROM_GALLERY;
import static com.example.getphotolibrary.addphoto.AddPhotoBottomSheetButtons.REMOVE_PHOTO;
import static com.example.getphotolibrary.addphoto.AddPhotoBottomSheetButtons.USE_CAMERA;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/*
 * Created by 849501 on 10/20/2017.
 */
@Retention(SOURCE)
@StringDef({USE_CAMERA, FROM_GALLERY, REMOVE_PHOTO})
public @interface AddPhotoBottomSheetButtons {

    String USE_CAMERA = "use_camera";

    String FROM_GALLERY = "from_gallery";

    String REMOVE_PHOTO = "remove_photo";

}
