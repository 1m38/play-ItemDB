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
import models.ItemNullTitleException;

public class ApiController extends Controller{

	@Transactional
	public Result init() {
		// 初期ダミーデータを登録
		new Item(null, "Item1", "description1", 200L, "").save();
		new Item(null, "Item2", "", 1000L, "").save();

		return ok("Saved initial data.");
	}

	@Transactional
	public Result showAll() {
		// 全itemを取得して、jsonで返す
		// いくつitemがあっても全て返す(開発用) 返すitem数を制限したAPIも後で作る
		List<Item> items = Item.finder.all();

		return ok(Json.toJson(items));
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
			return notFound("Item id " + id + " not found.");
		}
		return ok(Json.toJson(item));
	}
	
	@Transactional
	public Result createItem() {
		JsonNode json = request().body().asJson();
		if(json == null) {
			return badRequest("Expecting JSON data");
		}
		Item item = null;
		try{
			item = Item.fromJson(json);
		} catch(ItemNullTitleException e) {
			return badRequest(e.getMessage());
		}
		// idは自動で割り振られるので削除しておく
		item.id = null;
		item.save();

		response().setHeader(LOCATION, controllers.routes.ApiController.show(item.id).url());
		return created(Json.toJson(item));
	}
	
	@Transactional
	public Result deleteItem(Long id) {
		Item item = Item.finder.byId(id);
		if(item == null) {
			return notFound("Item id " + id + " not found.");
		}
		item.delete();
		return ok(Json.toJson(item));
	}
	
	@Transactional
	public Result updateItem(Long id) {
		JsonNode json = request().body().asJson();
		if(json == null) {
			return badRequest("Expecting JSON data");
		}
		Item oldItem = Item.finder.byId(id);
		if(oldItem == null) {
			return notFound("Item id " + id + " not found.");
		}
		
		Item newItem = null;
		try{
			newItem = Item.fromJson(json);
		} catch(ItemNullTitleException e) {
			return badRequest(e.getMessage());
		}
		if(newItem.id != oldItem.id) {
			return badRequest("Item IDs in JSON and URI not equal.");
		}
		newItem.update();

		response().setHeader(LOCATION, controllers.routes.ApiController.show(newItem.id).url());
		return created(Json.toJson(newItem));
	}
}
