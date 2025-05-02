package com.example.CMS.Services;

import com.example.CMS.Common.GlobalConstants.Role;
import com.example.CMS.DTO.UserDTO;
import com.example.CMS.Model.Article;
import com.example.CMS.Model.User;
import com.example.CMS.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class); // Logger instance

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<UserDTO> getAllUser() {
        // 1. Fetch all User entities
        List<User> userEntities = userRepository.findAll();

        // 2. Use stream and map with inline constructor call
        //    (Assumes UserDTO has a constructor like:
        //     UserDTO(Long userId, String name, String username, String email, Role role))
        return userEntities.stream()
                .map(user -> new UserDTO( // Create DTO directly in the lambda
                        user.getUserId(),
                        user.getName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()
                        // Ensure password/lists are NOT passed if constructor includes them
                ))
                .collect(Collectors.toList());
    }
    public User detail(Long id){
        return userRepository.getReferenceById(id);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void store(User user){
        userRepository.save(user);
    }
    public void resetPassword(User user){
        userRepository.save(user);

    }
    @Transactional
    public void delete(Long id){
        User userToDelete = userRepository.findByUserId(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new EntityNotFoundException("User not found with id: " + id);
                });

        // 2. Manually handle articles where the user is the EDITOR
        //    Set their 'editor' foreign key to NULL using a bulk update for efficiency.
        //    (Requires nullable editor_id column in DB)
        log.debug("Nullifying editor reference for articles edited by user id: {}", id);
        List<Article> articlesToUpdate = entityManager.createQuery("SELECT a FROM Article a WHERE a.editor = :user", Article.class)
                .setParameter("user", userToDelete)
                .getResultList();
        log.debug("Found {} articles with user {} as editor before bulk update.", articlesToUpdate.size(), id);
        String updateEditorJpql = "UPDATE Article a SET a.editor = null WHERE a.editor.id = :userId";
        int updatedEditorCount = 0;
        if(userToDelete.getRole() == Role.EDITOR){
            try {
                // *** Optional but Recommended: Flush before bulk update ***
                // log.debug("Flushing EntityManager before bulk update...");
                // entityManager.flush();
                // log.debug("EntityManager flushed.");

                // *** 2. Parameter: Pass the ID value ***
                updatedEditorCount = entityManager.createQuery(updateEditorJpql)
                        .setParameter("userId", userToDelete.getUserId()) // Pass the Long ID
                        .executeUpdate();

                log.info("Executed bulk update. Set editor to null for {} articles previously edited by user id: {}", updatedEditorCount, id);

                // Optional but Recommended: Clear persistence context AFTER bulk update if needed
                 entityManager.clear();

            } catch (Exception e) {
                log.error("Error executing bulk update to nullify editor for user id {}: {}", id, e.getMessage(), e);
                // Re-throw or handle as appropriate
                throw new RuntimeException("Failed to nullify editor references for user " + id, e);
            }
        }
        userRepository.delete(userToDelete);
        log.info("Set editor to null for {} articles previously edited by user id: {}", updatedEditorCount, id);
    }
}
