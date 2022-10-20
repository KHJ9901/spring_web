package com.cosmos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmos.common.OracleConn;
import com.cosmos.dto.AttachFile;
import com.cosmos.dto.Board;
import com.cosmos.dto.Criteria;
import com.cosmos.dto.Reply;
import com.cosmos.dto.Thumbnail;

import oracle.jdbc.OracleTypes;

public class BoardDao_SQL {
	private final Connection conn = OracleConn.getInstance().getConn();
		
	public List<Board> boardList(Criteria cri) {
				
		List<Board> board = new ArrayList<Board>();
		
		String sql = "SELECT * FROM ("				
				+ "SELECT rownum as rn, seqno, title, wdate, count, name"; 
	       sql += " FROM (";
	       sql += " SELECT seqno, title,"; 
		   sql += " TO_CHAR(b.wdate, 'YYYY\"년\"MM\"월\"DD\"일\" HH:MI:SS PM', 'nls_date_language=american') wdate,"; 
		   sql += "	count, name";
		   sql += "	FROM board b, member m ";
		   sql += " WHERE b.id = m.id ";
		   sql += " ) ";
	       sql += " WHERE rownum <= ?*? ORDER BY seqno DESC ";
	       sql += ") WHERE 1=1 ";
	       sql += " AND rn > (?-1)*?";	      	       
	       
		System.out.println(sql);	   
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, 
					  						  ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, cri.getCurrentPage());
			stmt.setInt(2, cri.getRowPerPage());
			stmt.setInt(3, cri.getCurrentPage());
			stmt.setInt(4, cri.getRowPerPage());
			
			ResultSet rs = stmt.executeQuery();
			
			//rs.last();
			
			//rs.beforeFirst();
			
			//int i=0;
			while(rs.next()) {
				Board b = new Board();
				b.setNo(rs.getString("rn"));
				b.setTitle(rs.getString("title"));
				b.setWdate(rs.getString("wdate"));
				b.setName(rs.getString("name"));
				b.setCount(rs.getString("count"));
				b.setSeqno(rs.getString("seqno"));
				//board[i++] = b;
				board.add(b);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return board;
	}

	public Board boardDetail(String seqno) {
		
		Board board = new Board();
		
		try {
			//조회수 카운트
			String sql = "update board set count = count + 1 where seqno = "+seqno;
			PreparedStatement stmt = conn.prepareStatement(sql);		
			stmt.executeUpdate();
			
			//해당 게시물 세부내용 조회
			sql = "select title, b.content, b.id, b.open, ";
			sql +="	      TO_CHAR(b.wdate, 'YYYY-MM-DD(DY) HH:MI:SS PM') wdate, "; 
			sql +="		  count, (select name from member m where m.id = b.id ) name ";      
			sql +="	from board b ";
			sql +="	where b.seqno = ?";
			sql +="	union all ";
			sql +="	select '', r.content, r.id, '', ";
			sql +="	       TO_CHAR(r.wdate, 'YYYY-MM-DD(DY) HH:MI:SS PM'), ";
			sql +="	       0, (select name from member m where m.id = r.id) ";
			sql +="	from reply r ";  
			sql +="	where r.board_seqno  = ? ";			
						
			/*
			 * sql = "select title, b.content, id, "; sql +=
			 * " TO_CHAR(b.wdate, 'YYYY\"년\"MM\"월\"DD\"일(\"DY\")\" HH:MI:SS PM') wdate,";
			 * sql += " count, (select name from member m where m.id = b.id ) name"; sql +=
			 * " from board b "; sql += "where b.seqno = ?";
			 */			
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, 
					  						  ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, seqno);
			stmt.setString(2, seqno);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();			
										
			board.setSeqno(seqno);
			board.setTitle(rs.getString("title"));
			board.setContent(rs.getString("content"));
			board.setId(rs.getString("id"));
			board.setWdate(rs.getString("wdate"));
			board.setCount(rs.getString("count"));
			board.setName(rs.getString("name"));
			board.setOpen(rs.getString("open"));
						
			//rs.last();
			//Reply[] reply_arr = new Reply[rs.getRow()-1];
			List<Reply> reply_arr = new ArrayList<Reply>();
			
			//rs.first();
			//int i=0;
			while(rs.next()) {
				Reply reply = new Reply();
				reply.setComment(rs.getString("content"));
				reply.setId(rs.getString("id"));
				reply.setWdate(rs.getString("wdate"));
				reply.setName(rs.getString("name"));
				//reply_arr[i++] = reply;
				reply_arr.add(reply);
			}
			board.setReply(reply_arr);	
						
			//첨부파일 저장
			sql = "SELECT * FROM attachfile where board_seqno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, seqno);			
			rs = stmt.executeQuery();
						
			List<AttachFile> fileList = new ArrayList<AttachFile>(); 
					
			while(rs.next()) {
				AttachFile attachfile = new AttachFile();
				attachfile.setNo(rs.getString("no"));
				attachfile.setFileName(rs.getString("filename"));
				attachfile.setSaveFileName(rs.getString("savefilename"));
				attachfile.setFileSize(rs.getString("filesize"));
				attachfile.setFileType(rs.getString("filetype"));
				attachfile.setFilePath(rs.getNString("filepath"));				
				
				if(rs.getString("filetype").contains("image")) {				
					sql = "SELECT * FROM attachfile_thumb WHERE attach_no = ? ";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, rs.getString("no"));			
					ResultSet rs2 = stmt.executeQuery();
					
					while(rs2.next()) {
						Thumbnail th = new Thumbnail();	
						th.setNo(rs2.getString("no"));
						th.setFileName(rs2.getString("filename"));
						th.setFileSize(rs2.getString("filesize"));
						th.setFilePath(rs2.getString("filepath"));
						attachfile.setThumbnail(th);
					}
				}
				fileList.add(attachfile);
			}
			
			board.setAttachfile(fileList);
			
			//System.out.println(reply_arr[1].getComment());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//System.out.println(board.getReply()[1].getComment());
		return board;
	}

	public String insert(Board board, AttachFile attachFile) {
		
		
		String sql="INSERT INTO board(seqno, title, content, open, id) VALUES (BOARD_SEQ.NEXTVAL, ?,?,?,?)";
		PreparedStatement stmt;
		String seqno = null;
		
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, board.getTitle());
			stmt.setString(2, board.getContent());
			stmt.setString(3, board.getOpen());
			stmt.setString(4, board.getId());
			stmt.executeQuery();
						
			sql = "SELECT max(seqno) as seqno FROM board";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			seqno =rs.getString("seqno");
			
			//첨부파일
			if(attachFile != null) {
				
				String attach_no = insertAttachFile(seqno, attachFile);				
				String fileType = attachFile.getFileType();				
				
				//썸네일
				if(fileType.substring(0, fileType.indexOf("/")).equals("image")) {
					insertThumbNail(attach_no, attachFile);
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {						
			try {
				conn.rollback();
			} catch (SQLException e1) {				
				System.out.println("rollback처리됨");
			}
			e.printStackTrace();
		}
		
		return seqno; 
	}
	
	void insertThumbNail(String attach_no, AttachFile attachFile) {
		//썸네일 저장
		String sql = "INSERT INTO attachfile_thumb(no, filename, filesize, filepath, attach_no) "
		    + " VALUES (ATTACHFILE_THUMB_SEQ.NEXTVAL, ?,?,?,?)";
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			Thumbnail thumb = attachFile.getThumbnail();
			stmt.setString(1, thumb.getFileName());
			stmt.setString(2, thumb.getFileSize());
			stmt.setString(3, thumb.getFilePath());
			stmt.setString(4, attach_no);
			stmt.executeQuery();
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
	}
	
	String insertAttachFile(String seqno, AttachFile attachFile) {
		//첨부파일저장
		String sql = "INSERT INTO attachFile(no, filename, savefilename, filesize, filetype, filepath, board_seqno)"
			+ " VALUES (ATTACHFILE_SEQ.NEXTVAL, ?,?,?,?,?,?)";
		PreparedStatement stmt;
		String attach_no = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, attachFile.getFileName());
			stmt.setString(2, attachFile.getSaveFileName());
			stmt.setString(3, attachFile.getFileSize());
			stmt.setString(4, attachFile.getFileType());
			stmt.setString(5, attachFile.getFilePath());
			stmt.setString(6, seqno);
			stmt.executeQuery();
			
			sql = "SELECT max(no) FROM attachFile";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			attach_no =rs.getString(1);			
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return attach_no;
	}		

	public void update(Board board, AttachFile attachFile) {
		//보드 update
		String sql="call p_updateBoard(?,?,?,?)";
		CallableStatement stmt;
		try {
			stmt = conn.prepareCall(sql);
			stmt.setString(1, board.getTitle());
			stmt.setString(2, board.getContent());
			stmt.setString(3, board.getOpen());
			stmt.setString(4, board.getSeqno());
			stmt.executeQuery();
			
			//첨부파일
			if(attachFile != null) {				
				String attach_no = insertAttachFile(board.getSeqno(), attachFile);				
				String fileType = attachFile.getFileType();				
				
				//썸네일
				if(fileType.substring(0, fileType.indexOf("/")).equals("image")) {
					insertThumbNail(attach_no, attachFile);
				}
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
		
	}

	public int getTotalRec() {
		int total = 0;
		
		String sql="SELECT count(*) as total FROM board WHERE isdel='N' AND open='Y'";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			total = rs.getInt("total");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
		
		return total;
	}

	public Map<String, String> deleteByNo(String seqno) {
		Map<String, String> map = new HashMap<String, String>();
		CallableStatement stmt;
		String sql = "call p_deleteBoard(?,?,?,?)";
		try {
			stmt = conn.prepareCall(sql);
			stmt.setString(1, seqno);
			stmt.registerOutParameter(2, OracleTypes.VARCHAR);
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);
			stmt.executeQuery();
			System.out.println("프로시저 리턴결과 : " + stmt.getString(2));
			map.put("savefilename", stmt.getString(2));
			map.put("filepath", stmt.getString(3));
			map.put("thumb_filename", stmt.getString(4));
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
		return map;
	}
	
}









