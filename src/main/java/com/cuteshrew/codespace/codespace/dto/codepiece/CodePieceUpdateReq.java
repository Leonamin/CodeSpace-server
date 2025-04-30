package com.cuteshrew.codespace.codespace.dto.codepiece;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodePieceUpdateReq {
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot be more than 1000 characters")
    private String description;

    @Size(min = 3, max = 100, message = "Language must be between 3 and 100 characters")
    private String language;

    @Size(max = 10000, message = "Code must under 10000 characters")
    private String code;

    @Size(min = 4, max = 16, message = "Password must be between 4 and 16 characters")
    private String password;

    @Size(min = 1, max = 20, message = "OwnerName must be between 1 and 20 characters")
    private String ownerName;
}
