package team7.simple.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import team7.simple.domain.course.dto.CourseRequestDto;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        properties = {
                "testId=lhs8701",
                "testName=이한솔"
        }
        //classes = {TestJpaRestController.class, MemberService.class},
        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Transactional
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mvc; //CourseController 관련 의존성만 로드

    @Autowired
    CourseService courseService;

    @Autowired
    ObjectMapper objectMapper;

    static final String testToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmc4NzAxZHNkZiIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjgwODc4ODMsImV4cCI6MTY5OTYyMzg4M30.MlThld3p5RFSHgAt0TFCrQ-dxQtaCFl5vLusDycVwXc";

    @Test
    void 강좌_개설() throws Exception {

//        String courseRequestDtoJson = objectMapper.writeValueAsString(new CourseRequestDto("title", "subtitle"));
//
//        //given
//        ResultActions resultActions = mvc.perform(post("/open/course")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .header(ConstValue.JWT_HEADER, testToken)
//                        .content(courseRequestDtoJson));
//
//        //then
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(print());

    }

    @Test
    void 강좌_조회() throws Exception {
        //given
        ResultActions actions = mvc.perform(get("/open/course/1"));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print()); //처리 내용을 출력
//                .andExpect((ResultMatcher) jsonPath("$.id", is("goddaehee")));
    }
}
