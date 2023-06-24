package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController  // 데이터만 보낼거라 RestController 사용함
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();  // 데이터베이스 대신 임시로 사용함

    // 메모 생성하기
    // 데이터는 boby에 JSON형태로 넘어옴
    //MemoRequestDto로 받아서 MemoResponseDto로 돌려줄거임
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // requestDto 를 Entity로 지정해야함(데이터베이스에 저장해야되니까)
        Memo memo = new Memo(requestDto);
        // requestDto는 username과 contants만 가지고 있기 때문에 기본생성자 이외에 생성자로 지정해야함
        // Alt+Enter로 생성자 생성

        // Memo Max ID Check(번호 부여하기)
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto로 변환을 진행함
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    // 메모 조회하기
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map TO List (위에 데이터를 Map으로 선언해서 List형태로 변환해줌)
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();
        return responseList;
    }

    // 메모 변경하기
//    @PutMapping("/memos/{id}")

    // 메모 삭제하기
//    @DeleteMapping("/memos/{id}")
}
