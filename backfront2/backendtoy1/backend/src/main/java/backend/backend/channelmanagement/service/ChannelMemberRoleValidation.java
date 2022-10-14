package backend.backend.channelmanagement.service;

import backend.backend.Member.domain.Member;
import backend.backend.channelmember.domain.ChannelPlatform;

public interface ChannelMemberRoleValidation {

    /**
     * @param
     * @return 해당 channelPlatform에서 member의 Role을 검증
     */
    public boolean validationRole();
}
