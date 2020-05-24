package com.heima.article.service.impl;

import com.heima.article.service.IAppArticleService;
import com.heima.common.common.contants.ArticleConstant;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.mappers.app.ApArticleMapper;
import com.heima.model.mappers.app.ApUserArticleListMapper;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserArticleList;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class AppArticleServiceImpl implements IAppArticleService {

    private static final short MAX_PAGE_SIZE = 50;

    @Autowired
    private ApArticleMapper articleMapper;

    @Autowired
    private ApUserArticleListMapper apUserarticleListMapper;

    /**
     * type 1-加载更多  2加载最新
     *
     * @param dto
     * @param type
     * @return
     */
    @Override
    public ResponseResult load(ArticleHomeDto dto, Short type) {

        // 参数校验
        if(dto == null){
            dto = new ArticleHomeDto();
        }
        // 时间校验
        if (dto.getMaxBehotTime() == null) {
            dto.setMaxBehotTime(new Date());
        }
        if (dto.getMinBehotTime() == null) {
            dto.setMinBehotTime(new Date());
        }
        Integer size = dto.getSize();
        if (size == null || size <= 0) {
            size = 20;
        }
        size = Math.min(size, MAX_PAGE_SIZE);
        dto.setSize(size);

        // 文章频道参数校验
        if (StringUtils.isBlank(dto.getTag())) {
            dto.setTag(ArticleConstant.DEFAULT_TAG);
        }

        // 类型参数的校验
        if (!type.equals(ArticleConstant.LOADTYPE_LOAD_MORE)
                && !type.equals(ArticleConstant.LOADTYPE_LOAD_NEW)) {
            type = ArticleConstant.LOADTYPE_LOAD_MORE;
        }

        // 获取用户的信息
        ApUser user = AppThreadLocalUtils.getUser();

        // 判断用户是否存在
        if(user != null){
            // 用户存在 已登录 加载推荐的文章
            List<ApArticle> apArticleList = getUserArticle(user, dto, type);
            return ResponseResult.okResult(apArticleList);
        } else {
            // 用户不存在 未登录 加载默认的文章
            List<ApArticle> apArticles = getDefaultArticle(dto, type);
            return ResponseResult.okResult(apArticles);
        }

    }

    /**
     * 加载默认文章信息
     * @param dto
     * @param type
     * @return
     */
    private List<ApArticle> getDefaultArticle(ArticleHomeDto dto, Short type) {
        return articleMapper.loadArticleListByLocation(dto, type);
    }

    /**
     * 先从用户的推荐表中查找文章信息，如果没有在从默认文章信息获取数据
     * @param user
     * @param dto
     * @param type
     * @return
     */
    private List<ApArticle> getUserArticle(ApUser user, ArticleHomeDto dto, Short type) {
        List<ApUserArticleList> list = apUserarticleListMapper.loadArticleIdListByUser(user, dto, type);
        if (list != null && list.size() > 0) {
            return articleMapper.loadArticleListByIdList(list);
        } else {
            return getDefaultArticle(dto, type);
        }
    }
}
