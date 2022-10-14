package backend.backend.channelmanagement.service.management.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaxDiscountPercent {
    MAX_DISCOUNT_PERCENT(10);

    private final int maxDiscountPercent;
}
