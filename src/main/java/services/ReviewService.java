package services;

import entities.Review;
import entities.response.ReviewResponse;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repositories.ReviewRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @PersistenceContext
    EntityManager entityManager;

    public List<ReviewResponse> getMostPopularReviews(int pageSize){
        return reviewRepository.getPopularReviews(PageRequest.of(0, pageSize));
    }

    public List<ReviewResponse> getLatestReviews(int pageSize){
        return reviewRepository.getLatestReviews(PageRequest.of(0, pageSize));
    }

    public List<Review> getFilteredReviews(String text) throws InterruptedException {
        System.out.println(text);
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();
        QueryBuilder queryBuilder = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Review.class)
                .get();
        Query query = queryBuilder
                .keyword()
                .onFields("text", "title", "comments.text")
                .matching(text)
                .createQuery();
        FullTextQuery fullTextQuery = fullTextEntityManager
                .createFullTextQuery(query, Review.class);
        fullTextEntityManager.close();
        return (List<Review>) fullTextQuery.getResultList();
    }
}
