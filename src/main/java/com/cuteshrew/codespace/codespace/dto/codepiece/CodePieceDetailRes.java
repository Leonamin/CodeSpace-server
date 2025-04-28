package com.cuteshrew.codespace.codespace.dto.codepiece;

import com.cuteshrew.codespace.codespace.entity.CodePieceEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CodePieceDetailRes {
    private Long id;
    private Long spaceId;
    private String name;
    private String description;
    private String language;
    private String code;
    private String ownerName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CodePieceDetailRes fromEntity(CodePieceEntity entity) {
        return CodePieceDetailRes.builder()
                .id(entity.getId())
                .spaceId(entity.getSpaceId())
                .name(entity.getName())
                .description(entity.getDescription())
                .language(entity.getLanguage())
                .code(entity.getCode())
                .ownerName(entity.getOwnerName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
