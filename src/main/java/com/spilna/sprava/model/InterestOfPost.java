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
    private String interest;
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="post_id")
    private PostInf postInf;

    public InterestOfPost() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public PostInf getPostInf() {
        return postInf;
    }

    public void setPostInf(PostInf postInf) {
        this.postInf = postInf;
    }
}
