package dao;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import utility.ConnectionManager;

public class  BlogDaoImpl implements  BlogDaoInterface{

    @Override
    public void insertBlog(Blog blog) throws ClassNotFoundException, SQLException, IOException {
	
	int blogid = blog.getBlogId();
	String blogtitle = blog.getBlogTitle();
	String blogdesc  = blog.getBlogDescription();
	LocalDate blogdate = blog.getPostedOn();
	
	ConnectionManager cm  = new ConnectionManager();
	String sql = "insert into blog(id, title, blogdesc , bdate) values (?,?,?,?)";
	
	PreparedStatement st = ConnectionManager.getConnection().prepareStatement(sql);
	st.setInt(1, blogid);
	st.setString(2, blogtitle);
	st.setString(3, blogdesc);
	st.setDate(4,Date.valueOf(blogdate));
	st.executeUpdate();
	ConnectionManager.getConnection().close();
    }

    @Override
    public List<Blog> selectAllBlogs() throws ClassNotFoundException, SQLException, IOException {
	List<Blog> bloglist = new ArrayList<Blog>();
	ConnectionManager cm = new ConnectionManager();
	String sql = "select * from blog";
	Statement st = ConnectionManager.getConnection().createStatement();
	ResultSet rs = st.executeQuery(sql);
	Blog blog = new Blog();
	while(rs.next()) {
	    blog.setBlogId(rs.getInt("id"));
	    blog.setBlogTitle(rs.getString("title"));
	    blog.setBlogDescription(rs.getString("blogdesc"));
	    java.time.LocalDate localDate = rs.getDate("bdate").toLocalDate();
	    blog.setPostedOn(localDate);
	    bloglist.add(blog);
	}
	rs.close();
	st.close();
	ConnectionManager.getConnection().close();
	System.out.println("this is blog list"+bloglist );
	return bloglist;
    }
    
}
