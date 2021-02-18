package com.tonghoangvu.lhufriends.dto.request;

import com.tonghoangvu.lhufriends.dto.StudentDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
public class UpsertRequestDto {
    @Valid
    private List<StudentDto> data;

    private long time;
    private List<String> skipped;
}
