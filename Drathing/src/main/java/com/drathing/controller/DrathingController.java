package com.drathing.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.drathing.dto.PrintDTO;
import com.drathing.service.DrathingService;



@Controller
public class DrathingController {
	private final DrathingService service;
	
	@Autowired
	private DrathingController(DrathingService service) {
		this.service=service;
	}
	
	//main
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
	
	//로그인 선택창
	@GetMapping("/login/select")
	public String loginselect() {
		return "/loginselect";
	}
	
	//게시판 (board)
	@GetMapping("/board")
	public String bulletin(Model model) {
		List<PrintDTO> prints =service.selectPrintDTOs();
		model.addAttribute("prints", prints);
		return "/board";
	}
	
	//게시판으로 그림 들고 오는 경우
	@PostMapping("/board")
	public String bulletin(@RequestParam(name = "drawingData")String drawingData,
			@RequestParam(name = "key")String key,Model model, HttpServletRequest request) {
		 String[] parts = drawingData.split(",");
		 PrintDTO print = service.selectPrintDTOPrint (parts[1]);
			 String user= String.valueOf(request.getSession().getAttribute("user"));
			 if(user.equals("null")) {
				 user="guest";
			 }
			 print.setUser(user);
			 print.setName(key);
			 print.setPrinting(parts[1]);
			 service.insertPrintDTO(print);
	
		return "redirect:/board";
	}
	
	//
	
	//
	
}
