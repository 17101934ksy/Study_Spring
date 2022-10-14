package backend.backend.channelmanagement.service.management.service;

import backend.backend.Member.domain.Member;
import backend.backend.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static backend.backend.channelmanagement.service.management.domain.MaxDiscountFee.MAX_DISCOUNT_FEE;
import static backend.backend.channelmanagement.service.management.domain.MaxDiscountPercent.MAX_DISCOUNT_PERCENT;

@Service
@RequiredArgsConstructor
public class ManagementDiscountPolicyImpl implements ManagementDiscountPolicy{

    private final MemberRepository memberRepository;

    @Override
    public void updateDiscountFee(Member member) {
        int maxDiscountFee = MAX_DISCOUNT_FEE.getMaxDiscountFee();
        int maxDiscountPercent = MAX_DISCOUNT_PERCENT.getMaxDiscountPercent();

        switch (member.getMembership()) {
            case PROPLUS:
                if (member.getTotalFee() >= 30000) {

                    if (((member.getManagementFee() - member.getNextMonthExpectedDiscount()) -
                            ((member.getManagementFee() - member.getNextMonthExpectedDiscount()) / maxDiscountPercent)) <= maxDiscountFee) {

                        member.updateNextMonthExpectedDiscount(((member.getManagementFee() - member.getNextMonthExpectedDiscount()) -
                                ((member.getManagementFee() - member.getNextMonthExpectedDiscount()) / maxDiscountPercent)));
                    };
                }

                memberRepository.save(member);
                break;
            default:
                break;
        }
    }
}

