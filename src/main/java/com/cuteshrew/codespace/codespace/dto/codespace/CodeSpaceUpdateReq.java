package com.cuteshrew.codespace.codespace.dto.codespace;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeSpaceUpdateReq {
    private Long id;
    private String name;
    private String description;
    private String password;
    private String ownerName;
}
