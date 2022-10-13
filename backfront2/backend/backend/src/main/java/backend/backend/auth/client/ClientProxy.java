package backend.backend.auth.client;


import backend.backend.member.domain.Member;

public interface ClientProxy {

    Member getUserData(String accessToken);
}
