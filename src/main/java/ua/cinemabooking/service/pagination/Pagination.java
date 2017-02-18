package ua.cinemabooking.service.pagination;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 2/18/2017.
 */
public class Pagination<T> implements Pageable {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(Pagination.class);

        @PersistenceContext
        private EntityManager em;

        private final Class genericType = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);

        @Override
        public List getAll(int start, int length) {
                List<T> result = new ArrayList<>();
                try {
                        CriteriaBuilder builder = em.getCriteriaBuilder();
                        CriteriaQuery<T> criteriaQuery = builder.createQuery(genericType);
                        Root<T> root = criteriaQuery.from(genericType);
                        criteriaQuery.select(root);
                        criteriaQuery.distinct(true);
                        result = toPage(start, length, criteriaQuery).getResultList();
                } catch (Exception e) {
                        LOGGER.error("ERROR: " + e.getMessage());
                }
                return result;
        }

        protected TypedQuery<T> toPage(int start, int length, CriteriaQuery<T> criteriaQuery) {
                final TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
                typedQuery
                        .setFirstResult(start)
                        .setMaxResults(length);
                return typedQuery;
        }
}
