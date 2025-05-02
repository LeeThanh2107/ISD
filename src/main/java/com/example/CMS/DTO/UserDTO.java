package com.example.CMS.DTO; // Or your preferred DTO package

import com.example.CMS.Common.GlobalConstants.Role;
import jakarta.validation.constraints.Email; // Keep if used for input validation
import jakarta.validation.constraints.NotBlank; // Example validation
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;   // Example validation

/**
 * Data Transfer Object (DTO) for the User entity.
 * Used for transferring user data without exposing the entity directly,
 * especially in API responses or requests, and excluding sensitive or
 * unnecessary fields like related entity lists or passwords (depending on use case).
 */
public class UserDTO {

    private Long userId; // Typically included for identifying the user, especially in responses

    @NotBlank(message = "Name cannot be blank") // Example validation
    @Size(max = 20, message = "Name must be less than or equal to 20 characters")
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username must be less than or equal to 50 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;


    @NotNull(message = "Role cannot be null")
    private Role role;

    // --- Constructors ---

    /**
     * Default constructor.
     */
    public UserDTO() {
    }

    /**
     * Parameterized constructor.
     * Note: Excludes password by default for response DTOs. Add it if needed for input.
     *
     * @param userId   The user's ID.
     * @param name     The user's full name.
     * @param username The user's username.
     * @param email    The user's email address.
     * @param role     The user's role.
     */
    public UserDTO(Long userId, String name, String username, String email, Role role) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // --- Getters and Setters ---

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
        return username;
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

    /* // Uncomment if password field is included above
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    */

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Optional: toString(), equals(), hashCode() methods can be added if needed.
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                // Avoid logging password if included: ", password='[PROTECTED]'" +
                ", role=" + role +
                '}';
    }
}