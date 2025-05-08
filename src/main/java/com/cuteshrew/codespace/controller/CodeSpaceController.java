package com.cuteshrew.codespace.controller;

import com.cuteshrew.codespace.dto.codespace.CodeSpaceCreateReq;
import com.cuteshrew.codespace.dto.codespace.CodeSpaceDeleteReq;
import com.cuteshrew.codespace.dto.codespace.CodeSpaceSummaryRes;
import com.cuteshrew.codespace.dto.codespace.CodeSpaceUpdateReq;
import com.cuteshrew.codespace.service.CodeSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/codespaces")
@RequiredArgsConstructor
public class CodeSpaceController {
    private final CodeSpaceService codeSpaceService;

    @PostMapping
    public Map<Object, Object> createCodeSpace(@RequestBody @Validated CodeSpaceCreateReq req) {
        codeSpaceService.createCodeSpace(req);

        return Collections.emptyMap();
    }

    @PutMapping("/{id}")
    public Map<Object, Object> updateCodeSpace(@PathVariable Long id, @RequestBody @Validated CodeSpaceUpdateReq req) {
        if (id == null) {
            throw new IllegalArgumentException("Code piece id not found");
        }

        codeSpaceService.updateCodeSpace(id, req);

        return Collections.emptyMap();
    }

    @DeleteMapping("/{id}")
    public Map<Object, Object> deleteCodeSpace(@PathVariable Long id, @RequestBody @Validated CodeSpaceDeleteReq req) {
        if (id == null) {
            throw new IllegalArgumentException("Code piece id not found");
        }

        codeSpaceService.deleteCodeSpace(id, req.getPassword());

        return Collections.emptyMap();
    }

    @GetMapping("/{id}")
    public CodeSpaceSummaryRes getCodeSpaceById(@PathVariable Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Code piece id not found");
        }

        return codeSpaceService.getCodeSpaceById(id);
    }

    @GetMapping
    public List<CodeSpaceSummaryRes> getCodeSpaceList(Pageable pageable) {
        return codeSpaceService.getAllCodeSpaces(pageable).getContent();
    }
}
