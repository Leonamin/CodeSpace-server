package com.cuteshrew.codespace.dto.codepiece;

import com.cuteshrew.codespace.entity.CodePieceEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CodePieceSummaryRes {
    private Long id;
    private Long spaceId;
    private String name;
    private String description;
    private String ownerName;
    private String language;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CodePieceSummaryRes fromEntity(CodePieceEntity entity) {
        return CodePieceSummaryRes.builder()
                .id(entity.getId())
                .spaceId(entity.getSpaceId())
                .name(entity.getName())
                .description(entity.getDescription())
                .ownerName(entity.getOwnerName())
                .language(entity.getLanguage())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
