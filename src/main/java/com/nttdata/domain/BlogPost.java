package com.nttdata.domain;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.codecs.pojo.annotations.BsonId;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A BlogPost.
 */
@MongoEntity(collection="blog_post")
@RegisterForReflection
public class BlogPost extends PanacheMongoEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @BsonId
    public String id;

    @NotNull
    @Size(max = 80)
    public String title;

     public String summary;

    public byte[] figure;

    @Field("figure_content_type")
      public String figureContentType;

     public String content;

    public String category;

    @NotNull
    @Size(max = 255)
    public String slug;

    @NotNull
    @Min(value = 0)
    @Max(value = 9)
    public Integer state;

    public Float weight;

    public LocalDate publishedAt;

    public Instant createdAt;

    public Instant lastModifiedAt;

    public String createdBy;

    public String lastModifiedBy;

    public String tags;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogPost)) {
            return false;
        }
        return id != null && id.equals(((BlogPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", summary='" + summary + "'" +
            ", figure='" + figure + "'" +
            ", figureContentType='" + figureContentType() + "'" +
            ", content='" + content + "'" +
            ", category='" + category + "'" +
            ", slug='" + slug + "'" +
            ", state=" + state +
            ", weight=" + weight +
            ", publishedAt='" + publishedAt + "'" +
            ", createdAt='" + createdAt + "'" +
            ", lastModifiedAt='" + lastModifiedAt + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            ", tags='" + tags + "'" +
            "}";
    }
}