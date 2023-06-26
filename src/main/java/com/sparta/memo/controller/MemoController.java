package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController    // 데이터만 보낼거라 RestController 사용함
@RequestMapping("/api")
public class MemoController {

//    private final JdbcTemplate jdbcTemplate;    // 데이터베이스
    private final MemoService memoService;

    public MemoController(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
        this.memoService = new MemoService(jdbcTemplate);
    }

    /* */

    // 메모 생성하기
    // 데이터는 boby에 JSON형태로 넘어옴
    //MemoRequestDto로 받아서 MemoResponseDto로 돌려줄거임
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // requestDto 를 Entity로 지정해야함(데이터베이스에 저장해야되니까)
        // RequestDto -> Entity

        // ★ 3 Layer Architecture로 변환하기
        // 1. Service
        // 다른 클래스의 매서드를 호출하려면 해당 클래스를 객체로 만들어야 함(인스턴스화 진행)
        // MemoService 객체 만들기

        //     중복제거
//     MemoService memoService = new MemoService(jdbcTemplate);  // ()에 jdbcTemplate을 넣어줘야 함
        return memoService.creatMemo(requestDto); // 컨트롤러의 메서드 이름과 서비스의 이름을 일치시키면 직관적으로 이해하기 편함
        // 컨트롤러 creatMemo에서 전달받아서 비지니스 로직을 수행하는 메서드라는걸 알 수 있음
        // 1. 컨트롤러가 가져온 requestDto을 들고가야함.
        // 2. MemoService에 creatMemo를 생성해야함
        // => 메모 서비스에 있는 creatMemo()에서 비지니스로직이 수행되고 그 값을 바로 리턴해서 클라이언트에게 보낸다.

    }

    // 메모 조회하기
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {

        // service
//        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.getMemos(); // 보내줄 데이터는 없어서 파리미터에 넣을 값도 없음
    }

    // 메모 변경하기
    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 업데이트 할 메모의 {id}를 받아옴, 수정된 정보를 JSON으로 받아옴

//        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.getupdateMemo(id, requestDto);       // id와 수정할 데이터를 넣어준다
    }

    // 메모 삭제하기
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {  // 지우는거라 body는 필요없음

//        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.deleteService(id);
    }
}