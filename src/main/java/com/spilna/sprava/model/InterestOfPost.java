package com.spilna.sprava.model;

import javax.persistence.*;

/**
 * Created by Ivanov on 05.11.15.
 */
@Entity
@Table(name = "interest_of_posts")
public class InterestOfPost {
    @Id// Id describe table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private String id;
    @Column(name = "post_id")// Definition of the column name
    private String idPost;
    private String interest;
    private PostInf postInf = new PostInf();

    public InterestOfPost() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="id_post")
    public PostInf getPostInf() {
        return postInf;
    }

    public void setPostInf(PostInf postInf) {
        this.postInf = postInf;
    }
}
