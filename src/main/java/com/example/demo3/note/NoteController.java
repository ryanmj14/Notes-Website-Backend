package com.example.demo3.note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/note")
public class NoteController {

	private final NoteService noteService;
	
	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}
	
	@GetMapping
	public List<NoteInfo> getInfo() {
		return noteService.getNoteInfo();		
	}
	
	@PostMapping
	public void createNoteInfo(@RequestBody NoteInfo noteInfo, Authentication authentication) {
		noteService.addNewNote(noteInfo);
	}
	
	@DeleteMapping(path = "/delete/{noteId}")
	public void deleteNote(@PathVariable("noteId") Long noteId, Authentication authentication) {
		noteService.deleteNote(noteId, authentication);
	}
	
	@PutMapping(path = "/updateTitle/{noteId}")
	public void updateNoteTitle(
			@PathVariable("noteId") Long noteId,
			@RequestBody String title,
			Authentication authentication) {
		noteService.updateNoteTitle(title, noteId, authentication);
	}
	
	@PutMapping(path = "/updateDescription/{noteId}")
	public void updateNoteDescription(
			@PathVariable("noteId") Long noteId,
			@RequestBody String description) {
		noteService.updateNoteDescription(description, noteId);		
	}
	
	@PutMapping(path = "/updateSpecial/{noteId}")
	public void updateNoteSpecial(
			@PathVariable("noteId") Long noteId,
			@RequestBody Boolean special) {
		noteService.updateNoteSpecial(special, noteId);	
	
	}
	
}
