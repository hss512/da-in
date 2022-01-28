package com.dain.repository;

import com.dain.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByUsername (String username) throws UsernameNotFoundException;


    Optional<Member> findByNickname (String nickname);

    Optional<Member> findByEmail (String email);
}
