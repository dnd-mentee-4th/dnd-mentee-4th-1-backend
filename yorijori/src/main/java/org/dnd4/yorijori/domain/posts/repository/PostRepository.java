package org.dnd4.yorijori.domain.posts.repository;

import lombok.RequiredArgsConstructor;
import org.dnd4.yorijori.domain.posts.entity.Posts;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(Posts post){
        if(post.getId() == null) em.persist(post);
        else em.merge(post);
    }

}
