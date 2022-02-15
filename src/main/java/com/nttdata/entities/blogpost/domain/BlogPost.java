package com.nttdata.entities.blogpost.domain;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.codecs.pojo.annotations.BsonId;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A BlogPost.
 */
@MongoEntity(collection = "blog_post")
@RegisterForReflection
public class BlogPost extends PanacheMongoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 80)
    public String title;

    public String summary;

    public byte[] figure;

    @JsonbProperty("figure_content_type")
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
            ", figureContentType='" + figureContentType + "'" +
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
