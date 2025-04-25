package com.cuteshrew.codespace.codespace.service;

import com.cuteshrew.codespace.codespace.entity.CodeSpaceEntity;
import com.cuteshrew.codespace.codespace.repository.CodeSpaceRepository;
import com.cuteshrew.codespace.codespace.util.PasswordUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CodeSpaceService {
    private final CodeSpaceRepository codeSpaceRepository;

    public CodeSpaceService(CodeSpaceRepository codeSpaceRepository) {
        this.codeSpaceRepository = codeSpaceRepository;
    }

    public void createCodeSpace(String name, String description, String password, String ownerName) {

        final String hashedPassword = PasswordUtil.hashPassword(password);

        final CodeSpaceEntity codeSpaceEntity = new CodeSpaceEntity();
        codeSpaceEntity.setName(name);
        codeSpaceEntity.setDescription(description);
        codeSpaceEntity.setPasswordHash(hashedPassword);
        codeSpaceEntity.setOwnerName(ownerName);

        codeSpaceRepository.save(codeSpaceEntity);
    }

    public void updateCodeSpace(Long id, String name, String description, String password, String ownerName) {
        final CodeSpaceEntity originCodeSpace = codeSpaceRepository.findById(id).orElse(null);

        if (originCodeSpace == null) {
            throw new IllegalArgumentException("Code space not found");
        }

        // TODO - hashedPassword와 전달받은 password 비교

        if (password == null) {
            throw new IllegalArgumentException("Password is required");
        }

        final boolean isVerified = PasswordUtil.verifyPassword(password, originCodeSpace.getPasswordHash());
        if (!isVerified) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (name != null) {
            originCodeSpace.setName(name);
        }

        if (description != null) {
            originCodeSpace.setDescription(description);
        }

        if (ownerName != null) {
            originCodeSpace.setOwnerName(ownerName);
        }

        codeSpaceRepository.save(originCodeSpace);
    }

    public void deleteCodeSpace(Long id, String password) {
        final CodeSpaceEntity originCodeSpace = codeSpaceRepository.findById(id).orElse(null);

        if (originCodeSpace == null) {
            throw new IllegalArgumentException("Code space not found");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password is required");
        }

        final boolean isVerified = PasswordUtil.verifyPassword(password, originCodeSpace.getPasswordHash());

        if (!isVerified) {
            throw new IllegalArgumentException("Invalid password");
        }

        codeSpaceRepository.deleteById(id);
    }

    public CodeSpaceEntity getCodeSpaceById(Long id) {
        final CodeSpaceEntity codeSpaceEntity = codeSpaceRepository.findById(id).orElse(null);

        if (codeSpaceEntity == null) {
            throw new IllegalArgumentException("Code space not found");
        }

        return codeSpaceEntity;
    }

    public Page<CodeSpaceEntity> getAllCodeSpaces(Pageable pageable) {
        return codeSpaceRepository.findAll(pageable);
    }
}
