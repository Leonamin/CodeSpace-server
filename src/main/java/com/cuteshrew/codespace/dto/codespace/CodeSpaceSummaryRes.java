package com.cuteshrew.codespace.dto.codespace;

import com.cuteshrew.codespace.entity.CodeSpaceEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CodeSpaceSummaryRes {
    private Long id;
    private String name;
    private String description;
    private String ownerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CodeSpaceSummaryRes fromEntity(CodeSpaceEntity entity) {
        return CodeSpaceSummaryRes.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .ownerName(entity.getOwnerName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
