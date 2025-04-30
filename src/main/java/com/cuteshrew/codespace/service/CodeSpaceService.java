package com.cuteshrew.codespace.service;

import com.cuteshrew.codespace.dto.codespace.CodeSpaceCreateReq;
import com.cuteshrew.codespace.dto.codespace.CodeSpaceSummaryRes;
import com.cuteshrew.codespace.dto.codespace.CodeSpaceUpdateReq;
import com.cuteshrew.codespace.entity.CodeSpaceEntity;
import com.cuteshrew.codespace.repository.CodeSpaceRepository;
import com.cuteshrew.codespace.util.PasswordUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CodeSpaceService {
    private final CodeSpaceRepository codeSpaceRepository;

    public CodeSpaceService(CodeSpaceRepository codeSpaceRepository) {
        this.codeSpaceRepository = codeSpaceRepository;
    }

    private CodeSpaceEntity getCodeSpaceEntity(Long id) {
        return codeSpaceRepository.findById(id).orElse(null);
    }

    public void createCodeSpace(CodeSpaceCreateReq req) {
        final String hashedPassword = PasswordUtil.hashPassword(req.getPassword());

        final CodeSpaceEntity codeSpaceEntity = new CodeSpaceEntity();
        codeSpaceEntity.setName(req.getName());
        codeSpaceEntity.setDescription(req.getDescription());
        codeSpaceEntity.setPasswordHash(hashedPassword);
        codeSpaceEntity.setOwnerName(req.getOwnerName());

        codeSpaceRepository.save(codeSpaceEntity);
    }

    public void updateCodeSpace(Long id, CodeSpaceUpdateReq req) {
        final CodeSpaceEntity originCodeSpace = getCodeSpaceEntity(id);

        if (originCodeSpace == null) {
            throw new IllegalArgumentException("Code space not found");
        }

        final boolean isVerified = PasswordUtil.verifyPassword(req.getPassword(), originCodeSpace.getPasswordHash());
        if (!isVerified) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (req.getName() != null) {
            originCodeSpace.setName(req.getName());
        }

        if (req.getDescription() != null) {
            originCodeSpace.setDescription(req.getDescription());
        }

        if (req.getOwnerName() != null) {
            originCodeSpace.setOwnerName(req.getOwnerName());
        }

        codeSpaceRepository.save(originCodeSpace);
    }

    public void deleteCodeSpace(Long id, String password) {
        final CodeSpaceEntity originCodeSpace = getCodeSpaceEntity(id);

        if (originCodeSpace == null) {
            throw new IllegalArgumentException("Code space not found");
        }

        final boolean isVerified = PasswordUtil.verifyPassword(password, originCodeSpace.getPasswordHash());

        if (!isVerified) {
            throw new IllegalArgumentException("Invalid password");
        }

        codeSpaceRepository.deleteById(id);
    }

    public CodeSpaceSummaryRes getCodeSpaceById(Long id) {
        final CodeSpaceEntity codeSpaceEntity = getCodeSpaceEntity(id);
        if (codeSpaceEntity == null) {
            throw new IllegalArgumentException("Code space not found");
        }
        return CodeSpaceSummaryRes.fromEntity(codeSpaceEntity);
    }

    public Page<CodeSpaceSummaryRes> getAllCodeSpaces(Pageable pageable) {
        return codeSpaceRepository.findAll(pageable).map(CodeSpaceSummaryRes::fromEntity);
    }
}
