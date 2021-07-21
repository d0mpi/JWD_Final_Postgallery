package by.bsu.d0mpi.UP_PostGallery.service.impl;

import by.bsu.d0mpi.UP_PostGallery.service.FilterService;
import by.bsu.d0mpi.UP_PostGallery.service.FilterType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleFilterService implements FilterService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile SimpleFilterService instance;

    public static SimpleFilterService getInstance() {
        SimpleFilterService localInstance = instance;
        if (localInstance == null) {
            synchronized (SimpleFilterService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SimpleFilterService();
                }
            }
        }
        return localInstance;
    }

    @Override
    public String buildAndGetPageWithFiltersRequest(ArrayList<FilterType> filterTypeList) {
        String sqlFirstPart = "SELECT * FROM posts ";
        String sqlLastPart = "ORDER BY post_create_date DESC LIMIT ?, 10";

        StringBuilder sqlRequest = new StringBuilder(sqlFirstPart);
        boolean isFirst = true;
        if (filterTypeList.contains(FilterType.HASHTAG)) {
            sqlRequest.append(" left join hashtags on post_id=hashtags_post_id ");
            sqlRequest.append(" where hashtag_text = ?");
            isFirst = false;
        }
        if (filterTypeList.contains(FilterType.AUTHOR)) {
            if (!isFirst) {
                sqlRequest.append(" AND ");
            }
            else {
                sqlRequest.append(" where ");
            }
            sqlRequest.append("posts.post_author = ?");
            isFirst = false;
        }
        if (filterTypeList.contains(FilterType.DATE)) {
            if (!isFirst) {
                sqlRequest.append(" AND ");
            }
            else {
                sqlRequest.append(" where ");
            }
            sqlRequest.append("posts.post_create_date = ?");
            isFirst = false;
        }
        sqlRequest.append(sqlLastPart);
        return sqlRequest.toString();
    }
}
