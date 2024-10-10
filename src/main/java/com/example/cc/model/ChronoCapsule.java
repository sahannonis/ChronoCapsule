package com.example.cc.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "chrono_capsules")
public class ChronoCapsule {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String title;

	    @Column(length = 500)
	    private String description;

	    @Column(nullable = false)
	    private LocalDate unlockDate;

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    @JsonBackReference
	    private User user;
	 
	 public ChronoCapsule() {
	 }
	 
	 public ChronoCapsule(String title, String description, LocalDate unlockDate, User user) {
		 this.title = title;
	     this.description = description;
	     this.unlockDate = unlockDate;
	     this.user = user;
	 }
	 
	 public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public LocalDate getUnlockDate() {
	        return unlockDate;
	    }

	    public void setUnlockDate(LocalDate unlockDate) {
	        this.unlockDate = unlockDate;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }
}
