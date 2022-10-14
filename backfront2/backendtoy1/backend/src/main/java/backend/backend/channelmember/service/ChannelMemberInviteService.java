package backend.backend.channelmember.service;

public interface ChannelMemberInviteService {

    public void inviteChannelMember(); // 매니저 / 호스트

    public void sendInviteLink(); // 모든 멤버의 정책 가능

    public void acceptComeInChannel(); // 호스트만

}
