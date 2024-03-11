package com.drathing.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.drathing.dto.CommentDTO;
import com.drathing.dto.KeywordDTO;
import com.drathing.dto.UserDTO;
import com.mysql.cj.Session;
import com.mysql.cj.xdevapi.JsonValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	@GetMapping(value = {"/index","/"})
	public String index() {
		return "/index";
	}
	
	//로그인 선택창
	@GetMapping("/login/select")
	public String loginselect() {
		return "/loginselect";
	}
	//로그인 고치기 pg값에 따라서 redirect 다르게 해야함

	//로그인창
	@GetMapping("/login")
	public String login(){
		return "/login";
	}

	@PostMapping("/login")
	public  String login(UserDTO user, Model model, HttpServletRequest request,
						 @RequestParam(value = "pg", required = false) String pg){
		String uid = user.getUid();

		String upassword = user.getUpassword();
		String msg =null;

		if(isValidEmail(uid)){
			UserDTO userDTO = service.selectUserDTO(uid);
			if(userDTO != null){
				if(userDTO.getUpassword().equals(upassword)){

					HttpSession session = request.getSession();
					session.setAttribute("user",user.getUid());
					session.setMaxInactiveInterval(60*60);

					if(pg !=null){
						if(pg != ""){
							return "redirect:/board";
						}else{
							return  "/keyword";
						}

					}else{

						return  "/keyword";
					}

				}else{
					 msg = "비밀번호가 다릅니다";
				}
			}else{
				msg = "등록되지 않은 이메일입니다";
			}
		}else{
			msg = "유효하지 않은 이메일 형식입니다";
		}
		model.addAttribute("msg",msg);

		return  "/login?pg="+pg;
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,
	@RequestParam(value = "k", required = false) String k){
		HttpSession session = request.getSession();
		session.invalidate();
		if(k!=null ){
			return  "/loginselect";
		}
		return "redirect:board";
	}

	//키워드 선택
	@GetMapping("/keyword")
	public String keyword(){
		return "/keyword";
	}

	// keyword create
	@GetMapping("/create")
	public  String create(){
		return "/create";
	}

	//그림 그리기
	@GetMapping("/drawing")
	public String drawing(Model model){
		KeywordDTO keywordDTO = service.selectKeywordDTO();
		model.addAttribute("key",keywordDTO.getName());
		return "/drawing";
	}

	@PostMapping("/drawing")
	public String drawing(@RequestParam("key") String name, Model model){
		KeywordDTO keywordDTO = new KeywordDTO();
		keywordDTO.setName(name);
		service.insertKeywordDTO(keywordDTO);
		model.addAttribute("key",name);
		return "/drawing";
	}


	//게시판 (board)
	@GetMapping("/board")
	public String board(Model model) {
		List<PrintDTO> prints =service.selectPrintDTOs();
		model.addAttribute("prints", prints);
		return "/board";
	}
	
	//게시판으로 그림 들고 오는 경우
	@PostMapping("/board")
	public String board(@RequestParam(name = "drawingData")String drawingData,
			@RequestParam(name = "key")String key,Model model, HttpServletRequest request) {
		System.out.println(drawingData);
		 String[] parts = drawingData.split(",");
		 PrintDTO print = new PrintDTO();
			 String user= String.valueOf(request.getSession().getAttribute("user"));
			 if(user.equals("null")) {
				 user="guest";
			 }
			 print.setUser(user);
			 print.setName(key);
			 print.setPrinting(parts[1]);
		System.out.println(print);
			 service.insertPrintDTO(print);
	
		return "redirect:/board";
	}

	
	//게시물
	@GetMapping("/bulletin")
	public String bulletin(Model model, @RequestParam("number") String number){
		PrintDTO printDTO = service.selectPrintDTO(number);
		List<CommentDTO> lists = service.selectCommentDTOs(number);
		model.addAttribute("print",printDTO);
		model.addAttribute("lists", lists);
		return "/bulletin";
	}
	//좋아요
	@PostMapping("/like")
	public  String like(Model model,
						@RequestParam("number") String number,
						@RequestParam("like")String like){
		PrintDTO printDTO = service.selectPrintDTO(number);
		int nlike = Integer.parseInt(like)+1;
		printDTO.setLike(String.valueOf(nlike));
		service.updatePrintDTO(printDTO);
		return "redirect:/bulletin?number="+number;
	}
}
