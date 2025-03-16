package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.PostSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Post;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.PostRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Transactional
    public String deactivatePost(String id, String userId){
        if(postRepository.deactivatePost(id,userId)<1)
            throw new FraudulentRequestException("Forbidden request. You can't manipulate other user's data.");
        return "success";
    }

    public PostSummaryDTO[] getAllUserActivePosts(String userId, String table){
        List<Post> posts;
        if(table.equals("Bid"))
            posts=postRepository.getAllUserActiveBids(userId);
        else posts=postRepository.getAllUserActiveAsks(userId);
        if(posts.isEmpty())
            return new PostSummaryDTO[0];
        final PostSummaryDTO[] summary=new PostSummaryDTO[posts.size()];
        for (int post = 0; post < summary.length; post++) {
            summary[post]=postMapper.toSummaryDTO(posts.get(post));
        }
        return summary;
    }

}
