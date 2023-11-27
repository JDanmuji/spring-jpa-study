package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//readOnly : JPA가 조회하는 곳에는 최적화 해줌 //스프링에서 제공하는 거 사용
@RequiredArgsConstructor // Lombok : final 필드를 가지고 있는 애들을 기준으로 생성자 생성해줌.
public class MemberSerivce {

    private final MemberRepository memberRepository; //final 컴파일 시점에서 확인할 수 있음

     //의존관계를 확인할 수 있다.
    // Autowired X : 최신 스프링의 경우, 생성자가 하나 있으면 자동적으로 Autowired
//    public MemberSerivce(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }


//    @Autowired //커스터 마이징을 할 수 있다.
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional // 따로 지정해주면 이게 제일 우선순위가 됨.
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    //회원 전체 조회

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
    

}
