package com.cowork.wikidata.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cowork.common.util.SchameCopyUtil;
import com.cowork.wikidata.dao.ArticleMongoDaoUtil;
import com.cowork.wikidata.model.Article;
import com.cowork.wikidata.vo.ArticleVo;
import com.cowork.wikidata.vo.ResponseVo;

@RestController
public class WikiDataController {
	
	@Autowired
	private ArticleMongoDaoUtil articleMongoDaoUtil;

	/**
	 * 根据userId查询这个用户的数据权限
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/api/saveArticle", method=RequestMethod.POST)
    public ResponseVo saveArticle(@RequestBody ArticleVo articalVo) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setErrorMsg(null);
		
		if(articalVo != null) {
			Article article = (Article)SchameCopyUtil.simpleCopy(articalVo, Article.class);
			if(articalVo.getId() != null && !"".equals(articalVo.getId().trim())) {
				article.setUpdateTime(new Date());
				articleMongoDaoUtil.updateArticle(article);
			}else {
				article.setInsertTime(new Date());
				article.setUpdateTime(new Date());
				String id = articleMongoDaoUtil.insertArticle(article);
				articalVo.setId(id);
			}
			responseVo.setSuccess(true);
		}
		responseVo.setArticle(articalVo);
        return responseVo;
    }
	
	@RequestMapping(value="/api/queryArticle", method=RequestMethod.GET)
    public ResponseVo queryArticle(@RequestParam String id) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setErrorMsg(null);
		
		if(id != null) {
			Article article = articleMongoDaoUtil.findArticleById(id);
			if(article != null){
				ArticleVo articalVo = (ArticleVo)SchameCopyUtil.simpleCopy(article, ArticleVo.class);
				responseVo.setArticle(articalVo);
				
				responseVo.setSuccess(true);
			}else{
				responseVo.setErrorMsg("article not found.");;
				responseVo.setSuccess(false);
			}
		}
		
        return responseVo;
    }
	
}