package backend.backend.channelmanagement.service.management.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaxDiscountFee {
    MAX_DISCOUNT_FEE(3000);

    private final int maxDiscountFee;
}
