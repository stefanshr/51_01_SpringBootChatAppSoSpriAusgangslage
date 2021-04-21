package ch.bbw.pr.sospri.member;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

/**
 * @author stefan.hohl
 * @version 21.04.2021
 */
public class MemberToUserDetailsMapper {
    static public UserDetails toUserDetails(Member member) {
        User user = null;

        if (member != null) {
            System.out.println("member: " + member);
            java.util.Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));
            user = new User(member.getPrename() + " " + member.getLastname()
                    , member.getPassword()
                    , true
                    , true
                    , true
                    , true
                    , authorities);

        }
        return user;
    }
}
