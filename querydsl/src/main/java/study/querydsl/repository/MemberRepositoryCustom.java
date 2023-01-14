package study.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;
import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCond cond);
    List<MemberTeamDto> searchPageSimple(MemberSearchCond cond, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberSearchCond cond, Pageable pageable);

}
