package com.syndicg5.controller;

import com.syndicg5.service.impl.ResidentServiceImpl;
import com.syndicg5.service.impl.SyndicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
public class SpringSessionController {
    @Autowired
    SyndicServiceImpl syndicService;

    @Autowired
    ResidentServiceImpl residentService;

    @GetMapping("/sessions")
    @ResponseBody
    public int process(HttpServletRequest request) {
        int id = Integer.parseInt(request.getSession().getAttribute("syndic").toString());
        return id;
    }
    @GetMapping("/sessions/resident")
    @ResponseBody
    public int processs(HttpServletRequest request) {
        int id = Integer.parseInt(request.getSession().getAttribute("resident").toString());
        return id;
    }
    @PostMapping("/sessions/save")
    @ResponseBody
    public int saveSession(@RequestParam("email") String email, HttpServletRequest request) {
        request.getSession(false);
        request.getSession().setAttribute("syndic", syndicService.findOneByEmail(email).getId());
        int id = Integer.parseInt(request.getSession().getAttribute("syndic").toString());
        return id;
    }
    @PostMapping("/sessions/savve")
    @ResponseBody
    public int savveSession(@RequestParam("email") String email, HttpServletRequest request) {
        request.getSession(false);
        request.getSession().setAttribute("resident", residentService.findOneByEmail(email).getId());
        int id = Integer.parseInt(request.getSession().getAttribute("resident").toString());
        return id;
    }


    @PostMapping("/sessions/destroy")
    public void destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
