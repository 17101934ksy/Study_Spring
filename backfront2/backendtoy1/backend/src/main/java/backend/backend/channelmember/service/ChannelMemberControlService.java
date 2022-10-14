package backend.backend.channelmember.service;

public interface ChannelMemberControlService {

    public void kickOutChannelMember(); // 매니저 / 호스트 -> 매니저 호스트 X

    public void informNotice(); // 매니저 / 호스트

    public void giveWarn(); // 매니저 / 호스트
}
