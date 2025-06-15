package com.project.organizer.dao;

import com.project.organizer.model.Literature;
import java.util.List;

public interface LiteratureDao {
    void save(Literature literature);
    Literature findById(Long id);
    List<Literature> findAll();
    void delete(Long id);
}
