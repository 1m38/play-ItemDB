package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.JsonNode;
import com.avaje.ebean.Model;

// import com.fasterxml.jackson.databind.JsonNode;

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
	
	@UpdatedTimestamp
	public Date updatedDate;
	
	public Item(Long id, String title, String description, Long price, String picture_uri) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.picture_uri = picture_uri;
	}
	
	public static final Find<Long, Item> finder = new Find<Long, Item>(){};
	
	
	public static Item fromJson(JsonNode json) throws ItemNullTitleException {
		Long id = null;
		String title = null;
		String description = null;
		Long price = null;
		String picture_uri = null;
		if(json.has("id")) {
			id = json.findPath("id").asLong();
		}
		if(json.has("title")) {
			title = json.findPath("title").textValue();
		} else {
			throw new ItemNullTitleException("null title isn't allowed.");
		}
		if(title.length() == 0) {
			throw new ItemNullTitleException("Empty title isn't allowed.");
		}
		if(json.has("description")) {
			description = json.findPath("description").textValue();
		}
		if(json.has("price")) {
			price = json.findPath("price").asLong();
		}
		if(json.has("picture_uri")) {
			picture_uri = json.findPath("picture_uri").textValue();
		}
		
		return new Item(id, title, description, price, picture_uri);
	}
}
