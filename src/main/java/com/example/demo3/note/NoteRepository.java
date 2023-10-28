package com.example.demo3.note;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteInfo, Long> {
	
	@Query("SELECT s FROM NoteInfo s WHERE s.title = ?1")
	Optional<NoteInfo> findNoteByTitle(String title);
	
	@Query("SELECT s FROM NoteInfo s WHERE s.title = ?1")
	Optional<NoteInfo> findNoteById(Long Id);
	
	List<NoteInfo> findByUserId(Long userId);

}
