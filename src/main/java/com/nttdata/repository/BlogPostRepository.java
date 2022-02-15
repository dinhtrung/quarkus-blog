package com.nttdata.repository;

import com.nttdata.domain.BlogPost;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import javax.enterprise.context.ApplicationScoped;

/**
 * Hibernate PanacheMongoDB repository for the BlogPost entity.
 */
@SuppressWarnings("unused")
@ApplicationScoped
public class BlogPostRepository implements PanacheMongoRepository<BlogPost> {


}
