package com.bustle.bustlebackend.repository;

import com.bustle.hackto2022.model.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<EventPost, Integer> {
    EventPost getByEventId(int Id);
}
