package com.ll.medium.domain.home.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
@Tag(name = "AdmHomeController", description = "관리자 홈 컨트롤러")
public class AdmHomeController {
    @GetMapping("")
    @Operation(summary = "메인")
    public String showMain() {
        return "domain/home/home/adm/main";
    }

    @GetMapping("/home/about")
    @Operation(summary = "소개")
    public String showAbout() {
        return "domain/home/home/adm/about";
    }
}
