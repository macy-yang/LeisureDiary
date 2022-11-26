package com.scujcc.leisurediary.ui.diarylist;

/**
 * diarylist中每个item的内容
 *
 * @author 杨梦婷
 * time:2022/11/17
 */
public class Recommend {
    private String name;
    private int ImageId;

    public Recommend(String name, int ImageId) {
        this.name = name;
        this.ImageId = ImageId;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}