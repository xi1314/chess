package chess.domain.model.article;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by YJH on 2016/4/15.
 */
public interface IArticleRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
