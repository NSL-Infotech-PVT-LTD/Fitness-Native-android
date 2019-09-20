package com.netscape.utrain.model;

import okhttp3.MultipartBody;

public class PortFolioImagesModel {
    int position;
    MultipartBody.Part imagePart;
    String imagePathe;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MultipartBody.Part getImagePart() {
        return imagePart;
    }

    public void setImagePart(MultipartBody.Part imagePart) {
        this.imagePart = imagePart;
    }

    public String getImagePathe() {
        return imagePathe;
    }

    public void setImagePathe(String imagePathe) {
        this.imagePathe = imagePathe;
    }
}
