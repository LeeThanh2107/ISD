package com.example.CMS.Services.Admin;

import com.example.CMS.Common.ConvertToDTO;
import com.example.CMS.Common.GlobalConstants.Status;
import com.example.CMS.Common.IdEncryptor;
import com.example.CMS.DTO.ArticleDto;
import com.example.CMS.DTO.ViewsDto;
import com.example.CMS.Model.Article;
import com.example.CMS.Repository.ArticleRepository;
import com.example.CMS.Repository.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.example.CMS.Common.ConvertToDTO.convertToDto;

@Service
public class AdminArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ViewsRepository viewsRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public List<ArticleDto> index() {
        return articleRepository.findAllByStatus(Status.PUBLISHED).stream()
                .map(ConvertToDTO::convertToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<ArticleDto> indexParallel() {
        List<CompletableFuture<ArticleDto>> futures = articleRepository.findAll().stream()
                .map(article -> CompletableFuture.supplyAsync(() -> convertToDto(article), executorService))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public ArticleDto detail(String id) throws Exception {
        Long decryptedId = IdEncryptor.decrypt(id);
        Article article = articleRepository.getReferenceById(decryptedId);
        return convertToDto(article);
    }
    // Cleanly shutdown thread pool
    public void shutdown() {
        executorService.shutdown();
    }
}
