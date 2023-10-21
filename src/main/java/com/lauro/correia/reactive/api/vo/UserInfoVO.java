package com.lauro.correia.reactive.api.vo;

import java.util.List;

public record UserInfoVO(String userId, String name, String email, AddressVO address, String phone, String website, CompanyVO company, List<AlbumVO> album, List<PostVO> post) {
}
