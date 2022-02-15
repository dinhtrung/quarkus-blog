package com.nttdata.web.rest;

import com.nttdata.domain.BlogPost;
import com.nttdata.repository.BlogPostRepository;
import com.nttdata.service.Paged;
import com.nttdata.web.rest.errors.BadRequestAlertException;
import com.nttdata.web.rest.vm.PageRequestVM;
import com.nttdata.web.rest.vm.SortRequestVM;
import com.nttdata.web.util.HeaderUtil;
import com.nttdata.web.util.PaginationUtil;
import com.nttdata.web.util.ResponseUtil;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

import static javax.ws.rs.core.UriBuilder.fromPath;

/**
 * REST controller for managing {@link com.nttdata.domain.BlogPost}.
 */
@Path("/api/blog-posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BlogPostResource {

    private static final String ENTITY_NAME = "blogPost";
    private final Logger log = LoggerFactory.getLogger(BlogPostResource.class);
    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    BlogPostRepository blogPostRepository;

    /**
     * {@code POST  /blog-posts} : Create a new blogPost.
     *
     * @param blogPost the blogPost to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new blogPost, or with status {@code 400 (Bad Request)} if the blogPost has already an ID.
     */
    @POST
    public Response createBlogPost(@Valid BlogPost blogPost, @Context UriInfo uriInfo) {
        log.debug("REST request to save BlogPost : {}", blogPost);
        if (blogPost.id != null) {
            throw new BadRequestAlertException("A new blogPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        blogPostRepository.persistOrUpdate(blogPost);
        BlogPost result = BlogPost.findById(blogPost.id);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /blog-posts} : Updates an existing blogPost.
     *
     * @param blogPost the blogPost to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated blogPost,
     * or with status {@code 400 (Bad Request)} if the blogPost is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogPost couldn't be updated.
     */
    @PUT
    public Response updateBlogPost(@Valid BlogPost blogPost) {
        log.debug("REST request to update BlogPost : {}", blogPost);
        if (blogPost.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        blogPostRepository.persistOrUpdate(blogPost);
        var response = Response.ok().entity(blogPost);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogPost.id).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /blog-posts/:id} : delete the "id" blogPost.
     *
     * @param id the id of the blogPost to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteBlogPost(@PathParam("id") String id) {
        log.debug("REST request to delete BlogPost : {}", id);
        blogPostRepository.findByIdOptional(new ObjectId(id)).ifPresent(blogPost -> {
            blogPostRepository.delete(blogPost);
        });
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /blog-posts} : get all the blogPosts.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of blogPosts in body.
     */
    @GET
    public Response getAllBlogPosts(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of BlogPosts");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        var result = new Paged<>(blogPostRepository.findAll(sort).page(page));
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /blog-posts/:id} : get the "id" blogPost.
     *
     * @param id the id of the blogPost to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the blogPost, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getBlogPost(@PathParam("id") String id) {
        log.debug("REST request to get BlogPost : {}", id);
        Optional<BlogPost> blogPost = blogPostRepository.findByIdOptional(new ObjectId(id));
        return ResponseUtil.wrapOrNotFound(blogPost);
    }
}
