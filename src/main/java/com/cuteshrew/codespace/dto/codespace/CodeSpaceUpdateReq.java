package com.cuteshrew.codespace.dto.codespace;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeSpaceUpdateReq {
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Size(min = 4, max = 16, message = "Password must be between 4 and 16 characters")
    private String password;

    @Size(min = 1, max = 20, message = "OwnerName must be between 1 and 20 characters")
    private String ownerName;

    @Size(max = 10000, message = "Description cannot be more than 1000 characters")
    private String description;
}
