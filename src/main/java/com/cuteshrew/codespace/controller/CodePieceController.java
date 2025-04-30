package com.cuteshrew.codespace.controller;

import com.cuteshrew.codespace.dto.codepiece.*;
import com.cuteshrew.codespace.service.CodePieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/codepieces")
@RequiredArgsConstructor
public class CodePieceController {
    private final CodePieceService codePieceService;

    @PostMapping
    public void createCodePiece(@RequestBody @Validated CodePieceCreateReq req) {
        codePieceService.createCodePiece(req);
    }

    @PutMapping("/{id}")
    public void updateCodePiece(@PathVariable Long id, @RequestBody @Validated CodePieceUpdateReq req) {
        if (id == null) {
            throw new IllegalArgumentException("Code piece id not found");
        }

        codePieceService.updateCodePiece(id, req);
    }

    @DeleteMapping("/{id}")
    public void deleteCodePiece(@PathVariable Long id, @RequestBody @Validated CodePieceDeleteReq req) {
        if (id == null) {
            throw new IllegalArgumentException("Code piece id not found");
        }

        codePieceService.deleteCodePiece(id, req.getPassword());
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
