package com.cuteshrew.codespace.codespace.service;

import com.cuteshrew.codespace.codespace.entity.CodePieceEntity;
import com.cuteshrew.codespace.codespace.repository.CodePieceRepository;
import com.cuteshrew.codespace.codespace.util.PasswordUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CodePieceService {
    final CodePieceRepository codePieceRepository;

    public CodePieceService(CodePieceRepository codePieceRepository) {
        this.codePieceRepository = codePieceRepository;
    }

    public void createCodePiece(
            Long spaceId, String name, String description, String language, String code, String password, String ownerName
    ) {
        if (spaceId == null) {
            throw new IllegalArgumentException("spaceId is required");
        }

        if (name == null) {
            throw new IllegalArgumentException("name is required");
        }

        if (language == null) {
            throw new IllegalArgumentException("language is required");
        }

        if (code == null) {
            throw new IllegalArgumentException("code is required");
        }

        if (password == null) {
            throw new IllegalArgumentException("password is required");
        }

        if (ownerName == null) {
            throw new IllegalArgumentException("ownerName is required");
        }

        final String hashedPassword = PasswordUtil.hashPassword(password);

        final CodePieceEntity codePieceEntity = new CodePieceEntity();
        codePieceEntity.setSpaceId(spaceId);
        codePieceEntity.setName(name);
        codePieceEntity.setDescription(description);
        codePieceEntity.setLanguage(language);
        codePieceEntity.setCode(code);
        codePieceEntity.setPasswordHash(hashedPassword);
        codePieceEntity.setOwnerName(ownerName);

        codePieceRepository.save(codePieceEntity);
    }

    public void updateCodePiece(Long id, String name, String description, String language, String code, String password, String ownerName) {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }

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

        if (name != null) {
            originCodePiece.setName(name);
        }

        if (description != null) {
            originCodePiece.setDescription(description);
        }

        if (language != null) {
            originCodePiece.setLanguage(language);
        }

        if (code != null) {
            originCodePiece.setCode(code);
        }

        if (ownerName != null) {
            originCodePiece.setOwnerName(ownerName);
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

    public CodePieceEntity getCodePieceById(Long id) {
        final CodePieceEntity codePieceEntity = codePieceRepository.findById(id).orElse(null);
        if (codePieceEntity == null) {
            throw new IllegalArgumentException("Code piece not found");
        }
        return codePieceEntity;
    }

    public Page<CodePieceEntity> getAllCodePieces(Long spaceId, Pageable pageable) {
        return codePieceRepository.findAllBySpaceId(spaceId, pageable);
    }
}
