package com.example.demo3.note;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.Authentication;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.appuser.AppUserService;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
public class NoteInfo {

		@Id
		@SequenceGenerator (
				name = "note_sequence",
				sequenceName = "note_sequence",
				allocationSize = 1
		)
		@GeneratedValue (
				strategy = GenerationType.SEQUENCE,
				generator = "note_sequence"
		)
		private long id;
		
		
		private Long userId;
		private int daysOld;
		@Transient 
		LocalDate timeCreated;
		
		private String title;
		private String description;
		private boolean special;

		
		public NoteInfo() {
			super();
		}

		public NoteInfo(String title, String description, boolean special, int daysOld, Long userId) {
			super();
			this.title = title;
			this.description = description;
			this.special = special;
			this.daysOld = daysOld;
			this.userId = userId;
		}
		
		public String getTitle() {
			return title;
		}

		@Override
		public String toString() {
			return "UserInfo [userId=" + userId + ", daysOld=" + daysOld + ", timeCreated=" + timeCreated + ", title="
					+ title + ", description=" + description + ", special=" + special + "]";
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}
		
		public Long getUserId() {
			return userId;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public boolean isSpecial() {
			return special;
		}

		public void setSpecial(boolean special) {
			this.special = special;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public LocalDate getTimeCreated() {
			return this.timeCreated;
		}

		public void setTimeCreated(LocalDate timeCreated) {
			this.timeCreated = timeCreated;
		}

		public int getDaysOld() {
			return daysOld;
			
		}

		public void setDaysOld(int daysOld) {
			this.daysOld = daysOld;	
		}
		
		
		public void setUserId(Long userId) {
			this.userId = userId;
		}
			
}
