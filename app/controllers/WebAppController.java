package controllers;

import com.avaje.ebean.Ebean;

import play.mvc.Controller;
import play.mvc.Result;

import views.html.index;

public class WebAppController extends Controller{
	public Result index() {
		return ok(index.render("Hello"));
	}
	
}
