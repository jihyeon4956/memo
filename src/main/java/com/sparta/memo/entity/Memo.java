package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// entity : 데이터베이스랑 소통할 때 사용하는 클래스로 memo클래스를 생성해서 필요한 정보를 담는다
@Getter
@Setter
@NoArgsConstructor
public class Memo {
    private Long id;            // 메모끼리 구분하기 위해서 사용함
    private String username;    // 메모를 작성한 사람의 이름
    private String contents;    // 메모의 내용

    public Memo(MemoRequestDto requestDto) { // POST에서 받아온 타입을 가지는 생성자를 생성해줌
        // 클라이언트에서 받아온 데이터가 requestDto에 들어있어서 사용하려고 생성했음
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        // Memo 클래스의 username,contents에 받아온 값을 넣어서 Memo객체를 생성해낸다.
    }

    public void update(MemoRequestDto requestDto) {         // PutMapping에서 update() 생성함
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }
}