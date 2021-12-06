package utils;

import entities.Group;
import entities.Review;
import entities.Tag;
import entities.request.ReviewRequest;
import entities.response.ReviewResponse;
import enums.ReviewGroups;
import lombok.experimental.UtilityClass;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.codehaus.commons.nullanalysis.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.GroupRepository;
import repositories.ReviewRepository;
import repositories.TagRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class ReviewUtils {

    public static ReviewResponse convertReviewToReviewResponse(Review review) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Review.class, ReviewResponse.class)
                .field("id", "reviewId").field("title", "title")
                .field("text", "text").field("creation", "creation")
                .field("reviewGroups", "reviewGroups").field("reviewTags", "reviewTags")
                .field("reviewRating", "reviewRating")
                .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        ReviewResponse reviewResponse = mapper.map(review, ReviewResponse.class);
        reviewResponse.setReviewGroups(convertGroupSetToStringList(review.getReviewGroups()));
        reviewResponse.setReviewTags(convertTagSetToStringList(review.getReviewTags()));
        return reviewResponse;
    }

    public static Review convertReviewRequestToReview(ReviewRequest review){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(ReviewRequest.class, Review.class)
                .field("reviewId", "id").field("reviewRating", "reviewRating")
                .field("title", "title").field("text", "text")
                .field("reviewGroups", "reviewGroups").field("reviewTags", "reviewTags")
                .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Review reviewToDb = mapper.map(review, Review.class);
        reviewToDb.setReviewTags(convertTagStringListToSet(review.getReviewTags()));
        reviewToDb.setReviewGroups(convertGroupStringListToSet(review.getReviewGroups()));
        return reviewToDb;
    }

    public static List<String> convertGroupSetToStringList(Set<Group> set){
        return set.stream()
                .map(value -> new String(value.getGroups().name()))
                .collect(Collectors.toList());
    }

    public static List<String> convertTagSetToStringList(Set<Tag> set){
        return set.stream()
                .map(value -> new String(value.getName()))
                .collect(Collectors.toList());
    }

    public static Set<Tag> convertTagStringListToSet(List<String> list){
        Set<Tag> tags = new HashSet<>();
        for (String value: list){
            tags.add(new Tag(value));
        }
        return tags;
    }

    public static Set<Group> convertGroupStringListToSet(List<String> list){
        Set<Group> groups = new HashSet<>();
        for (String value: list){
            groups.add(new Group(ReviewGroups.valueOf(value)));
        }
        return groups;
    }
}
