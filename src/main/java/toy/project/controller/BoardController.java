package toy.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/notice")
    public String notice() {

        return "board/notice";
    }

    @GetMapping("/qna")
    public String qna() {

        return "board/qna";
    }

    @GetMapping("/review")
    public String review() {

        return "board/review";
    }

}
