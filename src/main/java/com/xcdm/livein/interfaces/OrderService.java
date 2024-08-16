package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.OrderDTO;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.pagination.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDTO saveOrder();

    PaginatedResponse<OrderDTO> getOrdersAndFormResult(int limit, int offset, HttpServletRequest request);
}
