package org.mbc.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public void loginGet(String error, String logout){
        // http://localhost/member/login?error=???
        // http://localhost/member/logout?logout=???

        log.info("MemberController.loginGet 메서드 실행...");
        log.info("logout : " + logout); // 데이터베이스에서 활용
        log.info("error: " + error); // 데이터베이스에서 활용

        if(logout != null){
            log.info("logout 처리됨!!! : " + logout);
        }


    }

}
