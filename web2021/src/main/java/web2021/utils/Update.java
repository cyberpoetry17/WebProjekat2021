package web2021.utils;

import java.util.ArrayList;
import java.util.List;

import web2021.model.Article;
import web2021.model.ArticleQuantity;
import web2021.model.Comment;
import web2021.model.Customer;
import web2021.model.ShoppingCart;
import web2021.repository.CommentRepository;
import web2021.repository.CustomerRepository;

public class Update {
	
	private CustomerRepository customerRepository;
	private CommentRepository commentRepository;
	
	public Update() {
		this.customerRepository = new CustomerRepository();
		this.commentRepository = new CommentRepository();
	}
	
	public void updateShoppingCart(Article article) {
		for(Customer customer : customerRepository.getAll()) {
			ShoppingCart shoppingCart = customer.getShoppingCart();
			List<ArticleQuantity> newArticleQuantities = new ArrayList<ArticleQuantity>();
			double newPrice = 0;
			for(ArticleQuantity articleQuantity : shoppingCart.getArticles()) {
				if(articleQuantity.getArticle().getId().equals(article.getId())) {
					if(article.getQuantity() != 0) {
						articleQuantity.setArticle(article);
						newPrice = newPrice + articleQuantity.getQuantity() * articleQuantity.getArticle().getPrice();
						newArticleQuantities.add(articleQuantity);
					}
				}
				else {
					newPrice = newPrice + articleQuantity.getQuantity() * articleQuantity.getArticle().getPrice();
					newArticleQuantities.add(articleQuantity);
				}
			}
			shoppingCart.setArticles(newArticleQuantities);
			shoppingCart.setPrice(newPrice);
			customerRepository.update(customer);
		}
	}
	
	public void updateComment(Customer costumer) {
		for(Comment comment : commentRepository.getAll()) {
			if(comment.getCustomer().getId().equals(costumer.getId())) {
				comment.setCustomer(costumer);
				commentRepository.update(comment);
			}
		}
	}
}
