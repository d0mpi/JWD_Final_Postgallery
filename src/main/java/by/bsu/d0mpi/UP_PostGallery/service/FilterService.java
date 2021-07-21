package by.bsu.d0mpi.UP_PostGallery.service;

import by.bsu.d0mpi.UP_PostGallery.service.impl.SimpleFilterService;

import java.util.ArrayList;

public interface FilterService {
    static FilterService simple() {
        return SimpleFilterService.getInstance();
    }


    String buildAndGetPageWithFiltersRequest(ArrayList<FilterType> filters);

    String buildAndGetPostsCountWithFiltersRequest(ArrayList<FilterType> filterTypeList);
}
