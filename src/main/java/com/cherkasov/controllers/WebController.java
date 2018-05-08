package com.cherkasov.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class WebController {

  @GetMapping("/devices")
  public String devices() {
    log.trace("Access to /devices");
    return "/devices";
  }

  @GetMapping("/")
  public String homeRoot() {
    log.trace("Access to /");
    return "/home";
  }

  @GetMapping("/home")
  public String home() {
    log.trace("Access to /home");
    return "/home";
  }

  @GetMapping("/admin")
  public String admin() {
    log.trace("Access to /admin");
    return "/admin";
  }

  @GetMapping("/user")
  public String user() {
    log.trace("Access to /user");
    return "/user";
  }

  @GetMapping("/about")
  public String about() {
    log.trace("Access to /about");
    return "/about";
  }

  @GetMapping("/login")
  public String login() {
    log.trace("Access to /login");
    return "/login";
  }

  @GetMapping("/403")
  public String error403() {
    log.trace("Access to /403");
    return "/error/403";
  }
}
