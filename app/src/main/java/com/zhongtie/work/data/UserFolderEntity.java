package com.zhongtie.work.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class UserFolderEntity {
    private String folderName;
    private List<UserFolderEntity> folderList;
    private List<UserFileEntity> fileList;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<UserFolderEntity> getFolderList() {
        if(folderList==null)
            folderList=new ArrayList<>();
        return folderList;
    }

    public void setFolderList(List<UserFolderEntity> folderList) {
        this.folderList = folderList;
    }

    public List<UserFileEntity> getFileList() {
        if(fileList==null)
            fileList=new ArrayList<>();
        return fileList;
    }

    public void setFileList(List<UserFileEntity> fileList) {
        this.fileList = fileList;
    }
}
