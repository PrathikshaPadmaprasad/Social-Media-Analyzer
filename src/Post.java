import java.sql.Connection;

public class Post {
	private int id;
	private String content;
	private String author;
	private int likes;
	private int shares;
	private int userId;
		
	public Post(int id, String content, String author,int likes, int shares,int userId) {
		this.id=id;
		this.content=content;
		this.author=author;
		this.likes=likes;
		this.shares=shares;
		this.userId=userId;
	
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
}
