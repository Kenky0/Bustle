package com.bustle.bustlebackend.repository;

import com.bustle.hackto2022.model.SeekDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // check this out later;
public abstract class InvitationRepository implements JpaRepository<SeekDiscussion, Integer> {
}