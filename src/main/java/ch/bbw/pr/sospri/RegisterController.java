package ch.bbw.pr.sospri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;

import java.util.Locale;

/**
 * RegisterController
 *
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
@Controller
public class RegisterController {
    @Autowired
    MemberService memberservice;

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        System.out.println("getRequestRegistMembers");
        model.addAttribute("registerMember", new RegisterMember());
        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegistMembers(RegisterMember registerMember, Model model) {
        System.out.println("postRequestRegistMembers: registerMember");
        System.out.println(registerMember);


        if (registerMember.getPassword().equals(registerMember.getConfirmation())) {
			if(registerMember.getPrename().length()<2 || registerMember.getPrename().length()>25){
				registerMember.setConfirmation("Firstname must be between 2-25 Characters");
				return "register";
			}
			if(registerMember.getLastname().length()<2 || registerMember.getLastname().length()>25){
				registerMember.setConfirmation("Lastname must be between 2-25 Characters");
				return "register";
			}
			if (!memberservice.RegisterUser(registerMember)) {
				registerMember.setConfirmation("User: " + registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase() + " already exists");
				return "register";
			}
        }else {
        	registerMember.setConfirmation("Password and confirmation dont match");
			return "register";
		}
        model.addAttribute("name", "Welcome "+registerMember.getPrename()+"!    ");
        return "registerconfirmed";
    }
}