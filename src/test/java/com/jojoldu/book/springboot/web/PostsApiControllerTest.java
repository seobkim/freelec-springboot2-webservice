package com.jojoldu.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)

// Api Controller를 테스트하는데 HelloController와 달리 @WebMvcTest를 사용하지 않았음.
// @WebMvcTest 의 경우 JPA 기능이 작동하지 않기 때문인데, Controller 와 ControllerAdvice 등 외부 연동과 관련된 부분만
// 활성화되니 지금 같이 JPA 기능까지 한번에 테스트할 때는 @SpringBootTest와 TestRestTemplate응 사용하면 됨

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    // @WithMockUser가 MockMvc에서만 작동하기 때문에
    // 현재 테스트 클래스는 스프링 부트 테스트기 때문에 진행 전에 mockMvc 인스턴스를 생성
    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test

    // 인증된 모의(가짜) 사용자를 만들어서 사용.
    // roles에 권한을 추가할 수 있음
    // 즉 이 어노테이션으로 인해 Role_USER 권한을 가진 사용자가 API를 요청하는 것과 동일한 효과를 가지게 됨
    @WithMockUser(roles ="USER")
    public void Posts_등록된다() throws Exception{

        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto
                .builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "api/v1/posts";

        //when
        // 생성된 MockMvc를 통해 API를 테스트
        // 본문(body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto, Long.class);

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정된다() throws Exception{

        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();

        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
       mvc.perform(put(url)
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(new ObjectMapper().writeValueAsString(requestDto)))
               .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle())
                .isEqualTo(expectedTitle);

        assertThat(all.get(0).getContent())
                .isEqualTo(expectedContent);

    }
}
