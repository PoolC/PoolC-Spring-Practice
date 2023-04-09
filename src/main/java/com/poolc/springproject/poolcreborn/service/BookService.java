package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.book.Book;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.book.BookRequest;
import com.poolc.springproject.poolcreborn.payload.request.book.BookSearchRequest;
import com.poolc.springproject.poolcreborn.payload.response.book.BookDto;
import com.poolc.springproject.poolcreborn.repository.BookRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.util.BookMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    @Value("${poolcreborn.app.clientId}")
    private String clientId;
    @Value("${poolcreborn.app.clientSecret}")
    private String clientSecret;

    public void saveBook(BookRequest bookRequest, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.isAdmin()) {
            Book book = new Book();
            bookMapper.buildBookFromRequest(bookRequest, book);
            bookRepository.save(book);
        }
    }

    public void deleteBook(Long bookId, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.isAdmin()) {
            Optional<Book> book = bookRepository.findById(bookId);
            book.ifPresent(bookRepository::delete);
        }
    }

    public List<BookDto> naverBookSearchApi(BookSearchRequest bookSearchRequest) {
        String url = "https://openapi.naver.com/";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("v1/search/book.json")
                .queryParam("query", bookSearchRequest.getQuery())
                .queryParam("display", bookSearchRequest.getDisplay())
                .queryParam("start", bookSearchRequest.getStart())
                .queryParam("sort", bookSearchRequest.getSort())
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        List<BookDto> bookDtoList = fromJSONtoBookDtoList(result.getBody());

        return bookDtoList;

    }
    public List<BookDto> fromJSONtoBookDtoList(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray naverBooks = rjson.getJSONArray("items");
        List<BookDto> naverBookDtoList = new ArrayList<>();
        for (int i = 0; i < naverBookDtoList.size(); i++) {
            JSONObject naverBooksJson = (JSONObject) naverBooks.get(i);
            BookDto itemDto = new BookDto(naverBooksJson);
            naverBookDtoList.add(itemDto);
        }
        return naverBookDtoList;
    }

}
