package com.cuteshrew.codespace.codespace.controller;

import com.cuteshrew.codespace.codespace.dto.codepiece.CodePieceCreateReq;
import com.cuteshrew.codespace.codespace.dto.codepiece.CodePieceDetailRes;
import com.cuteshrew.codespace.codespace.dto.codepiece.CodePieceSummaryRes;
import com.cuteshrew.codespace.codespace.dto.codepiece.CodePieceUpdateReq;
import com.cuteshrew.codespace.codespace.service.CodePieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/codepieces")
@RequiredArgsConstructor
public class CodePieceController {
    private final CodePieceService codePieceService;

    @PostMapping
    public void createCodePiece(@RequestBody CodePieceCreateReq req) {
        codePieceService.createCodePiece(req);
    }

    @PutMapping("/{id}")
    public void updateCodePiece(@PathVariable Long id, @RequestBody CodePieceUpdateReq req) {
        codePieceService.updateCodePiece(id, req);
    }

    @DeleteMapping("/{id}")
    public void deleteCodePiece(@PathVariable Long id, @RequestBody String password) {
        codePieceService.deleteCodePiece(id, password);
    }

    @GetMapping("/{id}")
    public CodePieceDetailRes getCodePieceById(@PathVariable Long id) {
        return codePieceService.getCodePieceById(id);
    }

    @GetMapping
    public Page<CodePieceSummaryRes> getAllCodePieces(@RequestParam Long spaceId, Pageable pageable) {
        return codePieceService.getAllCodePieces(spaceId, pageable);
    }

}
