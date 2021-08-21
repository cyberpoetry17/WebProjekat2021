package web2021.service;

import static web2021.Application.update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import web2021.dto.AddArticleDTO;
import web2021.dto.ArticleDTO;
import web2021.model.Article;
import web2021.model.Restaurant;
import web2021.model.enums.ArticleType;
import web2021.repository.RestaurantRepository;

public class ArticleService {
	
	private RestaurantRepository restaurantRepository;
	
	public ArticleService() {
		this.restaurantRepository = new RestaurantRepository();
	}
	
	public Article getArticleById(Long id) {
		for(Restaurant restaurant : restaurantRepository.getAll()) {
			for(Article article : restaurant.getArticles()) {
				if(article.getId().equals(id)) {
					if(!article.isDeleted()) {
						return article;
					}
				}
			}
		}
		return null;
	}
	
	public List<ArticleType> getAllArticleTypes() {
		List<ArticleType> response = new ArrayList<ArticleType>(Arrays.asList(ArticleType.values()));
		return response;
	}
	
	public boolean checkIsNameTaken(Long id, String name) {
		Restaurant restaurant = restaurantRepository.getById(id);
		for(Article article : restaurant.getArticles()) {
			if(article.getName().toLowerCase().equals(name.toLowerCase())) {
				if(!article.isDeleted()) return true;
			}
		}
		return false;
	}
	
	public Article addArticle(AddArticleDTO dto) {
		Article article = new Article();
		article.setId(System.currentTimeMillis());
		article.setDeleted(false);
		article.setName(dto.getName());
		article.setPrice(dto.getPrice());
		article.setArticleType(dto.getArticleType());
		article.setQuantity(dto.getQuantity());
		article.setDescription(dto.getDescription());
		article.setImage(dto.getImage());
		article.setRestaurantId(dto.getRestaurantId());
		Restaurant restaurant = restaurantRepository.getById(dto.getRestaurantId());
		restaurant.getArticles().add(article);
		restaurantRepository.update(restaurant);
		return article;
	}
	
	public List<Article> getAllArticlesFor(Long restaurantId) {
		List<Article> response = new ArrayList<Article>();
		for(Article article : restaurantRepository.getById(restaurantId).getArticles()) {
			if(!article.isDeleted()) {
				response.add(article);
			}
		}
		return response;
	}
	
	public Article updateArticle(ArticleDTO dto) {
		for(Restaurant restaurant : restaurantRepository.getAll()) {
			for(Article article : restaurant.getArticles()) {
				if(article.getId().equals(dto.getId())) {
					article.setName(dto.getName());
					article.setPrice(dto.getPrice());
					article.setArticleType(dto.getArticleType());
					article.setQuantity(dto.getQuantity());
					article.setDescription(dto.getDescription());
					article.setImage(dto.getImage());
					restaurantRepository.update(restaurant);
					update.updateShoppingCart(article);
					return article;
				}
			}
		}
		return null;
	}
	
	public List<Article> getAvailableArticles(Long restaurantId) {
		List<Article> response = new ArrayList<Article>();
		for(Article article : getAllArticlesFor(restaurantId)) {
			if(article.getQuantity() > 0) {
				response.add(article);
			}
		}
		return response;
	}
	
}
