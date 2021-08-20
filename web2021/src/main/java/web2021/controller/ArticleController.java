package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.articleService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.AddArticleDTO;
import web2021.dto.ArticleDTO;
import web2021.dto.CheckArticleNameDTO;

public class ArticleController {
	
	public static Route checkIsNameTaken = (Request request, Response response) -> {
		String payload = request.body();
		CheckArticleNameDTO checkArticleNameDTO = gson.fromJson(payload, CheckArticleNameDTO.class);
		String json = gson.toJson(articleService.checkIsNameTaken(checkArticleNameDTO.getRestaurantId(), checkArticleNameDTO.getName()));
		return json;
	};
	
	public static Route getAllArticleTypes = (Request request, Response response) -> {
		String json = gson.toJson(articleService.getAllArticleTypes());
		return json;
	};
	
	public static Route addArticle = (Request request, Response response) -> {
		String payload = request.body();
		AddArticleDTO addArticleDTO = gson.fromJson(payload, AddArticleDTO.class);
		String json = gson.toJson(articleService.addArticle(addArticleDTO));
		return json;
	};
	
	public static Route getAllArticles = (Request request, Response response) -> {
		Long restaurantId = Long.parseLong(request.params(":id"));
		String json = gson.toJson(articleService.getAllArticlesFor(restaurantId));
		return json;
	};
	
	public static Route updateArticle = (Request request, Response response) -> {
		String payload = request.body();
		ArticleDTO articleDTO = gson.fromJson(payload, ArticleDTO.class);
		String json = gson.toJson(articleService.updateArticle(articleDTO));
		return json;
	};
	
	public static Route getAvailableArticles = (Request request, Response response) -> {
		Long restaurantId = Long.parseLong(request.params(":id"));
		String json = gson.toJson(articleService.getAvailableArticles(restaurantId));
		return json;
	};
}
