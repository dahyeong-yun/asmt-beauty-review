package kr.co.beauty.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import kr.co.beauty.dao.MemberDAO;
import kr.co.beauty.vo.MemberVO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	private ModelAndView modelAndView;
	private MemberVO memberVO;
	
	@Autowired
	private HttpSession session;
	
	// 로그인
	public ModelAndView memberLogin(MemberVO memberVO, HttpServletResponse response) throws IOException {
		modelAndView = new ModelAndView();
		response.setContentType("text/html; charset=UTF-8");
		
		MemberVO loginMember = memberDAO.memberLogin(memberVO);
		PrintWriter out = response.getWriter();
		
		if (memberVO.getMEM_PW().equals(loginMember.getMEM_PW())) {
			session.setAttribute("session_id", memberVO.getMEM_ID());
			modelAndView.setViewName("main");
		} else {
			// 로그인 실패 alert
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}
		return modelAndView;
	}

}