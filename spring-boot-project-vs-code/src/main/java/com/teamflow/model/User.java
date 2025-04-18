package com.teamflow.model;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // 유저 아이디

  @Column(unique = true, nullable = false)
  private String userId; // 로그인 시 사용하는 유저 아이디

  @Column(unique = true, nullable = false)
  private String username; // 닉네임

  @JsonIgnore
  @Column(nullable = false)
  private String password; // 비밀번호

  private String email;
  private String position;
  private String contactTime;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<TeamMembers> teamMembers = new ArrayList<>();


  private String profile;

  private String myColor;
}
