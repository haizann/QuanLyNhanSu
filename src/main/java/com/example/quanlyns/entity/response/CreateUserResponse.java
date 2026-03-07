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
public class CreateUserResponse {

    private Long id;
    private String name;
    private String email;
    private int age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    private String createdBy;

    public CreateUserResponse(Long id, String name, String email, int age, GenderEnum gender, String address,
            Instant createdAt, String createdBy) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

}
