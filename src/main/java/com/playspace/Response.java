package com.playspace;

public class Response {
	
	public static String render(String content) {
		StringBuilder html = new StringBuilder();
		html.append("<!doctype html>")
			.append("<body>")
			.append("<p>" + content + "</p>")
			.append("</body>")
			.append("</html>");
		
		return html.toString();
	}

}
