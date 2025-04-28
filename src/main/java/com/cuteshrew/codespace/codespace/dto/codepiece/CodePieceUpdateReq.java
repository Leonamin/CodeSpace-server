package com.cuteshrew.codespace.codespace.dto.codepiece;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodePieceUpdateReq {
    private Long id;
    private String name;
    private String description;
    private String language;
    private String code;
    private String password;
    private String ownerName;
}
