package com.usersserve.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DateRequestDto {
    private Date startDate;
    private Date endDate;
}
