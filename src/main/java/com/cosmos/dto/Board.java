package com.cosmos.dto;

import java.util.List;

public class Board {
	private String no;
	private String seqno;
	private String title;
	private String content;
	private String count;
	private String wdate;
	private String name;
	private String id;
	private String open;
	//private Reply[] reply;
	private List<Reply> reply;
	private List<AttachFile> attachfile; 
	
	public List<AttachFile> getAttachfile() {
		return attachfile;
	}
	public void setAttachfile(List<AttachFile> attachfile) {
		this.attachfile = attachfile;
	}
	
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/*
	public Reply[] getReply() {
		return reply;
	}
	
	public void setReply(Reply[] reply) {
		this.reply = reply;
	}
	*/
	
	public List<Reply> getReply() {
		return reply;
	}
	
	public void setReply(List<Reply> reply) {
		this.reply = reply;
	}
	
	
}
