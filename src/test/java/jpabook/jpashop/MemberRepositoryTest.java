package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberSerivce;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // rollback 자동
public class MemberRepositoryTest {


    @Autowired MemberSerivce memberSerivce;
    @Autowired MemberRepository memberRepository;

    @Autowired  EntityManager em;

    @Test
   // @Rollback(false) db 에 쿼리 들어가는 거 보고싶다.!
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberSerivce.join(member);

        //then
        em.flush(); //쿼리를 강제적으로 날림
        assertEquals(member, memberRepository.findOne(saveId));


    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원예외() throws Exception {
        //given1
        Member member1 = new Member();
        member1.setName("kim");

        //given2
        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberSerivce.join(member1);
        memberSerivce.join(member2);
//(expected = IllegalStateException.class) 이걸 사용함으로써 아래의 기능을 수행
//        try {
//            memberSerivce.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }
        //

        //then
        Assert.fail("예외가 발생해야 함.");
    }


}