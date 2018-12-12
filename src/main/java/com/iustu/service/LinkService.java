package com.iustu.service;

import com.iustu.entity.Link;

import java.util.List;

public interface LinkService {
    List<Link> getLinkListByPage(int start, int rows);
    List<Link> getLinkList();
    void insertLink(Link link);
    void updateLink(Link link);
    void deleteLinkById(int id);
    Long getLinkCount();
}
