package fr.insy2s.utils.wrappers;

import java.util.List;

import fr.insy2s.service.dto.ArticleDTO;
import fr.insy2s.service.dto.PanierDTO;
import fr.insy2s.service.dto.TypeArticleDTO;
import fr.insy2s.service.dto.UserDTO;

public class PanierWrapper {

	private PanierDTO panier;
	private UserDTO client;
	private List<ArticleWrapper> listeArticleWrappers;
	
	
	
	public PanierWrapper(PanierDTO panier, UserDTO client, List<ArticleWrapper> listeArticleWrappers) {
		super();
		this.panier = panier;
		this.client = client;
		this.listeArticleWrappers = listeArticleWrappers;
	}
	public PanierDTO getPanier() {
		return panier;
	}
	public void setPanier(PanierDTO panier) {
		this.panier = panier;
	}
	public UserDTO getClient() {
		return client;
	}
	public void setClient(UserDTO client) {
		this.client = client;
	}
	public List<ArticleWrapper> getListeArticleWrappers() {
		return listeArticleWrappers;
	}
	public void setListeArticleWrappers(List<ArticleWrapper> listeArticleWrappers) {
		this.listeArticleWrappers = listeArticleWrappers;
	}
	
	
	
	
	
	
	
	
}
