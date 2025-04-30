package com.cuteshrew.codespace.repository;

import com.cuteshrew.codespace.entity.CodeSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeSpaceRepository extends JpaRepository<CodeSpaceEntity, Long> {
    Page<CodeSpaceEntity> findAll(Pageable pageable);
}
