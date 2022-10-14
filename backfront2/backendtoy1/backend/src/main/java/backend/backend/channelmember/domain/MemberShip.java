package backend.backend.channelmember.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public enum MemberShip {
    PROPLUS(9900, 60, 600),
    PRO(7400, 30, 240),
    Free(0, 10, 30);

    private final int membershipFee;
    private final int maxSimultaneousConnection;
    private final int maxContinuousManageTime;
}
