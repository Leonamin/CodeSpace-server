package com.cuteshrew.codespace.dto.codespace;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeSpaceDeleteReq {
    private String password;
}
