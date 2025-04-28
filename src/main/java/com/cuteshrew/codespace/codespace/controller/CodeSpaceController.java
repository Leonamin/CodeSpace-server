package com.cuteshrew.codespace.codespace.controller;

import com.cuteshrew.codespace.codespace.dto.codespace.CodeSpaceCreateReq;
import com.cuteshrew.codespace.codespace.dto.codespace.CodeSpaceDeleteReq;
import com.cuteshrew.codespace.codespace.dto.codespace.CodeSpaceSummaryRes;
import com.cuteshrew.codespace.codespace.dto.codespace.CodeSpaceUpdateReq;
import com.cuteshrew.codespace.codespace.service.CodeSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/codespaces")
@RequiredArgsConstructor
public class CodeSpaceController {
    private final CodeSpaceService codeSpaceService;

    @PostMapping
    public void createCodeSpace(@RequestBody CodeSpaceCreateReq req) {
        codeSpaceService.createCodeSpace(req);
    }

    @PutMapping("/{id}")
    public void updateCodeSpace(@PathVariable Long id, @RequestBody CodeSpaceUpdateReq req) {
        codeSpaceService.updateCodeSpace(id, req);
    }

    @DeleteMapping("/{id}")
    public void deleteCodeSpace(@PathVariable Long id, @RequestBody CodeSpaceDeleteReq req) {
        codeSpaceService.deleteCodeSpace(id, req.getPassword());
    }

    @GetMapping("/{id}")
    public CodeSpaceSummaryRes getCodeSpaceById(@PathVariable Long id) {
        return codeSpaceService.getCodeSpaceById(id);
    }

    @GetMapping
    public Page<CodeSpaceSummaryRes> getCodeSpaceList(Pageable pageable) {
        return codeSpaceService.getAllCodeSpaces(pageable);
    }
}
