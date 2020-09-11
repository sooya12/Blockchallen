package com.ssafy.blockchallen.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Challenge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Column(name = "start_date")
    @Temporal(TemporalType.DATE)
	private Date startDate;
    
	@Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    private Integer fee;
    
    @Column(name = "is_random")
    private Boolean isRandom;
    
    @OneToMany(mappedBy = "challenge")
    private Set<Certification> certifications;
    

	@OneToMany(mappedBy = "challenge")
	private Set<ChallengeAccount> challengeaccounts;

}
