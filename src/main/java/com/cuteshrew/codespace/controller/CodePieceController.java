package com.cuteshrew.codespace.controller;

import com.cuteshrew.codespace.dto.codepiece.*;
import com.cuteshrew.codespace.service.CodePieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/codepieces")
@RequiredArgsConstructor
public class CodePieceController {
    private final CodePieceService codePieceService;

    @PostMapping
    public Map<Object, Object> createCodePiece(@RequestBody @Validated CodePieceCreateReq req) {
        codePieceService.createCodePiece(req);

        return Collections.emptyMap();
    }

    @PutMapping("/{id}")
    public Map<Object, Object> updateCodePiece(@PathVariable Long id, @RequestBody @Validated CodePieceUpdateReq req) {
        if (id == null) {
            throw new IllegalArgumentException("Code piece id not found");
        }

        codePieceService.updateCodePiece(id, req);

        return Collections.emptyMap();
    }

    @DeleteMapping("/{id}")
    public Map<Object, Object> deleteCodePiece(@PathVariable Long id, @RequestBody @Validated CodePieceDeleteReq req) {
        if (id == null) {
            throw new IllegalArgumentException("Code piece id not found");
        }

        codePieceService.deleteCodePiece(id, req.getPassword());

        return Collections.emptyMap();
    }

    @GetMapping("/{id}")
    public CodePieceDetailRes getCodePieceById(@PathVariable Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Code piece id not found");
        }

        return codePieceService.getCodePieceById(id);
    }

    @GetMapping
    public List<CodePieceSummaryRes> getAllCodePieces(@RequestParam(name = "space_id") Long spaceId, Pageable pageable) {
        return codePieceService.getAllCodePieces(spaceId, pageable).getContent();
    }

}
