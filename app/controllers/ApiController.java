package controllers;

import java.util.List;
import java.util.ArrayList;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import models.Item;

public class ApiController extends Controller{

	@Transactional
	public Result init() {
		// 初期ダミーデータを登録
		new Item(1L, "Item1", "description1", 200L, "").save();
		new Item(2L, "Item2", "", 1000L, "").save();

		ObjectNode result = Json.newObject();
		result.put("message", "Saved initial data.");
		return ok(result);
	}

	@Transactional
	public Result showAll() {
		// 全itemを取得して、jsonで返す
		// いくつitemがあっても全て返す(開発用) 返すitem数を制限したAPIも後で作る
		List<Item> items = Item.finder.all();

		ObjectNode result = Json.newObject();
		result.put("items", Json.toJson(items));
		return ok(result);
	}
	
	public Result showItems() {
		// 全itemを取得して、jsonで返す
		// 返すitem数を制限し(10-100の間, クエリパラメータで指定)、
		// 何ページ目かを指定
		
		// とりあえずshowAllしておく
		return showAll();
	}
	
	@Transactional
	public Result show(Long id) {
		Item item = Item.finder.byId(id);
		if(item == null) {
			return badRequest("Item id " + id + " not found.");
		}
		return ok(Json.toJson(item));
	}
	
	@Transactional
	public Result createItem() {
		JsonNode json = request().body().asJson();
		if(json == null) {
			return badRequest("Expecting Json data");
		}
		Item item = Item.fromJson(json);
		if(Item.finder.byId(item.id) != null) {
			return badRequest("Item id " + item.id + " already exists.");
		}
		item.save();

		ObjectNode result = Json.newObject();
		result.put("message", "Item saved.");
		result.put("item", Json.toJson(item));
		return ok(result);
	}
	
	@Transactional
	public Result deleteItem(Long id) {
		Item item = Item.finder.byId(id);
		if(item == null) {
			return badRequest("Item id " + id + " not found.");
		}
		item.delete();
		ObjectNode result = Json.newObject();
		result.put("message", "Item deleted.");
		result.put("item", Json.toJson(item));
		return ok(result);
	}
	
	@Transactional
	public Result updateItem(Long id) {
		JsonNode json = request().body().asJson();
		if(json == null) {
			return badRequest("Expecting Json data");
		}
		Item oldItem = Item.finder.byId(id);
		if(oldItem == null) {
			return badRequest("Item id " + id + " not found.");
		}
		
		Item newItem = Item.fromJson(json);
		if(newItem.id != oldItem.id) {
			return badRequest("Item ID not equals.");
		}
		newItem.update();

		ObjectNode result = Json.newObject();
		result.put("message", "Item updated.");
		result.put("item", Json.toJson(newItem));
		return ok(result);
	}
}
