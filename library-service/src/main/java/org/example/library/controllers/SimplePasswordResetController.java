package org.example.library.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.security.SimplePasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SimplePasswordResetController {

    private final SimplePasswordResetService passwordResetService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, 
                                        RedirectAttributes redirectAttributes) {
        boolean success = passwordResetService.requestPasswordReset(email);
        
        if (success) {
            redirectAttributes.addFlashAttribute("message", 
                "Если пользователь с таким email существует, на него будет отправлена ссылка для восстановления пароля.");
        } else {
            redirectAttributes.addFlashAttribute("message", 
                "Если пользователь с таким email существует, на него будет отправлена ссылка для восстановления пароля.");
        }
        
        return "redirect:/forgot-password";
    }

    @GetMapping("/password-reset")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        if (!passwordResetService.isValidResetToken(token)) {
            model.addAttribute("error", "Недействительная или истекшая ссылка для восстановления пароля.");
            return "reset-password-error";
        }
        
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/password-reset")
    public String processResetPassword(@RequestParam String token,
                                       @RequestParam String password,
                                       @RequestParam String confirmPassword,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        
        if (password == null || password.length() < 6) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Пароль должен содержать минимум 6 символов.");
            return "reset-password";
        }
        
        if (!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Пароли не совпадают.");
            return "reset-password";
        }

        boolean success = passwordResetService.resetPassword(token, password);
        
        if (success) {
            redirectAttributes.addFlashAttribute("message", 
                "Пароль успешно изменен. Теперь вы можете войти с новым паролем.");
            return "redirect:/login";
        } else {
            model.addAttribute("token", token);
            model.addAttribute("error", "Не удалось сбросить пароль. Попробуйте еще раз.");
            return "reset-password";
        }
    }
}