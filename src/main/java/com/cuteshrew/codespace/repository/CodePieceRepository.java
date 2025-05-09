package com.cuteshrew.codespace.repository;

import com.cuteshrew.codespace.entity.CodePieceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodePieceRepository extends JpaRepository<CodePieceEntity, Long> {
    List<CodePieceEntity> findAllBySpaceIdAndDeletedAtIsNull(Long spaceId);

    Page<CodePieceEntity> findAllBySpaceIdAndDeletedAtIsNull(Long spaceId, Pageable pageable);

    Page<CodePieceEntity> findCodePieceEntitiesByLanguageAndDeletedAtIsNull(String language, Pageable pageable);
}
