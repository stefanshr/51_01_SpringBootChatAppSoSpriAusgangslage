package ch.bbw.pr.sospri.member;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author stefan.hohl
 * @version 21.04.2021
 */
public class MemberGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 8835903531623403993L;
    private String authority;

    public MemberGrantedAuthority(String authority) {
        super();
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "MemberGrantedAuthority [authority=" + authority + "]";
    }
}
