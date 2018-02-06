package com.huzijun.check.controller;

import com.huzijun.check.Params;
import com.huzijun.check.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping(value = "/Hello",method = RequestMethod.POST)
	public String Hello (@Valid Params params)  {
			return params.getId() +" "+ params.getName();
	}
}
