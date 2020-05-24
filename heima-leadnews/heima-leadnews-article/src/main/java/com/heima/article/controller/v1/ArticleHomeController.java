package com.heima.article.controller.v1;

import com.heima.article.apis.ArticleHomeControllerApi;
import com.heima.article.service.IAppArticleService;
import com.heima.common.common.contants.ArticleConstant;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;
import org.elasticsearch.search.aggregations.bucket.terms.DoubleTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController implements ArticleHomeControllerApi {

    @Autowired
    private IAppArticleService appArticleService;

    /**
     * 加载文章首页
     *
     * @param dto
     * @return
     */
    @Override
    @GetMapping("/load")
    public ResponseResult lead(ArticleHomeDto dto) {
        return appArticleService.load(dto, ArticleConstant.LOADTYPE_LOAD_MORE);
    }

    /**
     * 加载更多的文章
     *
     * @param dto
     * @return
     */
    @Override
    @GetMapping("/loadmore")
    public ResponseResult leadMore(ArticleHomeDto dto) {
        return appArticleService.load(dto, ArticleConstant.LOADTYPE_LOAD_MORE);
    }

    /**
     * 加载最新的文章
     *
     * @param dto
     * @return
     */
    @Override
    @GetMapping("/loadnew")
    public ResponseResult leadNew(ArticleHomeDto dto) {
        return appArticleService.load(dto, ArticleConstant.LOADTYPE_LOAD_NEW);
    }
}
