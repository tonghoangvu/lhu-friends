package com.tonghoangvu.lhufriends.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpsertResponse {
    private final long totalTime;
    private final long upsertedCount;
}
