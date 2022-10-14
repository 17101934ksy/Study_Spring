package backend.backend.channelmanagement.service.management.service;

import backend.backend.Member.domain.Member;

public interface ManagementDiscountPolicy {
    /**
     * @param member
     * Member의 Membership등급과 총결제 금액에 따라 다른 할인 정책 적용
     *
     */
    void updateDiscountFee(Member member);

}
