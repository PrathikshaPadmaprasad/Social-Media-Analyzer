import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.print.attribute.DateTimeSyntax;

public class Post {
	private int id;
	private String content;
	private String author;
	private int likes;
	private int shares;
	private int userId;
	private LocalDateTime date_time;
		
	public Post(int id, String content, String author,int likes, int shares,int userId,LocalDateTime date_time) {
		this.id=id;
		this.content=content;
		this.author=author;
		this.likes=likes;
		this.shares=shares;
		this.userId=userId;
		this.date_time=date_time;
	    }
		
	
	public int getId (){
		return id;
	}
		
	public void setId(int id) {
		this.id=id;
	}
		
	public String getContent() {
		return content;	
	}
	
	public void setContent(String content) {
		this.content=content;
		
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author=author;
	}
	
	public int getLikes() {
		return likes;
		
	}
	public void setLikes(int likes) {
		this.likes=likes;
		
	}
	public int getShares() {
		return shares;
	}
	
	public void setShares(int shares) {
		this.shares=shares;
	}
	public int getuserId() {
		 return userId;
	}
	
	public void setuserId(int userId) {
		this.userId=userId;
		
	}

public String getDateTime() {
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	String formattedDateTime = date_time.format(formatter);

	return formattedDateTime;
	
}
public void setLocalDateTime(LocalDateTime date_time) {
	this.date_time=date_time;
}
}
	