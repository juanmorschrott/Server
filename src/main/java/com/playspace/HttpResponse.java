package com.playspace;

public class HttpResponse {

	private String content = "";
	
	private int status;

	public String render() {
		StringBuilder html = new StringBuilder();
		html.append("<!doctype html>")
			.append("<body style='padding-left:1em; font-size: 2em; font-family: monospace;'>")
			.append("<p>" + this.content + "</p>")
			.append("</body>")
			.append("</html>");

		return html.toString();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
