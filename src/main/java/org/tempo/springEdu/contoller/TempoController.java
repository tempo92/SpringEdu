package org.tempo.springEdu.contoller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tempo.springEdu.entity.User;

@RestController
@RequestMapping(path = "/api/tempo")
@RequiredArgsConstructor
public class TempoController {

    @RequestMapping("/checktoken")
    String checkToken(@AuthenticationPrincipal User user) {
        final Logger logger = LogManager.getLogger(this.getClass());
        String userNameMsg = String.format("user: %s",
                user != null ? user.getUsername() : "null");
        logger.info("checktoken; " + userNameMsg);
        return String.format("TempoController checktoken; %s", userNameMsg) ;
    }
}
