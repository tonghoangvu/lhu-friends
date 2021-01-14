package com.tonghoangvu.lhufriends.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UpsertModel {
    private long time;
    private List<String> skipped;
}
