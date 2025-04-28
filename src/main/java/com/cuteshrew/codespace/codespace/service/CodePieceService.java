package com.cuteshrew.codespace.codespace.service;

import com.cuteshrew.codespace.codespace.dto.codepiece.CodePieceCreateReq;
import com.cuteshrew.codespace.codespace.dto.codepiece.CodePieceDetailRes;
import com.cuteshrew.codespace.codespace.dto.codepiece.CodePieceUpdateReq;
import com.cuteshrew.codespace.codespace.entity.CodePieceEntity;
import com.cuteshrew.codespace.codespace.repository.CodePieceRepository;
import com.cuteshrew.codespace.codespace.util.PasswordUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        if (req.getSpaceId() == null) {
            throw new IllegalArgumentException("spaceId is required");
        }

        if (req.getName() == null) {
            throw new IllegalArgumentException("name is required");
        }

        if (req.getLanguage() == null) {
            throw new IllegalArgumentException("language is required");
        }

        if (req.getCode() == null) {
            throw new IllegalArgumentException("code is required");
        }

        if (req.getPassword() == null) {
            throw new IllegalArgumentException("password is required");
        }

        if (req.getOwnerName() == null) {
            throw new IllegalArgumentException("ownerName is required");
        }

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

        codePieceRepository.save(codePieceEntity);
    }

    public void updateCodePiece(Long id, CodePieceUpdateReq req) {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }

        final CodePieceEntity originCodePiece = getCodePieceEntity(id);

        if (originCodePiece == null) {
            throw new IllegalArgumentException("Code piece not found");
        }

        if (req.getPassword() == null) {
            throw new IllegalArgumentException("Password is required");
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

        codePieceRepository.save(originCodePiece);
    }

    public void deleteCodePiece(Long id, String password) {
        final CodePieceEntity originCodePiece = codePieceRepository.findById(id).orElse(null);

        if (originCodePiece == null) {
            throw new IllegalArgumentException("Code piece not found");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password is required");
        }

        final boolean isVerified = PasswordUtil.verifyPassword(password, originCodePiece.getPasswordHash());

        if (!isVerified) {
            throw new IllegalArgumentException("Invalid password");
        }

        codePieceRepository.deleteById(id);
    }

    public CodePieceDetailRes getCodePieceById(Long id) {
        final CodePieceEntity codePieceEntity = codePieceRepository.findById(id).orElse(null);
        if (codePieceEntity == null) {
            throw new IllegalArgumentException("Code piece not found");
        }
        return CodePieceDetailRes.fromEntity(codePieceEntity);
    }

    public Page<CodePieceDetailRes> getAllCodePieces(Long spaceId, Pageable pageable) {
        return codePieceRepository.findAllBySpaceId(spaceId, pageable).map(CodePieceDetailRes::fromEntity);
    }
}
