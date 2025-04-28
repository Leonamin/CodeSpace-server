package com.cuteshrew.codespace.codespace.dto.codepiece;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodePieceCreateReq {
    private Long spaceId;
    private String name;
    private String description;
    private String language;
    private String code;
    private String password;
    private String ownerName;
}
