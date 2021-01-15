package com.tonghoangvu.lhufriends.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpsertModel {
    private long time;
    private long upserted;
}
