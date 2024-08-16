package com.xcdm.livein.pagination;

import java.util.List;
import java.util.stream.Collectors;

public class PaginationUtil {

    public static <T> PaginatedResponse<T> paginate(
            List<T> items, int limit, int offset, String baseUrl) {

        if (limit <= 0) {
            limit = 10;
        }
        if (offset < 0) {
            offset = 0;
        }

        int totalItems = items.size();

        int fromIndex = Math.min(offset, totalItems);
        int toIndex = Math.min(offset + limit, totalItems);

        List<T> paginatedItems = items.subList(fromIndex, toIndex);

        PaginatedResponse<T> response = new PaginatedResponse<>();
        response.setCount(totalItems);
        response.setNext(offset + limit < totalItems ? generateNextUrl(baseUrl, offset + limit, limit) : null);
        response.setPrevious(offset > 0 ? generatePreviousUrl(baseUrl, offset - limit, limit) : null);
        response.setResults(paginatedItems);

        return response;
    }

    private static String generateNextUrl(String baseUrl, int nextOffset, int limit) {
        return String.format("%s?offset=%d&limit=%d", baseUrl, nextOffset, limit);
    }

    private static String generatePreviousUrl(String baseUrl, int previousOffset, int limit) {
        return String.format("%s?offset=%d&limit=%d", baseUrl, Math.max(previousOffset, 0), limit);
    }
}
