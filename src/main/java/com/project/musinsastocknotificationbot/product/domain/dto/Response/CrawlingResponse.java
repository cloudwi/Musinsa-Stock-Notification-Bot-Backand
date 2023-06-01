package com.project.musinsastocknotificationbot.product.domain.dto.Response;

public record CrawlingResponse(
    String title,
    String imageUrl
) {

  public static CrawlingResponse from(String title, String imageUrl) {
    return new CrawlingResponse(title, imageUrl);
  }
}
