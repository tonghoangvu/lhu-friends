package com.tonghoangvu.lhufriends.dto.response;

import com.tonghoangvu.lhufriends.model.response.UpsertResponse;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
public class UpsertResponseDto {
    private final long totalTime;
    private final long upsertedCount;

    @Contract(pure = true)
    public UpsertResponseDto(@NotNull UpsertResponse model) {
        this.totalTime = model.getTotalTime();
        this.upsertedCount = model.getUpsertedCount();
    }
}
