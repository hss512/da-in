package com.dain.service;

import com.dain.domain.dto.MemberDto;
import com.dain.domain.entity.Member;
import com.dain.principal.UserDetailsImpl;
import com.dain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
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
        String email = emailCheck(dto.getEmail());
        if(email != null) {
            dto.setRole("ROLE_USER");
            dto.setLocal(dto.getSido() + "_" + dto.getGugun());
            dto.setAge(LocalDateTime.now().getYear() - dto.getYy() + 1);
            return memberRepository.save(dto.toEntity()).getId();
        }
        else{
            return 0L;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).get();

        return new UserDetailsImpl(member);
    }

    public ResponseEntity<?> checkexistnickname(String nickname){
        Optional<Member> findNickname = memberRepository.findByNickname(nickname);

        if(!findNickname.isPresent()){
            return new ResponseEntity<>(1, HttpStatus.OK);//커밋용 주석
        }else {
            return new ResponseEntity<>(0, HttpStatus.OK);//커밋용 주석
        }
    }

    public ResponseEntity<?> checkexistusername(String username) {
        Optional<Member> findUsername = memberRepository.findByUsername(username);

        if(!findUsername.isPresent()){
            return new ResponseEntity<>(1, HttpStatus.OK);//커밋용 주석
        }else {
            return new ResponseEntity<>(0, HttpStatus.OK);//커밋용 주석
        }
    }

    public String emailCheck(String email){
        if(email!=null){
            return email;
        }else {
            return null;
        }
    }

    public ResponseEntity<?> checkexistemail(String email) {
        Optional<Member> findEmail = memberRepository.findByEmail(email);

        if(!findEmail.isPresent()){
            return new ResponseEntity<>(1, HttpStatus.OK);//커밋용 주석
        }else {
            return new ResponseEntity<>(0, HttpStatus.OK);//커밋용 주석
        }
    }

    @Transactional
    public void memberUpdate(Long id,String nickname,String local){
        log.info("서비스들어옴");
        log.info("서비스의 ID={}",id);
        log.info("이거슨 서비스의 닉네임={}",nickname);
        log.info("이거슨 서비스의 지역구={}",local);
        Member member = memberRepository.findById(id).get();
        log.info("서비스에서 찾은 멤버입니다={}",member.getUsername());
        member.toUpdateMember(nickname,local);
    }

    public ResponseEntity<?> memberDeleteForm(String password,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Member> findPassword = memberRepository.findByPassword(password);
        if (findPassword.equals(userDetails.getPassword())){
            return new ResponseEntity<>(1,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(0,HttpStatus.OK);
        }
    }
}
