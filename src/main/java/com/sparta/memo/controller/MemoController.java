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
    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 업데이트 할 메모의 {id}를 받아옴, 수정된 정보를 JSON으로 받아옴

        // 1. 해당 메모가 DB에 존재하는지 확인하기
        if (memoList.containsKey(id)) {
            // 해당 메모 가져오기
            Memo memo = memoList.get(id); // id에 맞는 memo 객체가 반환된다

            // 메모 수정하기
            memo.update(requestDto);    // update() 메서드 생성하기
            return memo.getId();        // id 를 리턴함
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다");
        }

    }

    // 메모 삭제하기
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {   // 지우는거라 body는 필요없음
        // 해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            // 해당 메모를 삭제하기
            memoList.remove(id);    // 키 값으로 바로 삭제하기
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }

    }
}
