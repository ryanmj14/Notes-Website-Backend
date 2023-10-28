package com.example.demo3.note;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.appuser.AppUserRepository;

@Service
public class NoteService {
	
	private final NoteRepository noteRepository;

	AppUser appUser;
	
	@Autowired
	AppUserRepository appUserRepository;
	
	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}
	
	public List<NoteInfo> getNoteInfo() {
		List<NoteInfo> resultList = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String email = userDetails.getUsername();
		Optional<AppUser> thisUser = appUserRepository.findByEmail(email);
		if(thisUser.isPresent()) {
			Long userId = thisUser.get().getId();
			resultList = noteRepository.findByUserId(userId);
		}
		return resultList;
	}

	public void addNewNote(NoteInfo noteInfo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String email = userDetails.getUsername();
		Optional<AppUser> thisUser = appUserRepository.findByEmail(email);
		if(thisUser.isPresent()) {
			Long userId = thisUser.get().getId();
			noteInfo.setUserId(userId);
			noteRepository.save(noteInfo);
		}	
	}

	public void deleteNote(Long noteId, Authentication authentication) {
		NoteInfo note = noteRepository.findById(noteId)
			.orElseThrow(() -> new IllegalStateException(
				"Note with ID " + noteId + " does not exist"));;
				
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String email = userDetails.getUsername();
		Optional<AppUser> thisUser = appUserRepository.findByEmail(email);
		
		if(thisUser.isPresent()) {
			Long userId = thisUser.get().getId();
			if(note.getUserId() == userId) {
				noteRepository.deleteById(noteId);
			} else {
				throw new IllegalStateException("User does not have access to note with ID " + noteId);
			}		
		} else {
			throw new IllegalStateException("User not found");
		}
	}
	
	@Transactional
	public void updateNoteTitle(String title, Long noteId, Authentication authentication) {
		NoteInfo note =  noteRepository.findById(noteId)
			.orElseThrow(() -> new IllegalStateException(
				"Note with ID " + noteId + " does not exist"));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String email = userDetails.getUsername();
		Optional<AppUser> thisUser = appUserRepository.findByEmail(email);
		if(thisUser.isPresent()) {
			Long userId = thisUser.get().getId();
			if(note.getUserId() != userId) {
				throw new IllegalStateException("User does not have access to note with ID " + noteId);
			}
			if(title != null && title.length() > 0 && !title.equals(note.getTitle())) {
				note.setTitle(title);
				noteRepository.save(note);
			} else {
				throw new IllegalStateException("invalid new title");
			}
		}
	}
	
	@Transactional
	public void updateNoteDescription(String description, Long noteId) {
		NoteInfo note = noteRepository.findById(noteId)
			.orElseThrow(() -> new IllegalStateException(
				"Note with ID " + noteId + " does not exist"));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String email = userDetails.getUsername();
		Optional<AppUser> thisUser = appUserRepository.findByEmail(email);
		if(thisUser.isPresent()) {
			Long userId = thisUser.get().getId();
			if(note.getUserId() != userId) {
				throw new IllegalStateException("User does not have access to note with ID " + noteId);
			}
			if(!description.equals(note.getDescription()))
			{
				note.setDescription(description);
				noteRepository.save(note);				
			} else {
				throw new IllegalStateException("New description is not valid");
			}
		}
	}
	
	@Transactional
	public void updateNoteSpecial(Boolean special, Long noteId) {
		NoteInfo note = noteRepository.findById(noteId)
			.orElseThrow(() -> new IllegalStateException(
				"Note with ID " + noteId + " does not exist"));

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String email = userDetails.getUsername();
		Optional<AppUser> thisUser = appUserRepository.findByEmail(email);
		if(thisUser.isPresent()) {
			Long userId = thisUser.get().getId();
			if(note.getUserId() != userId) {
				throw new IllegalStateException("User does not have access to note with ID " + noteId);
			}
			note.setSpecial(special);
			noteRepository.save(note);
		}
	}
	
}
