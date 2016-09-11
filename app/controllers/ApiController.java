package controllers;

import java.util.List;
import java.util.ArrayList;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
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
		List<Item> items = Item.finder.all();

		ObjectNode result = Json.newObject();
		result.put("items", Json.toJson(items));
		return ok(result);
	}
}
