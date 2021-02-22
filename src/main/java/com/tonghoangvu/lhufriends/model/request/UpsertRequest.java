package com.tonghoangvu.lhufriends.model.request;

import com.tonghoangvu.lhufriends.model.StudentItem;
import lombok.Getter;

import javax.validation.Valid;
import java.util.List;

@Getter
public class UpsertRequest {
    @Valid
    private List<StudentItem> data;

    private long time;
    private List<String> skipped;
}
