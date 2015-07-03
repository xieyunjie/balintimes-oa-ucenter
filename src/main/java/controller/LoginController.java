package controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import base.BaseController;

@Controller
public class LoginController extends BaseController
{
	@RequestMapping("login")
	public String loginView(Model model)
	{
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() == true)
		{
			return "redirect:/home";
		}

		return "login";
	}

	@RequestMapping("login/submit")
	public String loginSubmit(String username, String password, String rememberme, Model model, RedirectAttributes redirectAttributes)
	{
		try
		{
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					password);
			if ("true".equals(rememberme))
			{
				token.setRememberMe(true);
			}
			subject.login(token);
			if (subject.isAuthenticated())
			{
				return "redirect:/home";
			}
		}
		catch (UnknownAccountException exception)
		{
			// TODO: handle exception
			System.out.println(exception.getMessage());

			redirectAttributes.addFlashAttribute("errormessage", this.LocalMsg("AuthenticationException"));
			return "redirect:/login";
			// return "redirect:/login/error";
		}
		catch (IncorrectCredentialsException exception)
		{
			System.out.println(exception.getMessage());
			redirectAttributes.addFlashAttribute("errormessage", this.LocalMsg("IncorrectCredentials"));
			return "redirect:/login";
			// return "redirect:/login/error";
		}
		catch (AuthenticationException exception)
		{
			System.out.println(exception.getMessage());
			redirectAttributes.addFlashAttribute("errormessage", this.LocalMsg("AuthenticationException"));
			return "redirect:/login";
			// return "redirect:/login/error";
		}
		return "redirect:/login";
		// return "redirect:/login/error";
	}

	@RequestMapping("/logout")
	public String LogOutAction()
	{
		shiro.Utils.logout();
		// Subject subject = SecurityUtils.getSubject();
		// subject.logout();
		return "redirect:/login";
	}
}
