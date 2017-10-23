package com.example.getphotolibrary.addphoto;

/*
 * Created by 849501 on 10/20/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reusable.getphotolibrary.R;


public abstract class AddPhotoBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_photo_bottom_sheet, container,
                false);

        // get the views and attach the listener
        TextView tvBtnUseCamera = view.findViewById(R.id.tv_btn_add_photo_camera);
        TextView tvBtnFromGallery = view.findViewById(R.id.tv_btn_add_photo_gallery);
        TextView tvBtnRemovePhoto = view.findViewById(R.id.tv_btn_remove_photo);

        tvBtnUseCamera.setOnClickListener(this);
        tvBtnFromGallery.setOnClickListener(this);
        tvBtnRemovePhoto.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        int clickedViewId = v.getId();
        if (clickedViewId == R.id.tv_btn_add_photo_camera) {
            onAddPhotoBDFragmentButtonsClick(AddPhotoBottomSheetButtons.USE_CAMERA);

        } else if (clickedViewId == R.id.tv_btn_add_photo_gallery) {
            onAddPhotoBDFragmentButtonsClick(AddPhotoBottomSheetButtons.FROM_GALLERY);

        } else if (clickedViewId == R.id.tv_btn_remove_photo) {
            onAddPhotoBDFragmentButtonsClick(AddPhotoBottomSheetButtons.REMOVE_PHOTO);

        }
    }

    protected abstract void onAddPhotoBDFragmentButtonsClick(
            @AddPhotoBottomSheetButtons String button);

}
