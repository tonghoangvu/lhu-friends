package com.tonghoangvu.lhufriends.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
public class UpsertDto {
    private long time;
    @Valid
    private List<StudentDto> data;
    private List<String> skipped;
}
