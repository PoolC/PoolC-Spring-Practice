package com.poolc.springproject.poolcreborn.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserHoursDto {
    private String username;
    private String name;
    private String major;
    private int studentId;
    private int totalHours;
    private boolean isAdmin;

}