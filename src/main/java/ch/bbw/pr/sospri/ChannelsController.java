package ch.bbw.pr.sospri;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;
/**
 * ChannelsController
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
@Controller
public class ChannelsController {
	@Autowired
	MessageService messageservice;
	@Autowired
	MemberService memberservice;

	@GetMapping("/get-channel")
	public String getRequestChannel(Model model) {
		System.out.println("getRequestChannel");
		model.addAttribute("messages", messageservice.getAll());
		
		Message message = new Message();
		message.setContent("Der zweite Pfeil trifft immer.");
		System.out.println("message: " + message);
		model.addAttribute("message", message);
		return "channel";
	}

	@PostMapping("/add-message")
	public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult, HttpServletRequest request) {
		System.out.println("postRequestChannel(): message: " + message.toString());
		if(bindingResult.hasErrors()) {
			System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
			model.addAttribute("messages", messageservice.getAll());
			return "channel";
		}
		// Hack solange es kein authenticated member hat
		Principal principal = request.getUserPrincipal();
		Member tmpMember = memberservice.getByUserName(principal.getName().replace(" ",".").toLowerCase());
		message.setAuthor(tmpMember.getPrename() + " " + tmpMember.getLastname());
		message.setOrigin(new Date());
		System.out.println("message: " + message);
		messageservice.add(message);
		
		return "redirect:/get-channel";
	}
}
