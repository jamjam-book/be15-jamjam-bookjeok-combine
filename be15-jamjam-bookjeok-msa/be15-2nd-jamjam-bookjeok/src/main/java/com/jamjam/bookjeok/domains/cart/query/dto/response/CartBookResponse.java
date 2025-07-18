package com.jamjam.bookjeok.domains.cart.query.dto.response;

import lombok.Builder;

@Builder
public record CartBookResponse(
        Long bookId, String bookName,
        int quantity, int totalPrice, String imageUrl
) {
}