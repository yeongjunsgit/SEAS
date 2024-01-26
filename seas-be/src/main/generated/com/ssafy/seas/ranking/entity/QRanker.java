package com.ssafy.seas.ranking.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRanker is a Querydsl query type for Ranker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRanker extends EntityPathBase<Ranker> {

    private static final long serialVersionUID = -1504430935L;

    public static final QRanker ranker = new QRanker("ranker");

    public final com.ssafy.seas.common.entity.QBaseEntity _super = new com.ssafy.seas.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final StringPath memberId = createString("memberId");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath tier = createString("tier");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRanker(String variable) {
        super(Ranker.class, forVariable(variable));
    }

    public QRanker(Path<? extends Ranker> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRanker(PathMetadata metadata) {
        super(Ranker.class, metadata);
    }

}

