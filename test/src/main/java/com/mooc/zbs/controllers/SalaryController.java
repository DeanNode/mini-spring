package com.mooc.zbs.controllers;

import com.mooc.zbs.web.mvc.Controller;
import com.mooc.zbs.web.mvc.RequestMapping;
import com.mooc.zbs.web.mvc.RequestParam;

@Controller
public class SalaryController {
    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name") String name,@RequestParam("experience") String experience){
        System.out.println(name);
        System.out.println(experience);

        return 10000;
    }
}
