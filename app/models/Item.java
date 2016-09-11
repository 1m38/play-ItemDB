package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import com.avaje.ebean.annotation.CreatedTimestamp;

import com.avaje.ebean.Model;

@Entity(name = "item")
public class Item extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	@NotNull
	@Size(min=1, max=100)
	public String title;
	
	@Size(max=500)
	public String description;
	
	public Long price;
	
	@Size(max=100)
	public String picture_uri;
	
	@CreatedTimestamp
	public Date createDate;
	
	
	public Item(Long id, String title, String description, Long price, String picture_uri) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.picture_uri = picture_uri;
	}
	
	public static final Find<Long, Item> finder = new Find<Long, Item>(){};
	
}
