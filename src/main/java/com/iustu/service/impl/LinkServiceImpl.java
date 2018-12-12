package com.iustu.service.impl;

import com.iustu.entity.Link;
import com.iustu.entity.LinkExample;
import com.iustu.mapper.LinkMapper;
import com.iustu.mapper.custom.LinkMapperCustom;
import com.iustu.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;
    @Autowired
    private LinkMapperCustom linkMapperCustom;

    @Override
    public List<Link> getLinkListByPage(int start, int rows) {
        return linkMapperCustom.getLinkListByPage(start, rows);
    }

    @Override
    public List<Link> getLinkList() {
        return linkMapperCustom.getLinkListOrdered();
    }

    @Override
    public void insertLink(Link link) {
        linkMapper.insert(link);
    }

    @Override
    public void updateLink(Link link) {
        linkMapper.updateByPrimaryKey(link);
    }

    @Override
    public void deleteLinkById(int id) {
        linkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Long getLinkCount() {
        LinkExample example = new LinkExample();
        return linkMapper.countByExample(example);
    }
}
