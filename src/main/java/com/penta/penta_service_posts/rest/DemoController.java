package com.penta.penta_service_posts.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping
    @PreAuthorize("hasRole('client_admin')")
    public String hello(){return "Heelo 1" ; } 

    @GetMapping("/hello-2")
    @PreAuthorize("hasRole('client_user')")
    public String hello2(){return "Heelo 2" ; } 


}
