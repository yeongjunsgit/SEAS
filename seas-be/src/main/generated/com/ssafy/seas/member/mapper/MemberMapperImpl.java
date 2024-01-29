package com.ssafy.seas.member.mapper;

import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-29T10:45:18+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member MemberDtoToMember(MemberDto.Post memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( memberDto.getMemberId() );
        member.setPassword( memberDto.getPassword() );
        member.setNickname( memberDto.getNickname() );
        member.setEmail( memberDto.getEmail() );

        return member;
    }
}
