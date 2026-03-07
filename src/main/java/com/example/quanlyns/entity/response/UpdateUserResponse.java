package com.example.quanlyns.entity.response;

import java.time.Instant;

import com.example.quanlyns.util.constant.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserResponse {
    private Long id;
    private String name;
    private String email;
    private int age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
    private String updatedBy;

    public UpdateUserResponse(Long id, String name, String email, int age, GenderEnum gender, String address,
            Instant updatedAt, String updatedBy) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

}
