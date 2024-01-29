package com.ssafy.seas.category.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = 242283196L;

    public static final QCategory category = new QCategory("category");

    public final com.ssafy.seas.common.entity.QBaseEntity _super = new com.ssafy.seas.common.entity.QBaseEntity(this);

    public final StringPath backgroundColor = createString("backgroundColor");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final StringPath lineColor = createString("lineColor");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> quizCount = createNumber("quizCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

