package com.nttdata.entities.blogpost.repository;

import com.nttdata.entities.blogpost.domain.BlogPost;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import javax.enterprise.context.ApplicationScoped;

/**
 * Hibernate PanacheMongoDB repository for the BlogPost entity.
 */
@SuppressWarnings("unused")
@ApplicationScoped
public class BlogPostRepository implements PanacheMongoRepository<BlogPost> {


}
