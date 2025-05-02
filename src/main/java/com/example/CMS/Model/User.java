package com.example.CMS.Model;

import com.example.CMS.Common.GlobalConstants.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50)
    private String username;
    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;


    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE)
    private List<Article> writerArticleList;
    @OneToMany(mappedBy = "editor")
    private List<Article> editorArticleList;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return  username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Article> getWriterArticleList() {
        return writerArticleList;
    }

    public void setWriterArticleList(List<Article> writerArticleList) {
        this.writerArticleList = writerArticleList;
    }

    public List<Article> getEditorArticleList() {
        return editorArticleList;
    }

    public void setEditorArticleList(List<Article> editorArticleList) {
        this.editorArticleList = editorArticleList;
    }
}
