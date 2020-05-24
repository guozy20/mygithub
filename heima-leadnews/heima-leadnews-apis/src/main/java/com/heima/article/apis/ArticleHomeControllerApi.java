package com.heima.article.apis;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ArticleHomeControllerApi {

    /**
     * 加载文章首页
     * @param dto
     * @return
     */
    public ResponseResult lead(ArticleHomeDto dto);

    /**
     * 加载更多的文章
     * @param dto
     * @return
     */
    public ResponseResult leadMore(ArticleHomeDto dto);

    /**
     * 加载最新的文章
     * @param dto
     * @return
     */
    public ResponseResult leadNew(ArticleHomeDto dto);

}
