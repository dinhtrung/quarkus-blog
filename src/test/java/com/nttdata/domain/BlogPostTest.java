package com.nttdata.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.TestUtil;
import org.junit.jupiter.api.Test;


public class BlogPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogPost.class);
        BlogPost blogPost1 = new BlogPost();
        blogPost1.id = "id1";
        BlogPost blogPost2 = new BlogPost();
        blogPost2.id = blogPost1.id;
        assertThat(blogPost1).isEqualTo(blogPost2);
        blogPost2.id = "id2";
        assertThat(blogPost1).isNotEqualTo(blogPost2);
        blogPost1.id = null;
        assertThat(blogPost1).isNotEqualTo(blogPost2);
    }
}