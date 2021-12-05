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
import java.util.stream.Collectors;

@Service
@Transactional
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public List<String> getAllTags(int pageSize){
        return tagRepository.findAll(PageRequest.of(0, pageSize)).getContent()
                .stream().map(value -> new String(value.getName())).collect(Collectors.toList());
    }
}
