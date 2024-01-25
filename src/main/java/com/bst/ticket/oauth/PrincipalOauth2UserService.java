package com.bst.ticket.oauth;

import com.bst.ticket.auth.PrincipalDetails;
import com.bst.ticket.dao.impl.LoginDaoImpl;
import com.bst.ticket.dao.impl.RegistDaoImpl;
import com.bst.ticket.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    Logger logger = LoggerFactory.getLogger(PrincipalOauth2UserService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LoginDaoImpl loginDaoImpl;

    @Autowired
    private RegistDaoImpl registDaoImpl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String mbr_provider = userRequest.getClientRegistration().getRegistrationId();
        String mbr_providerId = oAuth2User.getAttribute("sub");
        String mbr_pwd = bCryptPasswordEncoder.encode("비밀번호가 필요해");
        String mbr_id = mbr_provider + "_" + mbr_providerId;
        String mbr_nm = "이름자리";
        LocalDate mbr_birthdate =LocalDate.now();
        String mbr_phone= "010-1234-1234";
        String mbr_address = "addresssssssssss";
        String mbr_postalcode = "12345";
        String mbr_email = oAuth2User.getAttribute("email");
        String mbr_role = "ROLE_USER";
        int team_id=10;

        MemberVO mEntity = null;
        try {
            mEntity = loginDaoImpl.login(mbr_id);
            logger.info("mEntity" + mEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (mEntity == null) { // 반환 받은 값이 없다 = username이 없으니까 강제 회원가입 처리
            try {
            mEntity = MemberVO.builder()
                    .mbr_id(mbr_id)
                    .mbr_pwd(mbr_pwd)
                    .mbr_email(mbr_email)
                    .mbr_role(mbr_role)
                    .mbr_nm(mbr_nm)
                    .mbr_birthdate(mbr_birthdate)
                    .mbr_phone(mbr_phone)
                    .mbr_address(mbr_address)
                    .mbr_postalcode(mbr_postalcode)
                    .team_id(team_id)
                    .mbr_provider(mbr_provider)
                    .mbr_providerId(mbr_providerId)
                    .build();
                registDaoImpl.regist(mEntity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("구글 로그인 한 적 있음");
        }

        return new PrincipalDetails(mEntity, oAuth2User.getAttributes());
    }
}