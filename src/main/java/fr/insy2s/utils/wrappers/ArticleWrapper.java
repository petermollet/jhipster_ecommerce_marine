package fr.insy2s.utils.wrappers;

import fr.insy2s.service.dto.ArticleDTO;
import fr.insy2s.service.dto.TypeArticleDTO;

public class ArticleWrapper {

	private ArticleDTO article;
	private TypeArticleDTO typeArticle;
	
	
	
	public ArticleWrapper(ArticleDTO article, TypeArticleDTO typeArticle) {
		super();
		this.article = article;
		this.typeArticle = typeArticle;
	}
	public ArticleDTO getArticle() {
		return article;
	}
	public void setArticle(ArticleDTO article) {
		this.article = article;
	}
	public TypeArticleDTO getTypeArticle() {
		return typeArticle;
	}
	public void setTypeArticle(TypeArticleDTO typeArticle) {
		this.typeArticle = typeArticle;
	}
	
	
	
	
	
	
}
