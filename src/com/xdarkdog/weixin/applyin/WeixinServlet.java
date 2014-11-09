package com.xdarkdog.weixin.applyin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// "/weixin.do"
public class WeixinServlet extends HttpServlet {
	private static final long serialVersionUID = -1L;
	private static final String token = "darkdog";
	private static final String weixinid = "gh_275da87c0371";

	// 进行地址的验证
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String checkin = "";

		String[] infos = { nonce, timestamp, token };
		if (signature != null && infos != null && infos.length == 3) {
			Arrays.sort(infos);
			for (String s : infos) {
				checkin += s;
			}
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-1"); // 选择SHA-1，也可以选择MD5
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			byte[] digest = md.digest(checkin.getBytes()); // 返回的是byte[]，要转化为String存储比较方便
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为十六进制数
			for (int i = 0; i < digest.length; i++) {
				String shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			if (hexString.toString().equalsIgnoreCase(signature)) {
				out.println(echostr);
			}
		}
		out.flush();
		out.close();
	}

	// 微信将用户发送的内容会发送到这里！
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 首先是把微信服务器发送额XML格式的字符串接受
		request.setCharacterEncoding("utf-8");
		BufferedReader br = request.getReader();
		char[] buf = new char[1024];
		int len = 0;
		StringBuffer buffer = new StringBuffer();
		while ((len = br.read(buf)) != -1) {
			buffer.append(buf, 0, len);
		}
		String xmlmsg = buffer.toString();
		// 获取消息的类型
		String msgType = msgType(xmlmsg);
		if (msgType.equalsIgnoreCase("event")) {
			processEvent(xmlmsg, response);
		} else if (msgType.equalsIgnoreCase("text")) {
			processText(xmlmsg, response);
		}

//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the POST method----杭州暗黑狗网络技术有限公司");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
	}

	// 使用小黄鸡和用户对话 
	private void processText(String xmlmsg, HttpServletResponse response) {
		// 获取用户的OpenID
		String fromUserName = getMiddle("FromUserName><![CDATA[", "]]></FromUserName", xmlmsg);
		// 获取用户发送的消息
		String content = getMiddle("Content><![CDATA[", "]]></Content", xmlmsg);
		// 获取小黄鸡的返回结果
		String res = SimsimiUtil.talk(content);
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("<xml>");
		out.println("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		out.println("<FromUserName><![CDATA[" + weixinid + "]]></FromUserName>");
		out.println("<CreateTime>" + System.currentTimeMillis() + "</CreateTime>");
		out.println("<MsgType><![CDATA[text]]></MsgType>");
		out.println("<Content><![CDATA[" + res + "]]></Content></xml>");
		out.flush();
		out.close();
	}

	// 处理事件 TODO 扫描带参数二维码事件 这个还没有处理
	private void processEvent(String xmlmsg, HttpServletResponse response) {
		String event = getMiddle("Event><![CDATA[", "]]></Event", xmlmsg);
		if (event.equals("subscribe")) { // TODO 用户订阅了公众号 
			// 获取用户的OpenID
			String fromUserName = getMiddle("FromUserName><![CDATA[", "]]></FromUserName", xmlmsg);
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			PrintWriter out;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			String res = "您好！感谢关注嘿狗社区服务平台！了解更过信息，请<a href=\"http://mp.weixin.qq.com/s?__biz=MzAwMzA0NDQ5MQ==&mid=201196564&idx=1&sn=ee1969116f74d0c01f3f30b209a3f203#rd\">点击这里</a>";
			out.println("<xml>");
			out.println("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
			out.println("<FromUserName><![CDATA[" + weixinid + "]]></FromUserName>");
			out.println("<CreateTime>" + System.currentTimeMillis() + "</CreateTime>");
			out.println("<MsgType><![CDATA[text]]></MsgType>");
			out.println("<Content><![CDATA[" + res + "]]></Content></xml>");
			out.flush();
			out.close();
		} else if (event.equals("unsubscribe")) { // TODO 用户取消订阅公众号 

		}
	}

	// 获取消息的类型 这么做比xml解析要快！
	private static String msgType(String msg) {
		return getMiddle("MsgType><![CDATA[", "]]></MsgType", msg);
	}

	// 功能方法
	private static String getMiddle(String start, String end, String str) {
		return str.substring(str.indexOf(start) + start.length(), str.indexOf(end));
	}

	public static void main(String[] args) {
		String xml = "<xml><ToUserName><![CDATA[gh_275da87c0371]]></ToUserName><FromUserName><![CDATA[oi9L-siJ6O_Eak2Bn1SRH4QIxqBk]]></FromUserName><CreateTime>1413460023</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[好的]]></Content><MsgId>6070764573189294221</MsgId></xml>";
		System.out.println(msgType(xml));
	}

}
