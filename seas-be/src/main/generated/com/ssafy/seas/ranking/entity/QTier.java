package com.ssafy.seas.ranking.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTier is a Querydsl query type for Tier
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTier extends EntityPathBase<Tier> {

    private static final long serialVersionUID = -873005902L;

    public static final QTier tier = new QTier("tier");

    public final com.ssafy.seas.common.entity.QBaseEntity _super = new com.ssafy.seas.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final NumberPath<Integer> maxScore = createNumber("maxScore", Integer.class);

    public final NumberPath<Integer> minScore = createNumber("minScore", Integer.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTier(String variable) {
        super(Tier.class, forVariable(variable));
    }

    public QTier(Path<? extends Tier> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTier(PathMetadata metadata) {
        super(Tier.class, metadata);
    }

}

