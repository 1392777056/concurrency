package cn.concurrency.demo.web;

import cn.concurrency.demo.example.threadlocal.TestThreadlocal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("threadlocal")
public class ThreadlocalController {

    @RequestMapping("/test")
    @ResponseBody
    public Long getId(){
        return TestThreadlocal.get();
    }


}
