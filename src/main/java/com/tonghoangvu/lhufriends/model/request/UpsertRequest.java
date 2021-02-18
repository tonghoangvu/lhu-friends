package com.tonghoangvu.lhufriends.model.request;

import com.tonghoangvu.lhufriends.dto.request.UpsertRequestDto;
import com.tonghoangvu.lhufriends.entity.Student;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UpsertRequest {
    private final long time;
    private final List<String> skipped;
    private List<Student> data;

    @Contract(pure = true)
    public UpsertRequest(@NotNull UpsertRequestDto dto) {
        if (dto.getData() != null)
            this.data = dto.getData().stream()
                    .map(Student::new)
                    .collect(Collectors.toList());
        this.time = dto.getTime();
        this.skipped = dto.getSkipped();
    }
}
