package com.ezen.view;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ezen.biz.dto.HostVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.service.AdminService;
import com.ezen.biz.service.HostService;
import com.ezen.biz.service.MemberService;
import com.ezen.biz.service.PasswordResetTokenService;

@Controller
@SessionAttributes({ "loginUser", "loginHost" })
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private HostService hostService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private PasswordResetTokenService passwordResetTokenService;

	@GetMapping("/login_form")
	public String loginView() {
		return "member/login";
	}

	@PostMapping("/login")
	public String loginAction(@Valid MemberVO vo, BindingResult bindingResult, Model model) {
		if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("pwd")) {
			model.addAttribute("message", "Please provide a valid email and password.");
			return "member/login";
		}

		int result = memberService.loginMember(vo);
		if (result == -2) {
			model.addAttribute("message", "Account temporarily locked due to repeated login failures. Try again later.");
			return "member/login";
		}

		if (result == 1) {
			model.addAttribute("loginUser", memberService.getMember(vo.getEmail()));
			return "redirect:index";
		}
		return "member/login_fail";
	}

	@PostMapping("/hostlogin")
	public String hostLoginAction(@Valid HostVO vo, BindingResult bindingResult, Model model) {
		if (bindingResult.hasFieldErrors("hemail") || bindingResult.hasFieldErrors("pwd")) {
			model.addAttribute("message", "Please provide a valid host email and password.");
			return "member/login";
		}

		String hostEmail = vo.getHemail();
		if (isAdminEmail(hostEmail)) {
			int result = adminService.loginAdmin(vo);
			if (result == 1) {
				model.addAttribute("loginAdmin", adminService.getAdmin(vo.getHemail()));
				return "redirect:admin_hostList";
			}
			return "host/login_fail";
		}

		int result = hostService.loginHost(vo);
		if (result == -2) {
			model.addAttribute("message", "Account temporarily locked due to repeated login failures. Try again later.");
			return "member/login";
		}

		HostVO host = hostService.getHost(hostEmail);
		if (result == 1 && host != null && host.getStatus() == 1) {
			model.addAttribute("loginHost", host);
			return "redirect:index";
		}
		return "host/login_fail";
	}

	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:index";
	}

	@GetMapping("/contract")
	public String contractView() {
		return "member/contract";
	}

	@PostMapping("/join_form")
	public String joinView() {
		return "member/join";
	}

	@GetMapping(value = "/email_check_form")
	public String emailCheckView(MemberVO vo, Model model) {
		int result = memberService.confirmEmail(vo.getEmail());
		model.addAttribute("email", vo.getEmail());
		model.addAttribute("message", result);
		return "member/emailcheck";
	}

	@PostMapping("/email_check_form")
	public String emailCheckAction(MemberVO vo, Model model) {
		int result = memberService.confirmEmail(vo.getEmail());
		model.addAttribute("email", vo.getEmail());
		model.addAttribute("message", result);
		return "member/emailcheck";
	}

	@GetMapping(value = "/host_email_check_form")
	public String hostEmailCheckView(HostVO vo, Model model) {
		int result = hostService.confirmEmail(vo.getHemail());
		model.addAttribute("hemail", vo.getHemail());
		model.addAttribute("message", result);
		return "member/hostemailcheck";
	}

	@PostMapping("/host_email_check_form")
	public String hostEmailCheckAction(HostVO vo, Model model) {
		int result = hostService.confirmEmail(vo.getHemail());
		model.addAttribute("hemail", vo.getHemail());
		model.addAttribute("message", result);
		return "member/hostemailcheck";
	}

	@PostMapping("/join")
	public String joinAction(@Valid MemberVO vo, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", "Please check the required fields and try again.");
			return "member/join";
		}

		memberService.insertMember(vo);
		return "member/login";
	}

	@PostMapping("/hostjoin")
	public String hostJoinAction(@Valid HostVO vo, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", "Please check the required fields and try again.");
			return "member/join";
		}

		hostService.insertHost(vo);
		return "member/login";
	}

	@GetMapping("/find_email_form")
	public String findEmailFormVIew() {
		return "member/findEmailAndPassword";
	}

	@PostMapping("/find_email")
	public String findEmailAction(MemberVO vo, Model model) {
		String email = memberService.selectEmailByNamePhone(vo);
		if (email != null) {
			model.addAttribute("message", 1);
			model.addAttribute("email", email);
		} else {
			model.addAttribute("message", -1);
		}
		return "member/findResult";
	}

	@PostMapping("/find_pwd")
	public String findPwdAction(MemberVO vo, Model model) {
		String matchedPwd = memberService.selectPwdByEmailNamePhone(vo);
		if (matchedPwd != null) {
			String token = passwordResetTokenService.issueToken("member", vo.getEmail());
			model.addAttribute("message", 1);
			model.addAttribute("email", vo.getEmail());
			model.addAttribute("token", token);
		} else {
			model.addAttribute("message", -1);
		}
		return "member/findPwdResult";
	}

	@PostMapping("/change_pwd")
	public String changePwdAction(@Valid MemberVO vo, BindingResult bindingResult,
			@RequestParam("token") String token, Model model) {
		if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("pwd")) {
			model.addAttribute("message", -1);
			model.addAttribute("email", vo.getEmail());
			model.addAttribute("token", token);
			return "member/findPwdResult";
		}

		boolean validToken = passwordResetTokenService.consumeToken(token, "member", vo.getEmail());
		if (!validToken) {
			model.addAttribute("message", -2);
			model.addAttribute("email", vo.getEmail());
			model.addAttribute("token", token);
			return "member/findPwdResult";
		}

		memberService.changePwd(vo);
		return "member/changePwdOk";
	}

	@GetMapping("/find_host_email_form")
	public String findHostEmailFormVIew() {
		return "member/findHostEmailAndPassword";
	}

	@PostMapping("/find_host_email")
	public String findHostEmailAction(HostVO vo, Model model) {
		String hemail = hostService.selectEmailByNamePhone(vo);
		if (hemail != null) {
			model.addAttribute("message", 1);
			model.addAttribute("hemail", hemail);
			model.addAttribute("email", hemail);
		} else {
			model.addAttribute("message", -1);
		}
		return "member/findResult";
	}

	@PostMapping("/find_host_pwd")
	public String findHostPwdAction(HostVO vo, Model model) {
		String matchedPwd = hostService.selectPwdByEmailNamePhone(vo);
		if (matchedPwd != null) {
			String token = passwordResetTokenService.issueToken("host", vo.getHemail());
			model.addAttribute("message", 1);
			model.addAttribute("hemail", vo.getHemail());
			model.addAttribute("token", token);
		} else {
			model.addAttribute("message", -1);
		}
		return "member/findHostPwdResult";
	}

	@PostMapping("/change_host_pwd")
	public String changeHostPwdAction(@Valid HostVO vo, BindingResult bindingResult,
			@RequestParam("token") String token, Model model) {
		if (bindingResult.hasFieldErrors("hemail") || bindingResult.hasFieldErrors("pwd")) {
			model.addAttribute("message", -1);
			model.addAttribute("hemail", vo.getHemail());
			model.addAttribute("token", token);
			return "member/findHostPwdResult";
		}

		boolean validToken = passwordResetTokenService.consumeToken(token, "host", vo.getHemail());
		if (!validToken) {
			model.addAttribute("message", -2);
			model.addAttribute("hemail", vo.getHemail());
			model.addAttribute("token", token);
			return "member/findHostPwdResult";
		}

		hostService.changePwd(vo);
		return "member/changePwdOk";
	}

	private boolean isAdminEmail(String hostEmail) {
		return "kozynest0330@gmail.com".equals(hostEmail) || "kozynest1104@gmail.com".equals(hostEmail)
				|| "kozynest0116@gmail.com".equals(hostEmail) || "kozynest0331@gmail.com".equals(hostEmail)
				|| "kozynest862@gmail.com".equals(hostEmail);
	}
}
