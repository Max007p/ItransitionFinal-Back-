package services;

import entities.Tag;
import entities.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repositories.ReviewRepository;
import repositories.TagRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public List<Tag> getAllTags(int pageSize){
        return tagRepository.findAll(PageRequest.of(0, pageSize)).getContent();
    }
}
