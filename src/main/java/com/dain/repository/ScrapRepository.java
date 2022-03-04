package com.dain.repository;

import com.dain.domain.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Scrap findScrapByMemberIdAndBoardId(Long userId, Long boardId);
}
