package com.cuteshrew.codespace.service;

import com.cuteshrew.codespace.dto.codepiece.CodePieceCreateReq;
import com.cuteshrew.codespace.dto.codepiece.CodePieceDetailRes;
import com.cuteshrew.codespace.dto.codepiece.CodePieceSummaryRes;
import com.cuteshrew.codespace.dto.codepiece.CodePieceUpdateReq;
import com.cuteshrew.codespace.entity.CodePieceEntity;
import com.cuteshrew.codespace.repository.CodePieceRepository;
import com.cuteshrew.codespace.util.PasswordUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CodePieceService {
    final CodePieceRepository codePieceRepository;
    final CodeSpaceService codeSpaceService;

    public CodePieceService(
            CodePieceRepository codePieceRepository,
            CodeSpaceService codeSpaceService
    ) {
        this.codePieceRepository = codePieceRepository;
        this.codeSpaceService = codeSpaceService;
    }

    private CodePieceEntity getCodePieceEntity(Long id) {
        return codePieceRepository.findById(id).orElse(null);
    }

    private boolean isCodeSpaceExist(Long spaceId) {
        return codeSpaceService.getCodeSpaceById(spaceId) != null;
    }

    public void createCodePiece(CodePieceCreateReq req) {
        if (!isCodeSpaceExist(req.getSpaceId())) {
            throw new IllegalArgumentException("Code space not found");
        }

        final String hashedPassword = PasswordUtil.hashPassword(req.getPassword());

        final CodePieceEntity codePieceEntity = new CodePieceEntity();
        codePieceEntity.setSpaceId(req.getSpaceId());
        codePieceEntity.setName(req.getName());
        codePieceEntity.setDescription(req.getDescription());
        codePieceEntity.setLanguage(req.getLanguage());
        codePieceEntity.setCode(req.getCode());
        codePieceEntity.setPasswordHash(hashedPassword);
        codePieceEntity.setOwnerName(req.getOwnerName());

        final LocalDateTime now = LocalDateTime.now();
        codePieceEntity.setCreatedAt(now);
        codePieceEntity.setUpdatedAt(now);

        codePieceRepository.save(codePieceEntity);
    }

    public void updateCodePiece(Long id, CodePieceUpdateReq req) {
        final CodePieceEntity originCodePiece = getCodePieceEntity(id);

        if (originCodePiece == null) {
            throw new IllegalArgumentException("Code piece not found");
        }

        final boolean isVerified = PasswordUtil.verifyPassword(req.getPassword(), originCodePiece.getPasswordHash());

        if (!isVerified) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (req.getName() != null) {
            originCodePiece.setName(req.getName());
        }

        if (req.getDescription() != null) {
            originCodePiece.setDescription(req.getDescription());
        }

        if (req.getLanguage() != null) {
            originCodePiece.setLanguage(req.getLanguage());
        }

        if (req.getCode() != null) {
            originCodePiece.setCode(req.getCode());
        }

        if (req.getOwnerName() != null) {
            originCodePiece.setOwnerName(req.getOwnerName());
        }

        final LocalDateTime now = LocalDateTime.now();
        originCodePiece.setUpdatedAt(now);

        codePieceRepository.save(originCodePiece);
    }

    public void deleteCodePiece(Long id, String password) {
        final CodePieceEntity originCodePiece = codePieceRepository.findById(id).orElse(null);

        if (originCodePiece == null) {
            throw new IllegalArgumentException("Code piece not found");
        }

        final boolean isVerified = PasswordUtil.verifyPassword(password, originCodePiece.getPasswordHash());

        if (!isVerified) {
            throw new IllegalArgumentException("Invalid password");
        }

        final LocalDateTime now = LocalDateTime.now();
        originCodePiece.setDeletedAt(now);

        codePieceRepository.save(originCodePiece);
    }

    public CodePieceDetailRes getCodePieceById(Long id) {
        final CodePieceEntity codePieceEntity = getCodePieceEntity(id);
        if (codePieceEntity == null) {
            throw new IllegalArgumentException("Code piece not found");
        }

        return CodePieceDetailRes.fromEntity(codePieceEntity);
    }

    public Page<CodePieceSummaryRes> getAllCodePieces(Long spaceId, Pageable pageable) {
        return codePieceRepository.findAllBySpaceIdAndDeletedAtIsNull(spaceId, pageable).map(CodePieceSummaryRes::fromEntity);
    }
}
