package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.commentService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.AddCommentDTO;
import web2021.dto.ReviewCommentDTO;

public class CommentController {
	
	public static Route getAllComments= (Request request, Response response) -> {
		String json = gson.toJson(commentService.getAllComments());
		return json;
	};
	
	public static Route getCommentsForManager = (Request request, Response response) -> {
		String id = request.params(":id");
		String json = gson.toJson(commentService.getCommentsForManager(Long.parseLong(id)));
		return json;
	};
	
	public static Route getCommentsForRestaurant = (Request request, Response response) -> {
		String id = request.params(":id");
		String json = gson.toJson(commentService.getCommentsForRestaurant(Long.parseLong(id)));
		return json;
	};
	
	public static Route addComment = (Request request, Response response) -> {
		String payload = request.body();
		AddCommentDTO addCommentDTO = gson.fromJson(payload, AddCommentDTO.class);
		String json = gson.toJson(commentService.addComment(addCommentDTO));
		return json;
	};
	
	public static Route reviewComment = (Request request, Response response) -> {
		String payload = request.body();
		ReviewCommentDTO commentDTO = gson.fromJson(payload, ReviewCommentDTO.class);
		String json = gson.toJson(commentService.reviewComment(commentDTO.getCommentId(), commentDTO.isApproved()));
		return json;
	};
	
}
