package com.tonghoangvu.lhufriends.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
public class UpsertDto {
    @Valid
    private List<StudentDto> data;

    private long time;
    private List<String> skipped;
}
