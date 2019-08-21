package com.orhon.pa.modules.opa.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="${adminPath}/opa/view")
public class ViewController {

	
	@RequestMapping(value="table",method=RequestMethod.POST)
	public String toView(){
		return "modules/opa/table";
	}
}
