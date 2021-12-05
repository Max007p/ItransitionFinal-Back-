package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repositories.GroupRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    public List<String> getAllGroups(int pageSize){
        return groupRepository.findAll(PageRequest.of(0, pageSize)).getContent()
                .stream().map(value -> new String(value.getGroups().name())).collect(Collectors.toList());
    }
}
