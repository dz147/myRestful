package com.dz147.dao;

import com.dz147.entity.Post;
import java.util.List;

public interface PostMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Post record);

    Post selectByPrimaryKey(Integer pid);

    List<Post> selectAll();

    int updateByPrimaryKey(Post record);
}