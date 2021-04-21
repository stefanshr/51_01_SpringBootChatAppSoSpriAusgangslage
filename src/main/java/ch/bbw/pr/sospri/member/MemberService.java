package ch.bbw.pr.sospri.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Locale;

/**
 * MemberService
 *
 * @author Peter Rutschmann
 * @version 09.04.2020
 * <p>
 * https://www.baeldung.com/transaction-configuration-with-jpa-and-spring
 * https://reflectoring.io/spring-security-password-handli
 */
@Service
@Transactional
public class MemberService implements UserDetailsService {
    @Autowired
    private MemberRepository repository;

    public Iterable<Member> getAll() {
        return repository.findAll();
    }

    public void add(Member member) {
        repository.save(member);
    }

    public void update(Long id, Member member) {
        //save geht auch für update.
        repository.save(member);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Member getById(Long id) {
        Iterable<Member> memberitr = repository.findAll();

        for (Member member : memberitr) {
            if (member.getId() == id) {
                return member;
            }
        }
        System.out.println("MemberService:getById(), id does not exist in repository: " + id);
        return null;
    }

    public Member getByUserName(String username) {
        Iterable<Member> memberitr = repository.findAll();

        for (Member member : memberitr) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }
        System.out.println("MemberService:getByUserName(), username does not exist in repository: " + username);
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = getByUserName(username);
        return MemberToUserDetailsMapper.toUserDetails(member);
    }

    public boolean RegisterUser(RegisterMember member) {

        Member newMember = new Member();
        newMember.setPrename(member.getPrename());
        newMember.setLastname(member.getLastname());
        String pepper = "pepper";
        BCryptPasswordEncoder bCPE = new BCryptPasswordEncoder(10, new SecureRandom());

        newMember.setPassword(bCPE.encode(member.getPassword()+pepper));
        newMember.setUsername(member.getPrename().toLowerCase() + "." + member.getLastname().toLowerCase());
        newMember.setAuthority("member");
        for (Member m : repository.findAll()) {
            if (!newMember.getUsername().equals(m.getUsername())) {
                System.out.println("Saved member: " + newMember.toString());
                repository.save(newMember);
            } else {
                System.out.println("Error Member already exists");
                return false;
            }

        }
        return true;


    }
}
