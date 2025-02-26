package com.teamflow.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // 유저 아이디

  @Column(unique = true, nullable = false)
  private String username; // 닉네임
  private String userid; // 아이디

  @JsonIgnore
  @Column(nullable = false)
  private String password; // 비밀번호

  private String email;
  private String position;
  private String contactTime;

  @ElementCollection
  @CollectionTable(name = "user_teams", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "team_name")
  private List<String> myTeam;

  private String profile;
}
