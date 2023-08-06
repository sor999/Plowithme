package com.example.Plowithme.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Profile {
    private String uploadFileName; //고객 업로드 파일명
    private String storeFileName; //서버 관리 파일명
    public Profile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
