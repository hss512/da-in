package com.dain.service;

import com.dain.domain.dto.MemberDto;
import com.dain.domain.entity.Member;
import com.dain.principal.UserDetailsImpl;
import com.dain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long createUser (MemberDto dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        log.info("왜안됌={}",dto.getPassword());
        dto.setRole("ROLE_USER");

        log.info(dto);
        return memberRepository.save(dto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).get();

        return new UserDetailsImpl(member);
    }

    @Transactional
    public ResponseEntity<?> checkexistnickname(String nickname){
        Optional<Member> findNickname = memberRepository.findByNickname(nickname);

        if(!findNickname.isPresent()){
            return new ResponseEntity<>(1, HttpStatus.OK);//커밋용 주석
        }else {
            return new ResponseEntity<>(0, HttpStatus.OK);//커밋용 주석
        }
    }
}//커밋용주석
