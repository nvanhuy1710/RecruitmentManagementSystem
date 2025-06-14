package com.app.homeworkoutapplication.module.article.dto.event;

import com.app.homeworkoutapplication.common.Event;
import com.app.homeworkoutapplication.module.article.dto.Article;

public class ApproveArticleEvent extends Event<Article> {

    public ApproveArticleEvent(Object source, Article article) {
        super(source, article);
    }

    @Override
    public String getName() {
        return "article.approved.event";
    }

    @Override
    public String getType() {
        return "article";
    }
}
